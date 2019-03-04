<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-form layui-card-header layuiadmin-card-header-auto">
        <div class="layui-form-item">
          
          <div class="layui-inline">
            <label class="layui-form-label">角色名</label>
            <div class="layui-input-block">
              <input type="text" name="name" placeholder="请输入" autocomplete="off" class="layui-input">
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
	        <script type="text/html" id="role-toolbar">
       		 <div  class="layui-btn-container">
        	  <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
      	     </div>
             </script>
        <table id="role_table" lay-filter="role_table"></table>
       
        <script type="text/html" id="table_tool">
          <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
          <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
        </script>
      </div>
    </div>
  </div>

  <script>
  layui.use(['table','form','view'], function(){
    var $ = layui.$
    ,form = layui.form
    ,table = layui.table
    ,view = layui.view;
    
  //列表table
    table.render({
      elem: '#role_table'
      ,url: '/role/listRole' 
       ,toolbar: '#role-toolbar',
       defaultToolbar: ['filter', 'print']
      ,cols: [[
		{type:'numbers'}
        ,{field: 'id', width: 100, title: 'ID', sort: true}
        ,{field: 'name', width: 100, title: '姓名'}
        ,{field: 'remark', minWidth: 100,title: '备注'}
        ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table_tool'}
      ]]
      ,page: true
      ,limit: 20
      ,text: '对不起，加载出现异常！'
    });
  
    
    //监听工具条
    table.on('tool(role_table)', function(obj){
      var data = obj.data;
      if(obj.event === 'del'){
    	  layer.confirm('确认删除行么', function(index){
    		  
    		  var field = {"id" : data.id};
              var url = "/role/deleteRole";
              var param = {"field":field,"index":index,"table":table,"layer":layer,"url":url,"tableId":"role_table"};
              ajaxSubmit(param);
              
          });
      } else if(obj.event === 'edit'){
        var tr = $(obj.tr);
        layer.open({
          type: 2
          ,title: '编辑角色'
          ,content: '/role/editRole?id='+data.id
          ,maxmin: true
          ,area: ['95%', '95%']
          ,btn: ['确定', '取消']
          ,yes: function(index, layero){
            var iframeWindow = window['layui-layer-iframe'+ index]
            ,submitID = 'LAY-user-front-submit'
            ,submit = layero.find('iframe').contents().find('#'+ submitID);
          
            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
              var field = data.field; //获取提交的字段
              //这样做是为了替换复选框的name
              var req=getReqData(field);
             
              var loadId = layer.load(1, {
                  shade: [0.3,'#000']
                });
                $.ajax({     
    		        url: "/role/updateRole?"+req,
    		        type: "GET",
    		        //data:field,
    		        dataType: "json",
    		        //async: false,
    		        success: function (data) {
    		        	if(data.status=="1"){
    		        		table.reload('role_table'); //数据刷新
    	  	              	layer.close(index);
    	  	              layer.msg(data.msg, {
    	  	                icon: 1
    	  	              });
    		        	}else{
    		        		layer.msg(data.msg, {
    		  	                icon: 2
    		  	            });
    		        	}
    		        	 layer.close(loadId);
    		          
    		        }
    		    });  
              
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
      table.reload('role_table', {
        where: field
      });
    });
    
    //头工具栏事件
    table.on('toolbar(role_table)', function(obj){
      var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
      switch(obj.event){
        case 'add':
        	layer.open({
                type: 2
                ,title: '添加角色'
                ,content: '/role/addRole'
                ,maxmin: true
                ,btn: ['确定', '取消']
        		,area: ['95%', '95%']
        		,success: function(layero,index){
        		  //默认最大化
        	      //layer.full(index);
        	    }
                ,yes: function(index, layero){
                  var iframeWindow = window['layui-layer-iframe'+ index]
                  ,submitID = 'LAY-user-front-submit'
                  ,submit = layero.find('iframe').contents().find('#'+ submitID);

                  //监听提交
                  iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                    var field = data.field; //获取提交的字段
                    //这样做是为了替换复选框的name
                    var req=getReqData(field);
                    var loadId = layer.load(1, {
                        shade: [0.3,'#000']
                      });
                    $.ajax({     
          		        url: "/role/saveRole?"+req,
          		        type: "GET",
          		        //data:req,
          		        dataType: "json",
          		        //async: false,
          		        success: function (data) {
          		        	if(data.status=="1"){
          		        		 table.reload('role_table'); //数据刷新
          		                 layer.close(index); //关闭弹层
	          		             layer.msg(data.msg, {
	       		  	                icon: 1
	       		  	            });
          		        	}else{
          		        		layer.msg(data.msg, {
        		  	                icon: 2
        		  	            });
          		        	}
          		        	layer.close(loadId);
          		          
          		        }
          		    }); 
                   
                  });  
                  
                  submit.trigger('click');
                }
              }); 
        break;
      };
    });
  
    
   
  });
  
  function getReqData(field){
	  var req="";
      //这样做是为了替换复选框的name
      for(var key in field){
      	if(key.indexOf("permissionIds")>=0){
      		req+="permissionIds="+field[key]+"&"
      	}else if(key.indexOf("permissionItem")>=0){
      		req+="permissionItem="+field[key]+"&"
      	}else{
      		req+=key+"="+field[key]+"&"
      	}
      }
      return req;
  }
  </script>
</body>
</html>