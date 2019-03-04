<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增模块</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
<form class="layui-form" action="" style="padding: 20px 0 0 0;">
	
    <div class="layui-form-item">
      <label class="layui-form-label">模块名称</label>
      <div class="layui-input-inline">
        <input type="text" name="name" lay-verify="required" placeholder="请输入模块名称" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">排序</label>
      <div class="layui-input-inline">
        <input type="text" name="orderIndex" lay-verify="number" placeholder="请输入排序编号" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">备注</label>
      <div class="layui-input-inline" style="width:60%">
        <input type="text" name="remark"  placeholder="请输入备注" autocomplete="off" class="layui-input">
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
  })
  </script>
  
</body>
</html>