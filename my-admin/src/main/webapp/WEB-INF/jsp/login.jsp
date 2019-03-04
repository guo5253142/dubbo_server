<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<link rel="stylesheet" href="/resources/js/layuiadmin/style/login.css" media="all">
<%@include file="/common/header.jsp"%>
</head>
<body>
 <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>后台管理系统</h2>
        <p></p>
      </div>
      <form class="adm-form" action="/login" method="post">
      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
          <input type="text" name="account" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input">
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
          <input type="password" name="password" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
        </div>
        <div class="layui-form-item">
          <div class="layui-row">
            <div class="layui-col-xs7">
              <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
              <input type="text" name="code" id="LAY-user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
            </div>
            <div class="layui-col-xs5">
              <div style="margin-left: 10px;">
               <img id="RIC" class="layadmin-user-login-codeimg" 
							src="/randomImageCode?datestr=<%=System.currentTimeMillis()%>"
							alt=""
							onclick="javascript:document.getElementById('RIC').src='/randomImageCode?'+(new Date().getTime().toString(36));return false;">
              </div>
            </div>
          </div>
        </div>
        <div class="layui-form-item" style="margin-bottom: 20px;">
          <p style="color: red">${message }&nbsp;</p>
        </div>
        <div class="layui-form-item">
          <button class="layui-btn layui-btn-fluid" type="submit">登 入</button>
        </div>
        
       
      </div>
      </form>
    </div>
    
    <div class="layui-trans layadmin-user-login-footer">
   		 <p>建议使用浏览器：FireFox、Chrome、IE10及其以上版本</p>
     <!--  <p>© 2018 <a href="http://www.layui.com/" target="_blank">layui.com</a></p>
       -->
    </div>
    
    
  </div>

  <script>
  
  </script>

</body>
</html>