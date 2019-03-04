<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块列表</title>
<%@include file="/common/header.jsp"%>
</head>
<body>
	<div class="layui-fluid">
    <div class="layui-card">
      <div class="layui-card-body">
	        <script type="text/html" id="toolbar">
       		 <div  class="layui-btn-container">
        	  <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
      	     </div>
             </script>
        <table id="module_table" lay-filter="module_table"></table>
       
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
    
  //列表table
    table.render({
      elem: '#module_table'
      ,url: '/module/listModule' 
       ,toolbar: '#toolbar',
       defaultToolbar: ['filter', 'print']
      ,cols: [[
		{type:'numbers'}
        ,{field: 'id', width: 100, title: 'ID', sort: true}
    
        ,{field: 'name', width: 150, title: '模块名称'}
       
        ,{field: 'remark', minWidth: 100,title: '备注'}
   
        ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table_tool'}
      ]]
      ,page: true
      ,limit: 20
      //,height: 'full-220'
      ,text: '对不起，加载出现异常！'
    });
  
    
    //监听工具条
    table.on('tool(module_table)', function(obj){
      var data = obj.data;
      if(obj.event === 'del'){
    	  layer.confirm('确认删除行么', function(index){
    		  var field = {"id" : data.id};
              var url = "/module/deleteModule";
              var param = {"field":field,"index":index,"table":table,"layer":layer,"url":url,"tableId":"module_table"};
              ajaxSubmit(param);
              
          });
      } else if(obj.event === 'edit'){
        var tr = $(obj.tr);

        layer.open({
          type: 2
          ,title: '编辑模块'
          ,content: '/module/editModule?id='+data.id
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
              var url = "/module/updateModule";
              var param = {"field":field,"index":index,"table":table,"layer":layer,"url":url,"tableId":"module_table"};
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
    table.on('toolbar(module_table)', function(obj){
      var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
      switch(obj.event){
        case 'add':
        	layer.open({
                type: 2
                ,title: '添加模块'
                ,content: '/module/addModule'
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
                    var url = "/module/saveModule";
                    var param = {"field":field,"index":index,"table":table,"layer":layer,"url":url,"tableId":"module_table"};
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