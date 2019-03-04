package com.my.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.admin.common.Constants;
import com.my.common.common.DataPage;
import com.my.common.system.domain.vo.UserSessionVo;

public abstract class BaseController<T> {
	public transient final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HttpSession session;
	//超级管理员id
	public final long ADMIN_ID=1l;
	//管理员角色id
	public final long ADMIN_ROLE_ID=1l;
	//ajax请求返回状态
	public final int AJAX_SUCCESS=1;
	public final int AJAX_FAIL=0;
	public final String EXCEPTION_MSG="系统出错，请稍后再试";
	
	protected UserSessionVo getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSessionVo vo = (UserSessionVo) session.getAttribute(Constants.USER_LOGIN_SESSION);// session参数
		return vo;
	}
	
	protected String getCurrentAccount() {
		UserSessionVo vo = (UserSessionVo) session.getAttribute(Constants.USER_LOGIN_SESSION);// session参数
		return vo.getUser().getAccount();
	}
	/**
	 * 输出分页json数据
	 * @param page
	 * @return
	 */
	 public void returnPageJson(HttpServletResponse response,DataPage<T> page){
		 try {
				response.setContentType("text/html; charset=utf-8");
				Map<String, Object> map= new HashMap<>();
				map.put("code", 0);
				map.put("msg", "");
				map.put("count", page.getTotalCount());
				map.put("data", page.getDataList());
				ObjectMapper mapper = new ObjectMapper();
		        String json = mapper.writeValueAsString(map);
				response.getWriter().write(json);
			} catch (IOException e) {
				logger.error("", e);
			}
	}
	 
	 /**
	 * 返回json结果
	 */
	public void outJson(HttpServletResponse response, int status, String msg){
		try {
			response.setContentType("text/html; charset=utf-8");
			Map<String, String> map= new HashMap<String, String>();
			map.put("status", status+"");
			map.put("msg", msg);
			response.getWriter().write(JSON.toJSONString(map));
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	 /**
	  * 返回json结果
	  */
	public void outJsonForException(HttpServletResponse response){
		try {
			response.setContentType("text/html; charset=utf-8");
			Map<String, String> map= new HashMap<String, String>();
			map.put("status", AJAX_FAIL+"");
			map.put("msg", EXCEPTION_MSG);
			response.getWriter().write(JSON.toJSONString(map));
		} catch (IOException e) {
			logger.error("", e);
		}
	}
}
