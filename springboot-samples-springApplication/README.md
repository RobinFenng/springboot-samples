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

## 启动失败 ##



如果你的应用启动失败，注册`FailureAnalyzers`你就会看到一个详细的报错信息以及修正的方法。例如：如果你使用8080端口启动你的应用，并且8080端口已经被占用了，你就能看到如下信息：

	***************************
	APPLICATION FAILED TO START
	***************************
	
	Description:
	
	Embedded servlet container failed to start. Port 8080 was already in use.
	
	Action:
	
	Identify and stop the process that's listening on port 8080 or configure this application to listen on another port.
	



**注**：Spring Boot已经提供了几个FailureAnalyzer实现，并且你也可以很随意的添加你自己的实现。

**以上为官方文档翻译内容**



**tips:**

1. `FailureAnalyzer`仅限于应用启动时提示。
2. 继承`AbstractFailureAnalyzer`类，自定义一个`exception`。
3. 在`resources/META-INF/spring.factories`中添加`org.springframework.boot.diagnostics.FailureAnalyzer=com.moregx.analyzers.CustomerFailureAnalyzer`


示例：`https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-springApplication`


## 自定义Banner ##


你可以在classpath中添加一个banner.txt文件或设置banner.location属性去修改启动时打印出来的banner。如果你的文件是不同编码的，你可以设置banner.charset属性（默认为UTF-8）.不仅仅是文本文件，你也可以添加一个banner.gif，banner.jpg or banner.png 图片文件到你的classpath中，或者设置banner.image.location属性.图片文件会被转换为ASCII后被呈现出来。

## 自定义SpringApplication ##



如果默认的SpringApplication不适合你的应用，你可以自定义一个本地的实例去替代，例如你想关闭banner,你可以这样：

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(MySpringConfiguration.class);
	    app.setBannerMode(Banner.Mode.OFF);
	    app.run(args);
	}




**注**：传入到SpringApplication构造函数中的参数应当是一个spring beans中的配置类（？），大多数是加了`@Configuration`的配置类，但是也可以是配置在XML中的配置类或者可以被扫描到的配置类

用一个`application.properties`文件来配置这些信息也是可以的。

[这里](http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/api/org/springframework/boot/SpringApplication.html "SpringApplication")有一份完整的配置列表


