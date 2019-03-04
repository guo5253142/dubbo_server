package com.my.asynch.tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
@Component
public class SpringApplicationContextHolder implements ServletContextListener  {
	public static WebApplicationContext webApplicationContext;
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();  
		webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);  
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	
}