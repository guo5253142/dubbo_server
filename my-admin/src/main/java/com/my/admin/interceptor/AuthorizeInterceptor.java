package com.my.admin.interceptor;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.my.admin.common.Constants;
import com.my.admin.util.CookieUtils;
import com.my.common.redis.service.JedisService;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.vo.UserSessionVo;
import com.my.common.tools.MD5;

/**
 * Spring MVC权限验证拦截器
 * 
 */
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JedisService jedisService;

	/**
	 * 在preHandle中，可以进行编码、安全控制等处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//防止ajax get请求的缓存
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		

		String actionName = "";

		if (handler instanceof HandlerMethod) {
			actionName = ((HandlerMethod) handler).getBean().getClass().getSimpleName()
					.toLowerCase();
		}
		if (handler instanceof ResourceHttpRequestHandler) {
			actionName = ((ResourceHttpRequestHandler) handler).getClass().getSimpleName()
					.toLowerCase();
		}
		//由于使用了AOP 所有的controller都被CGlib增强了，所以类的名字要做特殊处理
		if(actionName.indexOf("$$")>0){
			actionName=actionName.substring(0, actionName.indexOf("$$"));
		}
		MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
		String method = methodNameResolver.getHandlerMethodName(request).toLowerCase();
		if (method.equals("") || method.equals("/")
				|| method.toLowerCase().indexOf("ajax") != -1 
				|| method.contentEquals("index")
				|| method.contentEquals("logout")
				|| method.contentEquals("login") 
				|| method.contentEquals("home")
				|| method.contentEquals("main")
				|| method.contentEquals("showpasswordmodify")
				|| method.contentEquals("upload")) {
			//rcajax请求需进行session超时的判断
			if(method.toLowerCase().indexOf("rcajax") != -1 ){
				
			}else{
				return true;
			}
			
		}
		HttpSession session = request.getSession();
		String loginid = CookieUtils.getCookieValue(request, "loginid");
		String authid = CookieUtils.getCookieValue(request, "authid");
		String ssoToken = (String) request.getSession().getAttribute(Constants.TOKENID);
		String ssoTokenCt = jedisService.get(ssoToken);
		UserSessionVo vo = (UserSessionVo) session.getAttribute(Constants.USER_LOGIN_SESSION);
		String requestedWith = request.getHeader("x-requested-with");
		boolean isajax = (requestedWith != null && "XMLHttpRequest".equals(requestedWith));
		if (StringUtils.isNotBlank(loginid)) {
			loginid = URLDecoder.decode(loginid, "utf-8");
		}
		if ((StringUtils.isBlank(loginid) && StringUtils.isBlank(authid))
				 || (!MD5.encode(loginid + Constants.LOGIN_SYSTEM_YZ, "UTF-8").equals(authid)) 
				 || vo == null
				 || ssoTokenCt == null) {
			logger.info("单点登录,非法登录操作.account={}", loginid);
			if (isajax) {
				response.setContentType("application/json");
				response.setHeader("session-status", "timeout");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(
						"{\"status\":0,\"msg\":\"登录超时或登录信息有误，请重新登录！\"}");
				return false;
			} else {
				ModelAndView mav = new ModelAndView("forward:/index");
				mav.addObject("msg", "登录超时或登录信息有误，请重新登录！");
				throw new ModelAndViewDefiningException(mav);
			}
		}

		session.setAttribute(Constants.USER_LOGIN_SESSION, vo);
		session.setAttribute(Constants.TOKENID,ssoToken);
		//rcajax请求的无须权限
		if(method.toLowerCase().indexOf("rcajax") != -1 ){
			return true;
		}
		
		// 获取请求参数
		Map<String, String[]> paramMap = request.getParameterMap();

		Map<String, Permission> permissionMap = vo.getPermissionMap();
		Map<String, PermissionItem> itemMap = vo.getItemMap();
		boolean isPass = false; // 是否有权限 默认没有权限

		if (permissionMap.containsKey(actionName + method)) {
			Permission p = permissionMap.get(actionName + method);
			String para = p.getParamName();
			String paraValue = p.getParamValue();
			if (para != null && !para.equals("") && paraValue != null && paraValue != null) {
				String[] paramValue = (String[]) paramMap.get(para);
				if (paraValue.equalsIgnoreCase(paramValue[0])) {// 设定参数取出来值后与设定值比较
					isPass = true;
				}
			} else {// 不用判断子参数，直接通过
				isPass = true;
			}
		} else {
			// 有参数的
			if (paramMap.size() > 0) {
				Permission p = null;
				Iterator<String> it = paramMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					Object[] valuesObj = (Object[]) paramMap.get(key);
					if (valuesObj instanceof java.io.File[]) {
						continue;
					}
					String[] pValue = (String[]) paramMap.get(key);
					if (pValue != null && pValue.length > 0) {
						p = permissionMap.get(actionName + method + key.toLowerCase()
								+ pValue[0].toString().toLowerCase());
					}
					if (p != null) {
						isPass = true;
						break;
					}
				}
			}

		}

		// 子权限存在，且父权限的类名与当前执行类名一致
		if (itemMap.containsKey(actionName + method)
				&& itemMap.get(actionName + method).getPermission().getActionName()
						.equalsIgnoreCase(actionName)) {
			isPass = true;
		}
		if (!isPass) {
			// 如果是ajax
			if (isajax) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print("{\"status\":0,\"msg\":\"您没有权限进行此操作！\"}");
			} else {
				// 普通页面请求
				ModelAndView mav = new ModelAndView("noauthorize");
				mav.addObject("errorMsg", "您没有权限进行此操作！");
				throw new ModelAndViewDefiningException(mav);
			}
		}

		return isPass;
	}

	/**
	 * 在postHandle中，有机会修改ModelAndView；
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView model) throws Exception {

	}

	/**
	 * afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception exception) throws Exception {

	}

}
