<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/js/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet" href="/resources/js/layuiadmin/style/admin.css" media="all">

<!-- <script src="/resources/js/jquery-1.7.2.min.js"></script> -->
<script src="/resources/js/layuiadmin/layui/layui.js"></script>

<script>
var $;
  layui.config({
    base: '/resources/js/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index','jquery'],function(){
	  $=layui.$;
  });
  
  //标准ajax异步提交，带加载层
  function ajaxSubmit(param){
	  var table= param.table||''
	      //,layer=param.layer
	  	  ,field=param.field||''
	  	  ,index=param.index||''
	  	  ,tableId=param.tableId
	  	  ,type=param.type||'GET'
	  	  ,url=param.url;
	  var loadId = layer.load(1, {
          shade: [0.3,'#000']
        });
	  $.ajax({     
	        url: url,
	        type: type,
	        data: field,
	        dataType: "json",
	        //async: false,
	        success: function (data) {
	        	if(data.status=="1"){
	        		if(table!=''){
	        			var tid= tableId||'lay_table';
		        		table.reload(tid); //数据刷新
	        		}
	        		if(index!=''){
	        			layer.close(index);
	        		}
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
  }
  
  
</script>