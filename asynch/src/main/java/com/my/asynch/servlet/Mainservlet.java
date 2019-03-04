package com.my.asynch.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.my.asynch.job.JobManager;

public class Mainservlet extends HttpServlet{
	private static final long serialVersionUID = -3994014257130930572L;
	@Autowired
	private JobManager jobManager;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("jobRestart") != null) {
			jobManager.removeAll();
		}
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		jobManager = webApplicationContext.getBean(JobManager.class);
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		List<List<String>> jobList = jobManager.getJobDetail();
		StringBuffer stBuffer =new StringBuffer();
		stBuffer.append("<table>");
		for (List<String> jobdetailList : jobList) {
			stBuffer.append("<tr>");
			for (String jobdetail : jobdetailList) {
				stBuffer.append("<td>");
				stBuffer.append(jobdetail);
				stBuffer.append("</td>");
			}
			stBuffer.append("</tr>");
			
		}
		stBuffer.append("</table>");
		resp.getWriter().write(stBuffer.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
}
