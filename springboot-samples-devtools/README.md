## Developer tools ##

Spring Boot includes an additional set of tools that can make the application development experience a little more pleasant. The spring-boot-devtools module can be included in any project to provide additional development-time features. To include devtools support, simply add the module dependency to your build:

Spring Boot为了让你写代码的时候更爽一点提供了一系列的工具.在你的开发任何项目的时候,你都可以引入`spring-boot-devtools`模块.引入`spring-boot-devtools`模块很简单:

**Maven.**

	<dependencies>
	    <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-devtools</artifactId>
	        <optional>true</optional>
	    </dependency>
	</dependencies>

## Property defaults ##


Spring Boot为提高效率提供了若干个缓存的工具包.比如:模板引擎将会缓存编译后的文件，以避免每次都重新编译.同样， 当服务端是静态的时候，Spring MVC 也会缓存http的头信息.

在生产环境有时候缓存会很有效果，但是也有一些缺点，比如不能实时的看到你改动的效果.为此， `spring-boot-devtools `会默认disable掉这些缓存配置.

缓存的配置通常会在你的`application.properties`文件中.例如,`Thymeleaf`提供了`spring.thymeleaf.cache`属性.与其你需要设置这些属性在开发中, `spring-boot-devtools`模块还会自动在你的开发中接收这些配置(?).


