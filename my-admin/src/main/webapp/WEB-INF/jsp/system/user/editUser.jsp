<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
<form class="layui-form" lay-filter="editForm" style="padding: 20px 0 0 0;">
	<input type="hidden" name="id"/>
	 <div class="layui-form-item">
      <label class="layui-form-label">选择角色</label>
      <div class="layui-input-inline">
        <select  name="roleId" lay-verify="required" >
		  <option value="">请选择一个角色</option>
		  <c:forEach items="${roleList}" var="t">
		  	<option value="${t.id}">${t.name}</option>
		  </c:forEach>
		</select> 
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">用户名</label>
      <div class="layui-input-inline">
        <input type="text" name="account" readonly="readonly" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label">姓名</label>
      <div class="layui-input-inline">
        <input type="text" name="name" lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">选择状态</label>
      <div class="layui-input-inline">
        <select name="status" lay-verify="">
		  <option value="">请选择一个状态</option>
		  <c:forEach items="${statusList}" var="t">
		  	<option value="${t.index}">${t.description}</option>
		  </c:forEach>
		</select> 
      </div>
    </div>
    <div class="layui-form-item layui-hide">
      <input type="button" lay-submit lay-filter="LAY-user-front-submit" id="LAY-user-front-submit" value="确认">
    </div>
  </form>
  
   <script>
  layui.use(['form'], function(){
    var $ = layui.$
    ,form = layui.form;
    
    form.val("editForm", {
    	  "id":"${user.id}",
    	  "roleId":"${user.role.id}",
    	  "account": "${user.account}"
    	  ,"name": "${user.name}"
    	  ,"status": "${user.status}"
    	});
    
    
  })
  </script>
  
</body>
</html>