package com.my.admin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.my.admin.common.Constants;
import com.my.admin.util.CookieUtils;
import com.my.admin.util.NetworkUtil;
import com.my.common.redis.service.JedisService;
import com.my.common.system.domain.User;
import com.my.common.system.domain.vo.AdminMenuVo;
import com.my.common.system.domain.vo.UserSessionVo;
import com.my.common.system.service.UserService;
import com.my.common.tools.MD5;

@Controller
public class LoginController extends BaseController<Object>{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisService jedisService;
	
	@RequestMapping("/home")
	public String home(ModelMap map) {
		return "/home";
	}
	
	@RequestMapping({"/", "/index"})
	public String index(ModelMap map) {
		return "/login";
	}
	
	@RequestMapping("/login")
	public String login(String account, String password, String code, ModelMap map,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		try {
			if (!loginCheck(account, password, code, map, response,request)) {
				return "/login";
			}
		} catch (Throwable e) {
			logger.error(String.format("用户[%s]登录异常", account),e);
			map.put("message", "运行时异常,请联系管理员");
			return "/login";
		}
		
		return "redirect:/main";
	}
	
	@RequestMapping("/main")
	public ModelAndView main() {
		UserSessionVo vo = (UserSessionVo) request.getSession().getAttribute(
				Constants.USER_LOGIN_SESSION);
		ModelAndView mv = new ModelAndView();
		Map<String, List<AdminMenuVo>> menuMap = null;
		if (vo == null) {
			mv.setViewName("login");
			return mv;
		}
		//判断是否初始密码
		boolean isInitPassword = false;
		if(vo.getUser().getPassword().equals(MD5.encode1(Constants.DEFAULT_PASSWD))){
			isInitPassword = true;
		}
		
		menuMap = userService.getMenu(vo.getPermissionMap());
		mv.addObject("menuMap", menuMap);
		mv.addObject("isInitPassword", isInitPassword);
		mv.setViewName("main");
		return mv;
	}
	
	
	/***
	 * 登陆验证
	 * 
	 */
	private boolean loginCheck(String account, String password, String code,
			ModelMap map, HttpServletResponse response,HttpServletRequest request) {
		// Session超时，验证码无法获取
		
		if (request.getSession() == null
				|| request.getSession().getAttribute("randomCode") == null) {
			map.addAttribute("message", "验证码超时，请重新登陆");
			return false;
		}
		
		// 验证码
		if (StringUtils.isBlank(code)) {
			map.put("message", "请输入验证码");
			return false;
		}
		if (!code.equalsIgnoreCase((String) request.getSession().getAttribute("randomCode"))) {
			map.put("message", "验证码输入错误");
			return false;
		}
		// 2.验证用户名 密码
		//
		User user = userService.getUserByAccount(account);
		if (user == null) {
			map.put("message", "登陆失败！用户不存在");
			return false;
		}
		if (user.getStatus()==0) {
			map.put("message", "用户已被禁用!");
			return false;
		}
		if (!this.validateUser(user, account, password, map,request)) {
			return false;
		}
		if (!getPermission(user, response, map)) {
			return false;
		}
		User ssoUser = new User();
		ssoUser.setAccount(account);
		ssoUser.setPassword(password);
		setSSOtoken(ssoUser, response, map);
		return true;
	}
	
	/***
	 * 验证用户名密码
	 * 
	 */
	public boolean validateUser(User user, String account, String password,
			ModelMap map,HttpServletRequest request) {
		String ip = NetworkUtil.getIpAddress(request);
		// 登入密码错误次数处理
		String cacheKey = "loginerr_"+account + ip;
		String cacheObj = jedisService.get(cacheKey);
		int errCount = 1;
		if (cacheObj != null) {
			errCount = Integer.valueOf(cacheObj) + 1;
		}
		if (errCount >Constants.LOGIN_ERROR_COUNT) {
			//10分钟内连续错误10次及以上 同一用户同一Ip锁定10分钟
			map.put("message", "登陆失败！操作频繁");
			return false;
		}
		if (!user.getPassword().equals(MD5.encode1(password))) {// 最终版把MD5.encode1(password)替换password
			errCount +=1;
			//设置10分钟缓存,
			jedisService.set(cacheKey, errCount+"", 10*60L);
			map.put("message", "登陆失败！用户名或密码错误");
			return false;
		}
		return true;
	}
	
