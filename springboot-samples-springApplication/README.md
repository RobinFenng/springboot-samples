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


## Application events and listeners ##


除了像`ContextRefreshedEvent`这些spring通常的events，SpringApplication还提供了一些额外的events。



因为一些events在ApplicationContext创建之前被触发，所有你为他们注册一个监听。你可以使用`SpringApplication.addListener（）` 或者`SpringApplicationBuilder.listeners(…​)`去注册他们。
如果你想在各种情况下都自动注册这些监听的话， 你可以建一个 `META-INF/spring.factories`文件，然后设置
    
	org.springframework.context.ApplicationListener=com.example.project.MyListener


Application events are sent in the following order, as your application runs:

当应用运行时，Application events是按照如下顺序执行的：



1. 在运行开始，除了监听器注册和初始化以外，会发送一个`ApplicationStartingEvent`
2. 在Environment被用于已知的上下文时，但在上下文创建之前，会发送一个`ApplicationEnvironmentPreparedEvent`
3. 在refresh开始前，但在bean定义已被加载后，会发送一个`ApplicationPreparedEvent`。
4. 启动过程中如果出现异常，会发送一个`ApplicationFailedEvent`


**注**:你不会经常使用application实践，但是会很方便的了解他们的存在。事实上，spring boot运用事件做了很多事情。

## Web environment ##


SpringApplication会为你创建一个正确类型的ApplicationContext，通常会根据你的应用是否为web应用选择`AnnotationConfigApplicationContext`或`AnnotationConfigEmbeddedWebApplicationContext`。

判断是不是一个web容器很简单（通常根据几个类是否存在就行了），你也可以调用`setWebEnvironment(boolean webEnvironment)`覆盖默认的设置。

你可以完全控制ApplicationContext类型，那就是调用`setApplicationContextClass（）`方法。


**注**：当用JUnit测试SpringApplication的时候，建议设置`setWebEnvironment(false)`


##获取application参数##



如果你想获取通过`SpringApplication.run(…​)`启动的应用的参数，你可以注入一个`org.springframework.boot.ApplicationArguments`bean.ApplicationArguments接口提供了访问原始的String[]参数解析选项以及非选项参数：

	import org.springframework.boot.*
	import org.springframework.beans.factory.annotation.*
	import org.springframework.stereotype.*
	
	@Component
	public class MyBean {
	
	    @Autowired
	    public MyBean(ApplicationArguments args) {
	        boolean debug = args.containsOption("debug");
	        List<String> files = args.getNonOptionArgs();
	        // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
	    }
	
	}


**注**：Spring Boot也通过Spring Environment.`注册了一个CommandLinePropertySource`。这允许你用`@Value`注解注入一个application参数。

## 使用ApplicationRunner或CommandLineRunner ##



当SpringApplication启动之后，如果你想运行一些代码，你可以实现ApplicationRunner或CommandLineRunner接口.这两个接口都会提供一个方法运行在`SpringApplication.run(…​) `完全运行之前。

`CommandLineRunner`接口有一个获取String数组参数，而`ApplicationRunner`则有一个`ApplicationArguments`参数。

CommandLineRunner:

	package com.moregx.runner;
	
	import org.springframework.boot.CommandLineRunner;
	
	public class MyBeanCommandLineRunne implements CommandLineRunner {
	
		@Override
		public void run(String... args) throws Exception {
			// TODO Auto-generated method stub
	
		}
	
	}

ApplicationRunner：

	package com.moregx.runner;
	
	import org.springframework.boot.ApplicationArguments;
	import org.springframework.boot.ApplicationRunner;
	
	public class MyBeanApplicationRunner implements ApplicationRunner {
	
		@Override
		public void run(ApplicationArguments args) throws Exception {
			// TODO Auto-generated method stub
	
		}
	
	}




此外，如果你用多个CommandLineRunner或ApplicationRunner的bean,并且要有先后顺序的话，你可以实现`org.springframework.core.Ordered`或使用`org.springframework.core.annotation.Order` 注解



## Application exit ##


每个SpringApplication都会在JVM里注册一个钩子，以确保ApplicationContext优雅的退出。所有标准的Spring生命回调（如：DisposableBean接口和@PreDestroy注解）都会被使用到。

## Admin features ##

通过设置`spring.application.admin.enabled`属性，就可以开启admin-related特性。SpringApplicationAdminMXBean会暴露在MBeanServer上，你可以运用这个特性去管理你的Spring Boot应用。这个可能对于任何一个服务都是有用的。

**注**：如果你想查看http的端口，你可以使用`local.server.port`属性。

**注**：当你开启开启这个特性时，要注意`shutdown`方法。

Mbean的查看方法：

1. 在控制台输入`jconsole`，启动jvm自带的控制台。
2. 选择你的应用。
3. 选择到MBean tab。
4. 查看`org.springframework.boot.Admin.SpringApplication`。

你也可以自己定义MBean,只需实现`SpringApplicationAdminMXBean`即可，查看方法如上。

**注**：


1. 当设置`spring.application.admin.enabled=ture`时，你可以查到`org.springframework.boot.Admin.SpringApplication`，通过`getProperty()`方法,你可以查到`application.properties`中的配置信息。
2. 当设置`spring.application.admin.enabled=false`时,将不会查到`org.springframework.boot.Admin.SpringApplication`。
3. 无论`spring.application.admin.enabled`等于什么，都能查到自定义的`SpringApplicationAdminMXBean`

DEMO URL:[https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-springApplication](https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-springApplication "SpringApplication实例")