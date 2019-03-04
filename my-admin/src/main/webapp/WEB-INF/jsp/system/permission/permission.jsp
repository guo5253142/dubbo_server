<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限列表</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
	
	<div class="layui-form layui-card-header layuiadmin-card-header-auto">
         <div class="layui-form-item">
         
          <div class="layui-inline">
            <label class="layui-form-label">模块名称</label>
            <div class="layui-input-block">
              <select name="moduleId" >
				  <option value="">请选择所属模块</option>
				  <c:forEach items="${moduleList}" var="t">
				  	<option value="${t.id}">${t.name}</option>
				  </c:forEach>
				</select> 
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">权限名称</label>
            <div class="layui-input-block">
              <input type="text" name="name" placeholder="请输入权限名称" autocomplete="off" class="layui-input">
            </div>
          </div>
         
          <div class="layui-inline">
            <button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-user-front-search">
              <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
            </button>
          </div>
        </div>
      </div>
	
    <div class="layui-card">
      <div class="layui-card-body">
	        <script type="text/html" id="toolbar">
       		 <div  class="layui-btn-container">
        	  <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
			  <button class="layui-btn layui-btn-sm" lay-event="setItemAuth">设置子权限</button>
      	     </div>
             </script>
        <table id="lay_table" lay-filter="lay_table"></table>
       
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
    var form = layui.form
    ,table = layui.table;
    
  //列表table
    table.render({
      elem: '#lay_table'
      ,url: '/permission/listPermission' 
       ,toolbar: '#toolbar'
       ,defaultToolbar: ['filter', 'print']
      ,cols: [[
		{type:'radio',fixed: 'left'}
        ,{field: 'id', width: 100, title: 'ID', sort: true}
        ,{field: 'moduleName', width: 120, title: '模块名称'}
        ,{field: 'name', width: 120, title: '权限名称'}
        ,{field: 'actionName', width: 200, title: 'ACTION名称'}
        ,{field: 'methodName', width: 150, title: '加载页面方法'}
        ,{field: 'loadDataMethodName', width: 150, title: '加载数据方法'}
        ,{field: 'paramName', width: 120, title: '参数名称'}
        ,{field: 'paramValue', width: 120, title: '参数值'}
        ,{field: 'menuUrl', minWidth: 150,title: '菜单地址'}
        ,{field: 'isMenuDesc', minWidth: 100,title: '是否菜单'}
        ,{field: 'orderIndex', width: 100, title: '排序编号'}
        ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table_tool'}
      ]]
      ,page: true
      ,limit: 20
      //,height: 'full-220'
      ,text: '对不起，加载出现异常！'
    });
  
    
    //监听工具条
    table.on('tool(lay_table)', function(obj){
      var data = obj.data;
      if(obj.event === 'del'){
    	  layer.confirm('确认删除行么', function(index){
    		  var field={"id":data.id};
    		  var url = "/permission/deletePermission";
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":url};
              ajaxSubmit(param);
              
          });
      } else if(obj.event === 'edit'){
        var tr = $(obj.tr);

        layer.open({
          type: 2
          ,title: '编辑权限'
          ,content: '/permission/editPermission?id='+data.id
          ,maxmin: true
          ,area: ['700px', '550px']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              var url = "/permission/updatePermission";
              var param = {"field":field,"index":index,"table":table,"layer":layer,"url":url};
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
      table.reload('lay_table', {
        where: field
      });
    });
    
    //头工具栏事件
    table.on('toolbar(lay_table)', function(obj){
      var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
      switch(obj.event){
        case 'add':
        	showAddWin(table,layer);
        break;
        case 'setItemAuth':
        	showSetItemAuthWin(table,layer,checkStatus);
        	break;
      };
    });
  
    
   
  });
  
  function showSetItemAuthWin(table,layer,checkStatus){
	  var checkData=checkStatus.data;
	  if(checkData.length === 0){
          return layer.msg('请选择数据');
      }
	  layer.open({
          type: 2
          ,title: '设置【'+checkData[0].name+'】子权限'
          ,content: '/permissionItem/init?pid='+checkData[0].id
          ,maxmin: true
          ,area: ['95%', '95%']
          ,btn: ['取消']
          
        }); 
  }
  
  function showAddWin(table,layer){
	  layer.open({
          type: 2
          ,title: '添加权限'
          ,content: '/permission/addPermission'
          ,maxmin: true
          ,area: ['700px', '550px']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/permission/savePermission"};
              ajaxSubmit(param);
             
            });  
            
            submit.trigger('click');
          }
        }); 
  }
  </script>
</body>
</html>