**注**：完整的缓存配置列表可以在[这里](https://github.com/spring-projects/spring-boot/blob/v1.4.3.RELEASE/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java)查看.


## Automatic restart ##

当在`classpath`上的文件被修改时,`spring-boot-devtools`会重启你的应用.当你在使用IDE开发的时候，这个功能是一个很有效的,因为它会对你的修改进行及时的反馈.因为在`classpath`上的任意文件被修改都会被监测到,所以我们排除那些无需重启的静态文件.

### Excluding resources ###

Certain resources don’t necessarily need to trigger a restart when they are changed. For example, Thymeleaf templates can just be edited in-place. By default changing resources in /META-INF/maven, /META-INF/resources ,/resources ,/static ,/public or /templates will not trigger a restart but will trigger a live reload. If you want to customize these exclusions you can use the spring.devtools.restart.exclude property. For example, to exclude only /static and /public you would set the following:


在我们的开发过程中,肯定会有一些修改之后不需要重启的文件.比如，Thymeleaf模版被修改.默认在`/META-INF/maven`, `/META-INF/resources` ,`/resources` ,`/static` ,`/public` or `/templates`的文件被修改的话,只会重新加载而不会重启.如果你想设置在某个目录的修改不触发重启的话,你可以使用`spring.devtools.restart.exclude `属性.例如，你想在目录`/static`和`/public`下的修改不触发重启的话，你可以如下配置：

	spring.devtools.restart.exclude=static/**,public/**



**注**：如果你想保持默认的，而又想增加一下其他目录的话,你可以使用`spring.devtools.restart.additional-exclude`替代.


### Watching additional paths ###


当你在修改的文件不在你的classpath上,而你又想重启你的应用的时候.这样你就可以使用`spring.devtools.restart.additional-paths`属性了.

### Disabling restart ###

如果你不想对应用进行重启,你可以使用`spring.devtools.restart.enabled`属性，大多数你都可以设置在application.properties里面.

如果你想完全disable掉对重启的支持，比如说，由于不支持某个类库，你就要要设置一个System property在SpringApplication.run(…​)之前（？）. 如下:

	public static void main(String[] args) {
	    System.setProperty("spring.devtools.restart.enabled", "false");
	    SpringApplication.run(MyApp.class, args);
	}


##LiveReload##


`spring-boot-devtools`内置了一个`LiveReload`服务，当你修改资源的时候自动触发浏览器的刷新。在[livereload.com](http://livereload.com/extensions/)上能找到Chrome, Firefox and Safari的扩展程序。

如果你不想自动刷新，你可以设置 `spring.devtools.livereload.enabled` 为 `false`.


**注**：一次只能运行一个LiveReload服务，当你启动应用的时候，确保只有一个LiveReload服务.如果你在IDE中启动了多个应用，只能是第一个应用会支持LiveReload

##Remote applications##


Spring Boot developer tools不只局限于本地的开发.也有一些特性用于远程应用程序.加入远程应用的特性，你要设置spring.devtools.remote.secret属性.例：

	spring.devtools.remote.secret=mysecret


**注**：开启远程调用是有安全隐患的，不要在生产环境上开启.


`Remote devtools`支持两部分;一个是服务端接受连接，一个是在你IDE的客户端.当你设置 `spring.devtools.remote.secret property`属性的时候，服务端会自动开启.客户端要手动开启.

###Running the remote client application###

在你的IDE中运行远程的客户端应用.运行与服务端项目路径的`org.springframework.boot.devtools.RemoteSpringApplication`，你要把服务端的URL传到参数里.


例如：如果你现在在使用eclipse或STS，现在你有一个部署在云端的项目叫做my-app,你将进行如下操作:

- 从`run`菜单选择` Run Configurations…`
- 创建一个java 应用‘启动配置’
- 浏览my-app应用
- 运行o`rg.springframework.boot.devtools.RemoteSpringApplication`，并加入你的服务端url的参数

A running remote client will look like this:
一个运行的远程客户端会这样：

	 .   ____          _                                              __ _ _
	 /\\ / ___'_ __ _ _(_)_ __  __ _          ___               _      \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` |        | _ \___ _ __  ___| |_ ___ \ \ \ \
	 \\/  ___)| |_)| | | | | || (_| []::::::[]   / -_) '  \/ _ \  _/ -_) ) ) ) )
	  '  |____| .__|_| |_|_| |_\__, |        |_|_\___|_|_|_\___/\__\___|/ / / /
	 =========|_|==============|___/===================================/_/_/_/
	 :: Spring Boot Remote :: 1.4.3.RELEASE
	
	2015-06-10 18:25:06.632  INFO 14938 --- [           main] o.s.b.devtools.RemoteSpringApplication   : Starting RemoteSpringApplication on pwmbp with PID 14938 (/Users/pwebb/projects/spring-boot/code/spring-boot-devtools/target/classes started by pwebb in /Users/pwebb/projects/spring-boot/code/spring-boot-samples/spring-boot-sample-devtools)
	2015-06-10 18:25:06.671  INFO 14938 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@2a17b7b6: startup date [Wed Jun 10 18:25:06 PDT 2015]; root of context hierarchy
	2015-06-10 18:25:07.043  WARN 14938 --- [           main] o.s.b.d.r.c.RemoteClientConfiguration    : The connection to http://localhost:8080 is insecure. You should use a URL starting with 'https://'.
	2015-06-10 18:25:07.074  INFO 14938 --- [           main] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
	2015-06-10 18:25:07.130  INFO 14938 --- [           main] o.s.b.devtools.RemoteSpringApplication   : Started RemoteSpringApplication in 0.74 seconds (JVM running for 1.105)



**注**：因为远程客户端和实际应用使用相同的classpath,所以应用可以直接读取application properties.这也是为什么`spring.devtools.remote.secret` 属性会被读取到，并且通过服务端的验证.

**注**：通常建议你使用**https**,这样会在通信中会被加密，密码不会被拦截到.

**注**：如果你要使用一个代理连接远程应用,你可以配置`spring.devtools.remote.proxy.host` 和 `spring.devtools.remote.proxy.port`属性.​


###Remote update ###


当你在重新启动本地应用的时候,客户端会监视你都改变了那些文件.所有改动的文件都将会上传到远程服务端并且触发重启.当你正在迭代一个被部署在cloud service功能,而且本地却没有的(?)，是非常有帮助的.通常远程升级和重启要比进行一个完整的重新编译和部署要快多了.



**注**：只会监控到正在运行的应用的文件修改.如果你改了一个文件，然后启动文件，就会上传到服务端.

