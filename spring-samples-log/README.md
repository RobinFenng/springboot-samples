#Spring Boot之 Logback #



----------
Spring Boot 的日志信息默认使用的是Logback ，但对其他（如：log4j,SLF4J）依然支持。

## 日志级别 ##

日志级别包含：`TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `OFF`(Logback没有`FATAL`,对应的`ERROR`)


设置日志基本由`logging.level` + 包名构成。比如你要设置包` com.moregx.service`下的日志基本为`INFO`的话，你可以如下设置：


	logging:
	  level:
	    com.moregx.service:  INFO




## 自定义日志配置 ##

Spring Boot建议使用`logback-spring.xml`代替`logback.xml`,因为`logback.xml`加载的时间比较早，Spring无法完全控制。

<table style="border-collapse: collapse;border-top: 0.5pt solid ; border-bottom: 0.5pt solid ; border-left: 0.5pt solid ; border-right: 0.5pt solid ;">
<tr><td><b>Spring配置（application.properties）</b></td><td><b>系统属性（配置在logback-spring.xml中）</b></td></tr>
<tr><td>logging.file
</td><td>LOG_FILE</td></tr>
<tr><td>logging.path</td><td>LOG_PATH</td></tr>
</table>

##使用springProperty读取配置文件信息##

在Logback中，你可以使用`<springProperty>`标签读取Spring配置文件信息，例如：

application.yml:

	my:
	   logback:
	     attr: e:/logs

logback-spring.xml:
	
	<?xml version="1.0" encoding="UTF-8"?>
	<configuration debug="true" scan="true" scanPeriod="30 seconds">
	
	     <!-- 读取spring配置信息 --> 
	     <springProperty scope="context" name="attr" source="my.logback.attr" defaultValue="${LOG_PATH}"/>
	     
	     <property name="PATTERN" value="%d{yyyy-MM-dd HH:mm:ss} [%level] [%class:%line] - %m %n" /> 
	     <property name="FILE_PATH" value="${attr}/root/root.log" />   
	     <property name="SERVICE_FILE_PATH" value="${attr}/service/service.log" />  
	     <property name="HTTP_FILE_PATH" value="${attr}/http/http.log" />  
		...
	</configuration>


