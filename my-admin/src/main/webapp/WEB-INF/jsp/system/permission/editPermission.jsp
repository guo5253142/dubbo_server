<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改权限</title>
<%@include file="/common/header.jsp"%>

<style>
.layui-form-item label{width:90px};
</style>

</head>
<body>
<form class="layui-form" lay-filter="editForm" style="padding: 20px 0 0 0;">
	<input type="hidden" name="id"/>
	  <div class="layui-inline" style="margin-bottom: 15px">
      <label class="layui-form-label" style="width:90px">所属模块</label>
      <div class="layui-input-inline">
        <select name="moduleId" lay-verify="required">
		  <option value="">请选择所属模块</option>
		  <c:forEach items="${moduleList}" var="t">
		  	<option value="${t.id}">${t.name}</option>
		  </c:forEach>
		</select> 
      </div>
    </div>
    
    <div class="layui-inline" style="margin-bottom: 15px">
      <label class="layui-form-label" style="width:90px">权限名称</label>
      <div class="layui-input-inline">
        <input type="text" name="name" lay-verify="required" placeholder="请输入权限名称" autocomplete="off" class="layui-input">
      </div>
    </div>
    
    <div class="layui-inline" style="margin-bottom: 15px">
      <label class="layui-form-label" style="width:90px">是否菜单</label>
      <div class="layui-input-inline">
        <select name="isMenu" lay-verify="required">
		  <option value="">请选择是否</option>
		  <c:forEach items="${ynList}" var="t">
		  	<option value="${t.index}">${t.description}</option>
		  </c:forEach>
		</select> 
      </div>
    </div>
    
   <div class="layui-inline" style="margin-bottom: 15px">
      <label class="layui-form-label" style="width:90px">排序序号</label>
      <div class="layui-input-inline">
        <input type="text" name="orderIndex" lay-verify="number" placeholder="请输入排序编号" autocomplete="off" class="layui-input">
      </div>
    </div>
    
     <div class="layui-form-item">
      <label class="layui-form-label">ACTION名称</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="actionName" lay-verify="required" placeholder="请输入ACTION名称" autocomplete="off" class="layui-input">
      </div>
    </div>
     <div class="layui-form-item">
      <label  class="layui-form-label">菜单地址</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="menuUrl" lay-verify="required"  placeholder="请输入菜单地址" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">加载页面方法</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="methodName" lay-verify="required"  placeholder="请输入方法名称" autocomplete="off" class="layui-input">
      </div>
    </div>
     <div class="layui-form-item">
      <label class="layui-form-label">加载数据方法</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="loadDataMethodName" lay-verify="required"  placeholder="请输入方法名称" autocomplete="off" class="layui-input">
      </div>
    </div>
     <div class="layui-form-item">
      <label class="layui-form-label">参数名称</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="paramName"  placeholder="请输入参数名称" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">参数值</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="paramValue"  placeholder="请输入参数值" autocomplete="off" class="layui-input">
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
    	  "id":"${permission.id}"
    	  ,"moduleId": "${permission.moduleId}"
    	  ,"name": "${permission.name}"
    	  ,"orderIndex": "${permission.orderIndex}"
    	  ,"actionName": "${permission.actionName}"
    	  ,"isMenu": "${permission.isMenu}"
    	  ,"menuUrl": "${permission.menuUrl}"
    	  ,"loadDataMethodName": "${permission.loadDataMethodName}"
    	  ,"methodName": "${permission.methodName}"
    	  ,"paramName": "${permission.paramName}"
    	  ,"paramValue": "${permission.paramValue}"
    		  
    	});
    
    
  })
  </script>
  
</body>
</html>