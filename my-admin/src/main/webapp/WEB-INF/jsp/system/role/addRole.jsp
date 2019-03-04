<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增角色</title>
<style>
	.module:hover{color:#008000;cursor:pointer}
	.module_item{display:none}
</style>
<%@include file="/common/header.jsp"%>
<script src="/resources/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<form class="layui-form" action="" style="padding: 20px 0 0 0;">
	    <div class="layui-form-item">
	      <label class="layui-form-label">角色名</label>
	      <div class="layui-input-inline">
	        <input type="text" name="name" lay-verify="required" placeholder="请输入角色名" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	   
	    <div class="layui-form-item">
	      <label class="layui-form-label">备注</label>
	      <div style="width:60%" class="layui-input-inline">
	        <input type="text" name="remark"  placeholder="请输入备注" autocomplete="off" class="layui-input">
	      </div>
	  </div>
	  <div class="layui-form-item">
	  	  <label class="layui-form-label"></label>
	   	  <div class="layui-input-inline">
	      <a id="contorller" href="javascript:">全部展开/全部关闭</a>
	      </div>
	  </div>
	<div  class="layui-form-item">
	 <label class="layui-form-label">配置权限</label>
	 <div style="width:80%" class="layui-input-inline">
	 
		<table style="width:100%">
						<c:forEach items="${moduleList}" var="t" varStatus="s">
						<tr>
							<td>
								<div style="width:100%">
								<input  type="checkbox" lay-filter="moudle" class="module_checkbox"  value="${t.id}"/>
								
								<span  class="module" value="${t.id}">${t.name}：</span>
								
								</div>
								
								<div style="display:none;width:100%" class="module_item_${t.id} module_item">
									<c:forEach items="${modulePermissionMap[t.id]}" var="permission">
										<div style='margin:0 0 0 20px'>
											<input type='checkbox' 
												lay-filter="module_item"
												name='permissionIds[]' 
												class='permission_${permission.id}'
												value="${permission.id}"
												/>${permission.name}
										</div>
										<div style='margin:0 0 0 40px'>
											<c:forEach items="${permissionAndItemMap[permission.id]}" var="permissionItem" varStatus="status">
												<input type='checkbox' 
													class="checkbox_${permission.id}" 
													name='permissionItem[]'
													value="${permission.id}_${permissionItem.id}"
													/>${permissionItem.name}
												<c:if test="${(status.index + 1)%10==0}"><br/></c:if>
											</c:forEach>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
						</c:forEach>
					</table>
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
    //模块复选框
     form.on('checkbox(moudle)', function(data){
    	 if(data.elem.checked){
    		 var $module_item = "module_item_" + data.value;
    			$("." + $module_item + " input:checkbox").each(function(){
    				$(this).attr("checked","checked");
    				//重新渲染 才会显示勾选
    				form.render("checkbox");
    			});
    	 }else{
    		var $module_item = "module_item_" + data.value;
 			$("." + $module_item + " input:checkbox").each(function(){
 				$(this).removeAttr("checked");
 				//重新渲染 才会显示勾选
 				form.render("checkbox");
 			});
    	 }
    	});  
     
     //权限复选框
     form.on('checkbox(module_item)', function(data){
    	 if(data.elem.checked){
    		 var $module_item = "checkbox_" + data.value;
    			$("." + $module_item).each(function(){
    				$(this).attr("checked","checked");
    				//重新渲染 才会显示勾选
    				form.render("checkbox");
    			});
    	 }else{
    		var $module_item = "checkbox_" + data.value;
 			$("." + $module_item).each(function(){
 				$(this).removeAttr("checked");
 				//重新渲染 才会显示勾选
 				form.render("checkbox");
 			});
    	 }
    	});
  })
  
  
  $(".module").click(function(){
		var id = $(this).attr("value");
		var $module_item = $(".module_item_" + id);
		if ($module_item.css("display") == "none") {
			$module_item.show();
		} else {
			$module_item.hide();
		}
	});
  
  $("#contorller").toggle(function(){
		$(".module_item").each(function(){
			$(this).show();
		});
	},function(){
		$(".module_item").each(function(){
			$(this).hide();
		});
	})
  </script>
  
 
  
</body>
</html>