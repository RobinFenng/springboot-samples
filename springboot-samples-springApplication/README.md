#SpringApplication#



SpringApplication提供了一个便捷的启动spring应用的方法，那就是从`main`方法开始.在大多数情况下，你只需要调用`SpringApplication.run`方法即可。如下：

	public static void main(String[] args) {
	    SpringApplication.run(MySpringConfiguration.class, args);
	}


启动之后，你就会看到如下内容：

	  .   ____          _            __ _ _
	 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	  '  |____| .__|_| |_|_| |_\__, | / / / /
	 =========|_|==============|___/=/_/_/_/
	 :: Spring Boot ::   v1.5.1.RELEASE
	
	2013-07-31 00:08:16.117  INFO 56603 --- [           main] o.s.b.s.app.SampleApplication            : Starting SampleApplication v0.1.0 on mycomputer with PID 56603 (/apps/myapp.jar started by pwebb)
	2013-07-31 00:08:16.166  INFO 56603 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6e5a8246: startup date [Wed Jul 31 00:08:16 PDT 2013]; root of context hierarchy
	2014-03-04 13:09:54.912  INFO 41370 --- [           main] .t.TomcatEmbeddedServletContainerFactory : Server initialized with port: 8080
	2014-03-04 13:09:56.501  INFO 41370 --- [           main] o.s.b.s.app.SampleApplication            : Started SampleApplication in 2.992 seconds (JVM running for 3.658)


通常默认会打出`INFO`级别的日志，包括一些重要的启动信息

## Startup failure ##

:

如果你的应用启动失败，注册`FailureAnalyzers`你就会看到一个详细的报错信息以及修正的方法。例如：如果你使用8080端口启动你的应用，并且8080端口已经被占用了，你就能看到如下信息：

	***************************
	APPLICATION FAILED TO START
	***************************
	
	Description:
	
	Embedded servlet container failed to start. Port 8080 was already in use.
	
	Action:
	
	Identify and stop the process that's listening on port 8080 or configure this application to listen on another port.
	



**注**：Spring Boot已经提供了几个FailureAnalyzer实现，并且你也可以很随意的添加你自己的实现。


**注：**

1. `FailureAnalyzer`仅限于应用启动时提示。
2. 继承`AbstractFailureAnalyzer`类，自定义一个`exception`。
3. 在`resources/META-INF/spring.factories`中添加`org.springframework.boot.diagnostics.FailureAnalyzer=com.moregx.analyzers.CustomerFailureAnalyzer`

