<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>子权限列表</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
	
	
    <div class="layui-card">
      <div class="layui-card-body">
	        <script type="text/html" id="toolbar">
       		 <div  class="layui-btn-container">
        	  <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
			  <button class="layui-btn layui-btn-sm" lay-event="edit">修改</button>
			  <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
      	     </div>
             </script>
        <table id="lay_table" lay-filter="lay_table"></table>
       <input type="hidden" id="permissionId" value="${pid}"/>
      </div>
    </div>
  </div>

  <script>
  layui.use(['table','form'], function(){
    var $ = layui.$
    var form = layui.form
    ,table = layui.table;
    var pid=$("#permissionId").val();
  //列表table
    table.render({
      elem: '#lay_table'
      ,url: '/permissionItem/listPermissionItem?pid='+ pid
       ,toolbar: '#toolbar'
       ,defaultToolbar: []
      ,cols: [[
		{type:'checkbox',fixed: 'left'}
        ,{field: 'id', width: 100, title: 'ID', sort: true}
        ,{field: 'name', width: 200, title: '子权限名称'}
        ,{field: 'methodValue1', width: 200, title: '方法一名称'}
        ,{field: 'methodValue2', width: 200, title: '方法二名称'}
       
      ]]
      //,height: 'full-220'
      //,text: '对不起，加载出现异常！'
      ,text: {
    		    none: '暂无相关数据' //默认：无数据。
    		  }
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
          ,area: ['500px', '600px']
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
    
    //头工具栏事件
    table.on('toolbar(lay_table)', function(obj){
    	
      var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
      switch(obj.event){
        case 'add':
        	showAddWin(table,layer,checkStatus);
        break;
        case 'edit':
        	showEditWin(table,layer,checkStatus);
        break;
        case 'delete':
        	if(checkStatus.data.length==0){
        		 layer.msg("请选择要删除的数据");
       			 return;
        	}
        	  layer.confirm('确认删除所选择的数据么', function(index){
        		  var ids="";
        		  for(i=0;i<checkStatus.data.length;i++){
        			  ids+=checkStatus.data[i].id+","
        		  }
        		  ids=ids.substring(0,ids.length-1);
        		  var field={"ids":ids};
        		  var url = "/permissionItem/deletePermissionItem";
                  var param={"field":field,"index":index,"table":table,"layer":layer,"url":url};
                  ajaxSubmit(param);
                  
              });
        break;
       
      };
    });
  
    
   
  });
  
  function showEditWin(table,layer,checkStatus){
	  if(checkStatus.data.length>1||checkStatus.data.length==0){
		  layer.msg("请选择一条记录进行修改");
		  return;
	  }
	  var id=checkStatus.data[0].id;
	  layer.open({
          type: 2
          ,title: '修改子权限'
          ,content: '/permissionItem/editPermissionItem?id='+id
          ,maxmin: true
          ,area: ['500px', '400px']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/permissionItem/updatePermissionItem"};
              ajaxSubmit(param);
             
            });  
            
            submit.trigger('click');
          }
        }); 
	 
  }
  
  function showAddWin(table,layer,checkStatus){
	  var id=$("#permissionId").val();
	  layer.open({
          type: 2
          ,title: '添加子权限'
          ,content: '/permissionItem/addPermissionItem?pid='+id
          ,maxmin: true
          ,area: ['500px', '400px']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);

            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              var param={"field":field,"index":index,"table":table,"layer":layer,"url":"/permissionItem/savePermissionItem"};
              ajaxSubmit(param);
             
            });  
            
            submit.trigger('click');
          }
        }); 
  }
  </script>
</body>
</html>