此项目采用的框架有：springmvc+mybatis+redis+dubbo+zookeeper+quartz+layui
my-service文件结构：
1、my-server工程是提供server的，负责处理数据库相关的操作，最终打成war包。
2、my-common工程存放一些公共常量、方法及接口等代码，最终会打成jar包。
3、my-client工程存放连接dubbo服务的配置参数，供客户端使用，最终打成jar包。

asynch：定时任务项目，每隔30分钟从数据库里面读取一次任务配置（调用my-server服务），有新的job就加入进来。根据配置的cron表达式执行对应的任务。
此工程里面有一个layui自动签到程序的定时任务。需要用到额外的一个python脚本。有兴趣的可以到此链接去下载
https://github.com/guo5253142/layui
或者注释掉这个定时任务，就能运行了。

my-admin：后台管理系统-用户角色权限模板。用来创建用户、菜单，管理用户权限的项目（调用my-server服务）。
前端页面框架使用的是layui后台管理系统的模板（因为使用的是收费版，有版权问题，所以我把layui的核心js都删掉了，想要的可以给我留言）

