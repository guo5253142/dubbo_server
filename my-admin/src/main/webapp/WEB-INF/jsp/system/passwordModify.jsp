<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">修改密码</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">当前密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="oldPassword" lay-verify="required" lay-verType="tips" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="newPassword" lay-verify="pass" lay-verType="tips" autocomplete="off" id="LAY_password" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">5到10个字符</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">确认新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="rePassword" lay-verify="repass" lay-verType="tips" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn" lay-submit lay-filter="setmypass">确认修改</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <script>
  layui.use(['form','admin'], function(){
    var $ = layui.$
    ,form = layui.form
    ,admin = layui.admin;
    
    //设置密码
    form.on('submit(setmypass)', function(obj){
    	
      var field = obj.field; //获取提交的字段
      var loadId = layer.load(1, {
          shade: [0.3,'#000']
        });
	  $.ajax({     
	        url: "/rcajaxPasswordModify",
	        type: "POST",
	        data: field,
	        dataType: "json",
	        success: function (data) {
	        	if(data.status=="1"){
	           		layer.msg(data.msg, {
  	                	icon: 1
  	           		 });
	           		
	           		setTimeout(function(){
	           			var topAdmin = parent === self ? admin : parent.layui.admin;
	           			topAdmin.closeThisTabs();
	           			},700) 
	        	}else{
	        		layer.msg(data.msg, {
  	                icon: 2
  	            });
	        	}
	        	layer.close(loadId);
	        }
	    });
    	  
      
      
    });
  });
  </script>
  
</body>
</html>