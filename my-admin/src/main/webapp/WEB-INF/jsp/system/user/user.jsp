<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
         <div class="layui-form-item">
          <!-- <div class="layui-inline">
            <label class="layui-form-label">ID</label>
            <div class="layui-input-block">
              <input type="text" name="id" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>  -->
          <div class="layui-inline">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
              <input type="text" name="account" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
              <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
               <select name="roleId" lay-verify="">
				  <option value="">请选择一个角色</option>
				  <c:forEach items="${roleList}" var="t">
				  	<option value="${t.id}">${t.name}</option>
				  </c:forEach>
				</select> 
            </div>
          </div>
          <div class="layui-inline">
            <button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-user-front-search">
              <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
            </button>
          </div>
        </div>
      </div>
      
      <div class="layui-card-body">
	        <script type="text/html" id="user-toolbar">
       		 <div  class="layui-btn-container">
        	  <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
      	     </div>
             </script>
        <table id="user_table" lay-filter="user_table"></table>
       
        <script type="text/html" id="table_tool">
          <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
          <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
        </script>
      </div>
    </div>
  </div>

  <script>
  layui.use(['table','form'], function(){
    var $ = layui.$
    ,form = layui.form
    ,table = layui.table;
    
  //用户列表table
    table.render({
      elem: '#user_table'
      ,url: '/user/listUser' 
       ,toolbar: '#user-toolbar',
       defaultToolbar: ['filter', 'print']
      ,cols: [[
		{type:'numbers'}
        ,{field: 'id', width: 100, title: 'ID', sort: true}
        ,{field: 'account', title: '账号', minWidth: 100}
        ,{field: 'name', width: 100, title: '姓名'}
        ,{field: 'role', width: 100, title: '角色',templet:'<div>{{d.role.name}}</div>'}
        ,{field: 'statusDesc', width: 100,title: '状态'}
        ,{field: 'lastLoginTime', width: 200,title: '上次登录时间', sort: true}
        ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table_tool'}
      ]]
      ,page: true
      ,limit: 20
      //,height: 'full-220'
      ,text: '对不起，加载出现异常！'
    });
  
    
    //监听工具条
    table.on('tool(user_table)', function(obj){
      var data = obj.data;
      if(obj.event === 'del'){
    	  layer.confirm('确认删除行么', function(index){
    		  var field = {"id" : data.id}; //获取提交的字段
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/user/deleteUser","tableId":"user_table"};
              ajaxSubmit(param);
              
          });
      } else if(obj.event === 'edit'){
        var tr = $(obj.tr);

        layer.open({
          type: 2
          ,title: '编辑用户'
          ,content: '/user/editUser?id='+data.id
          ,maxmin: true
          ,area: ['500px', '450px']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/user/updateUser","tableId":"user_table"};
              ajaxSubmit(param);
             
            });  
            
            submit.trigger('click');
          }
          ,success: function(layero, index){
            
          }
        });
      }
    });
    
    //监听搜索
    form.on('submit(LAY-user-front-search)', function(data){
      var field = data.field;
      
      //执行重载
      table.reload('user_table', {
        where: field
      });
    });
    
    //头工具栏事件
    table.on('toolbar(user_table)', function(obj){
      var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
      switch(obj.event){
        case 'add':
        	layer.open({
                type: 2
                ,title: '添加用户'
                ,content: '/user/addUser'
                ,maxmin: true
                ,area: ['500px', '450px']
                ,btn: ['确定', '取消']
                ,yes: function(index, layero){
                  var iframeWindow = window['layui-layer-iframe'+ index]
                  ,submitID = 'LAY-user-front-submit'
                  ,submit = layero.find('iframe').contents().find('#'+ submitID);

                  //监听提交
                  iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                    var field = data.field; //获取提交的字段
                    var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/user/saveUser","tableId":"user_table"};
                    ajaxSubmit(param);
                   
                  });  
                  
                  submit.trigger('click');
                }
              }); 
        break;
      };
    });
  
    
   
  });
  </script>
</body>
</html>