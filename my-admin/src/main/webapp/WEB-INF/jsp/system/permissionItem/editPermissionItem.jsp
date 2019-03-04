<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改子权限</title>
<%@include file="/common/header.jsp"%>

</head>
<body>
<form class="layui-form"  lay-filter="editForm" action="" style="padding: 20px 0 0 0;">
	
    <input type="hidden" name="id"/>
    <div class="layui-form-item">
      <label class="layui-form-label">子权限名称</label>
      <div class="layui-input-inline">
        <input type="text" name="name" lay-verify="required" placeholder="请输入子权限名称" autocomplete="off" class="layui-input">
      </div>
    </div>
     <div class="layui-form-item">
      <label class="layui-form-label">方法一名称</label>
      <div class="layui-input-inline">
        <input type="text" name="methodValue1"  placeholder="请输入方法一名称" autocomplete="off" class="layui-input">
      </div>
    </div>
     <div class="layui-form-item">
      <label  class="layui-form-label">方法二名称</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="methodValue2"   placeholder="请输入方法二名称" autocomplete="off" class="layui-input">
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
    	  "id":"${permissionItem.id}"
    	  ,"name": "${permissionItem.name}"
    	  ,"methodValue1": "${permissionItem.methodValue1}"
    	  ,"methodValue2": "${permissionItem.methodValue2}"
    		  
    	});
    
    
  })
  </script>
  
</body>
</html>