	public boolean getPermission(User user, HttpServletResponse response, ModelMap map) {
		UserSessionVo vo = userService.getPermission(user);
		vo.setUser(user);
		if (user.getLastLoginTime() == null) {
			vo.setLastLoginTime(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		} else {
			vo.setLastLoginTime(new SimpleDateFormat("yyyy年MM月dd日").format(user.getLastLoginTime()));
		}
		user.setLastLoginTime(new Date(System.currentTimeMillis()));
		
		userService.updateUser(user);
		
		try {
			CookieUtils.setCookie(request, response, "loginid",
					URLEncoder.encode(user.getAccount(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("用户名转码存cookie异常,帐号【{}】", user.getAccount(), e);
		}

		CookieUtils.setCookie(request, response, "authid",
				MD5.encode(user.getAccount() + Constants.LOGIN_SYSTEM_YZ, "UTF-8"));

		HttpSession session = request.getSession();
		session.setAttribute(Constants.USER_LOGIN_SESSION, vo);

		return true;
	}
	
	public boolean setSSOtoken(User user, HttpServletResponse response,
			ModelMap map){
		try{
			String token = getTokenByUser(user);
			request.getSession().setAttribute(Constants.TOKENID, token);
			logger.info("单点登录session设置token={},sessionId={}",token,request.getSession().getId());
			return true;
		}catch(Exception e){
			logger.error("设置SSO token异常,帐号【{}】", user.getAccount(), e);
			return false;
		}
	}
	
	public String getTokenByUser(User user) {
		String account = user.getAccount();
		if(StringUtils.isBlank(account)){
			return null;
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String token = MD5.encode(user.getAccount() + uuid, "UTF-8");
		
		String oldTokenKey = jedisService.get(user.getAccount());
		if(StringUtils.isNotBlank(oldTokenKey)){
			jedisService.del(oldTokenKey);
		}
		String results = user.getAccount()+"::"+uuid;
		jedisService.set(token, results,60 * 60 * 24L );
		jedisService.set(user.getAccount(),token, 60 * 60 * 24L);
		logger.info("tokeninfo[token={}:uuid={}]",token,uuid);
		return token;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response,HttpServletRequest request) throws IOException {
		logger.info("用户：{}，退出登录",getUser(request).getUser().getId());
		request.getSession().removeAttribute(Constants.USER_LOGIN_SESSION);
		request.getSession().removeAttribute(Constants.TOKENID);
		request.getSession().invalidate();
		CookieUtils.deleteCookie(request, response, "loginid");
		CookieUtils.deleteCookie(request, response, "authid");
		return "redirect:/index";
	}

	/**
     * 当前用户密码修改UI
     */
	@RequestMapping("/showPasswordModify")
    public String passwordmodifyui(ModelMap modelMap, HttpServletRequest request) {
        return "/system/passwordModify";
    }
	
	@RequestMapping("/rcajaxPasswordModify")
    public void passwordModify(HttpServletResponse response,String oldPassword, String newPassword, String rePassword) {
        User user = userService.getUserByAccount(getCurrentAccount());
        if(!newPassword.equals(rePassword)){
        	outJson(response,this.AJAX_FAIL, "新密码两次输入不一致");
        	return;
        }else if(oldPassword.length() < 5 || oldPassword.length() > 10 || newPassword.length() < 5 || newPassword.length() > 10){
        	outJson(response,this.AJAX_FAIL, "密码长度必须在5~10字符之间！");
        	return;
        }else if(!user.getPassword().equals(MD5.encode1(oldPassword))) {
            outJson(response,this.AJAX_FAIL, "输入的旧密码与原来密码不一致！");
        	return;
        }else {
            user.setPassword(MD5.encode1(newPassword));
            userService.updateUser(user);
            outJson(response,this.AJAX_SUCCESS, "修改密码成功！");
        }

    }
}
