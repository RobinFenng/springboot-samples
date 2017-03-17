#外化配置#


Spring Boot允许你在外部进行配置参数，所以你可以使用相同的代码在不同的环境中。你可以使用properties文件，YAML文件，environment参数，命令行参数去外化配置。这些值可以直接被你注入到含有@Value注解的bean中，并通过Spring的环境抽象或绑定到结构化对象来访问。


Spring Boot使用了一个独特的`PropertySource`次序来允许属性值被合理的覆盖。需要以下面的次序来考虑属性赋值：


1. Devtools全局设置在你的home路径（`~/.spring-boot-devtools.properties`当debtools有效的时候）
2. 测试用例上的 `@TestPropertySource`。
3. 测试用例上的 `@SpringBootTest#properties`。
4. 命令行的参数
5. `SPRING_APPLICATION_JSON`的属性（内联在JSON中的运行环境参数或系统属性）
6. `ServletConfig`初始化参数。
7. `ServletContext`初始化参数。
8. `java:comp/env`中的JNDI属性。
9. JAVA系统属性。
10. 系统环境属性。
11. A RandomValuePropertySource that only has properties in random.* ??
12. 没有打包到jar中的`Profile-specific`应用属性（包括`application-{profile}.properties` and YAML variants）
13. 被打包到jar中的`Profile-specific`应用属性（包括`application-{profile}.properties` and YAML variants）
14. 没有打包到jar中的Application属性（包括`application.properties` and YAML variants）
15. 被打包到jar中的Application属性（包括`application.properties` and YAML variants）
16. 在你配置类上的`PropertySource`注解
17. 默认属性（使用`SpringApplication.setDefaultProperties`指定）


To provide a concrete example, suppose you develop a @Component that uses a name property:

你可以创建一个@Component使用names属性,以用来更好的理解：

	import org.springframework.stereotype.*
	import org.springframework.beans.factory.annotation.*
	
	@Component
	public class MyBean {
	
	    @Value("${name}")
	    private String name;
	
	    // ...
	
	}



在classpath中的`application.properties`中,你可以设置一个`name`的默认属性.当运行一个新的环境的话,外部的 `application.properties`可以覆盖掉默认的值;一次性测试,你可以使用命令行（例如:(`e.g. java -jar app.jar --name="Spring"`）


**注：**

- SPRING_APPLICATION_JSON属性可以使用命令行设置一个环境变量，例如在unix shell中：`SPRING_APPLICATION_JSON='{"foo":{"bar":"spam"}}' java -jar myapp.jar`
- 你也可以用在spring环境中以`foo.bar=spam`结尾，也可以在系统变量中提供`spring.application.json`,如：`java -Dspring.application.json='{"foo":"bar"}' -jar myapp.jar`或者使用命令行: `java -jar myapp.jar --spring.application.json='{"foo":"bar"}'`或者使用JNDI值：`java:comp/env/spring.application.json`



---
以上为翻译内容,示例次序如下：

1. `~/.spring-boot-devtools.properties` 指的是在`C:\Users\yourName` 下添加`.spring-boot-devtools.properties`文件,‘**.**’很重要。
2. 测试用例中` @TestPropertySource` 注解的properties的值。
3. 测试用例中`@SpringBootTest`注解的properties的值。
4. 使用命令行进行赋值,如：`java -jar app.jar --name="name in command level 4`"
5. 内联在环境变量或系统参数中的`SPRING_APPLICATION_JSON`属性。
6. ServletConfig参数 ？
7. 在application.properties中配置`ServletContext`初始化参数`server.context-parameters.name : name in ServletContext init parameter level 7`
8. JNDI ？
9. java 系统变量 `System.setProperty("name", "name in Java System properties  level 9");`
10. 系统环境变量 ？
11. `RandomValuePropertySource` ？
12. 打包在应用外明确环境的配置文件的配置信息。`java -jar -Dspring.config.location=D:\application-test.properties app.jar`
13. 打包在应用内明确环境的配置文件的配置信息。
14. 打包在应用外的application.properties配置，使用命令`java -jar -Dspring.config.location=D:\application.properties app.jar` 启动应用。
15. 打包在应用中的application.properties的配置。
16. 在配置类上的PropertySource注解指向的配置。
17. `SpringApplication.setDefaultProperties`设置默认值。



##配置随机值##


RandomValuePropertySource对注入随机值是很有效的（比如设置密码或用户测试）. 可以提供 integers, longs, uuids or strings类型，例如：

	my.secret=${random.value}
	my.number=${random.int}
	my.bignumber=${random.long}
	my.uuid=${random.uuid}
	my.number.less.than.ten=${random.int(10)}
	my.number.in.range=${random.int[1024,65536]}

random.int*语法是OPEN value (,max) CLOSE，此处OPEN，CLOSE可以是任何字符，并且value，max是整数。如果提供max，那么value是最小的值，max是最大的值（不包含在内）。


## 获得命令行参数 ##

`SpringApplication`会转换命令行参数并把他们添加到`Spring`环境中去.综上所述，命令行参数会优先于其他配置文件中的参数.

如果你不想命令行参数添加到环境中，你可以使用`SpringApplication.setAddCommandLineProperties(false)`禁掉.


## Application属性文件##


SpringApplication会从以下位置按照次序加载application.properties中的属性加载到Spring环境中：


1. 当前目录的/config 子目录
2. 当前目录
3. classpath中的config包
4. classpath跟目录

**注**：你也可以用` YAML ('.yml') files `文件替代`.properties`


如果你不想使用application.properties作为配置文件的名称，你可以使用a spring.config.name属性。你也可以使用spring.config.location属性去指向明确的目录加载。如下：

    $ java -jar myproject.jar --spring.config.name=myproject

或
	
	$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties



**注**： `spring.config.name` 和 `spring.config.location`会很早决定那些文件被加载，所以两个属性都会被作为环境属性定义（比如：OS参数，系统参数或命令行参数）



如果spring.config.location contains包含目录(而不是文件)，那他们应该以/结尾，（在加载前，spring.config.name产生的名称将被追加到后面）。Files specified in spring.config.location are used as-is, with no support for profile-specific variants, and will be overridden by any profile-specific properties.？


无论spring.config.location设置什么值，它默认会搜索`classpath:,classpath:/config,file:,file:config/ `目录.搜索目录是从低到高排序的（`file:config/` 优先），如果你想指定你自己的目录优先于默认的加载目录，你可以在`application.properties`中设置一个默认值，然后在运行的时候使用不同的文件覆盖它，同时保留默认配置。

## 特定的配置文件 ##


除了application.properties文件，也可以使用以application-{profile}.properties来命名的配置文件来设置属性。如果没有指定的话（如果没有明确激活的话，会加载 `application-default.properties`），环境有一组默认的配置文件（默认为：[default]）

特定的配置文件和标准的配置文件加载路径相同，特定的配置文件通常会覆盖默认配置无论是否被你打包到jar内部或外部。

## 属性文件中的占位符 ##

The values in application.properties are filtered through the existing Environment when they are used so you can refer back to previously defined values (e.g. from System properties).

配置在application.properties中的属性，你可以使用存在的配置.例如：

	app.name=MyApp
	app.description=${app.name} is a Spring Boot application


## 使用YAML替代Properties ##


YAML是一个JSON的超级，这是一个非常方便的拥有层级结构的配置数据。无论你是否使用SnakeYAML,SpringApplication类会自动支持YAML.


### 加载YAML ###

Spring Framework 提供了两个便利的类用于加载YAML文件.YamlPropertiesFactoryBean会将YAML作为Properties处理.YamlMapFactoryBean会将YAML作为Map处理.

例如，如下YAML：

	environments:
	    dev:
	        url: http://dev.bar.com
	        name: Developer Setup
	    prod:
	        url: http://foo.bar.com

	        name: My Cool App

将会被转译为：

	environments.dev.url=http://dev.bar.com
	environments.dev.name=Developer Setup
	environments.prod.url=http://foo.bar.com
	environments.prod.name=My Cool App


YAML列表被表示成使用[index]间接引用作为属性keys的形式，例如下面的YAML：

	my:
	   servers:
	       - dev.bar.com
	       - foo.bar.com
	       


将会被转化为：
	
	my.servers[0]=dev.bar.com
	my.servers[1]=foo.bar.com


To bind to properties like that using the Spring DataBinder utilities (which is what @ConfigurationProperties does) you need to have a property in the target bean of type java.util.List (or Set) and you either need to provide a setter, or initialize it with a mutable value, e.g. this will bind to the properties above

要像使用Spring DataBinder utilities那样绑定属性的话，你需要目标bean有一个java.util.List或Set的属性，然后有提供一个setter方法或者初始化一个可变的值，例如：

	@ConfigurationProperties(prefix="my")
	public class Config {
	    private List<String> servers = new ArrayList<String>();
	    public List<String> getServers() {
	        return this.servers;
	    }
	}



### 在Spring环境中暴露YAML属性 ###

`YamlPropertySourceLoader`类能够用于把YAML的属性暴露在Spring环境中。这允许你使用属性的@Value注解和占位符访问YAML属性。

### 多环境YAML文件 ##

You can specify multiple profile-specific YAML documents in a single file by using a spring.profiles key to indicate when the document applies. For example:


你可以使用一个文件配置多个环境配置，通过 spring.profiles 属性指明环境，例如：

	server:
	    address: 192.168.1.100
	---
	spring:
	    profiles: development
	server:
	    address: 127.0.0.1
	---
	spring:
	    profiles: production
	server:
	    address: 192.168.1.120




上面那个例子，如果当前使用的是development环境，server.address为127.0.0.1。如果当前既不是development环境，也不是production环境，server.address则为192.168.1.100。


当应用上下文启动时，如果没有任何环境被指定，就会启用default配置。所有我们设置的这个security.user.password只会在default被激活：

	server:
	  port: 8000
	---
	spring:
	  profiles: default
	security:
	  user:
	    password: weak



在这个例子中，无论如何这个password的属性都会被设置，但是他会被其他指定配置的属性覆盖：

	server:
	  port: 8000
	security:
	  user:
	    password: weak


### YAML的缺点###


YAML不能被@PropertySource加载，所以你想要这种加载值的话，你需要使用properties文件。

### Merging YAML lists ###

assume a MyPojo object with name and description attributes that are null by default. Let’s expose a list of MyPojo from FooProperties:

假设有一个MyPojo对象，里面有name和description两个属性，默认值都为null.现在我们要是在FooProperties里面设置一个MyPojo的集合。

	@ConfigurationProperties("foo")
	public class FooProperties {
	
	    private final List<MyPojo> list = new ArrayList<>();
	
	    public List<MyPojo> getList() {
	        return this.list;
	    }
	
	}


注意如下配置：
	
	foo:
	  list:
	    - name: my name
	      description: my description
	---
	spring:
	  profiles: dev
	foo:
	  list:
	    - name: my another name
	    


如果dev配置没有激活，像上面说的那样，FooProperties.list将会有一个MyPojo对象。然而如果dev被激活的话，这个list仍然会只有一个MyPojo对象（name为my another name，description为null）.这个配置不会添加第二个实例到list中，并不会合并list.



当一个集合被多个环境配置指定的花，最高优先权的那个会被使用（当且仅当此一个）

	foo:
	  list:
	    - name: my name
	      description: my description
	    - name: another name
	      description: another description
	---
	spring:
	  profiles: dev
	foo:
	  list:
	     - name: my another name



上面这个例子就是环境指定了dev,FooProperties.list只会有一个MyPojo对象并且name为my another name，description为空。


##  Type-safe Configuration Properties ##



使用`@Value("${property}")`注入到你配置中有时是笨重的，特别是你有多个配置文件或者你的数据是多层次的。Spring Boot提供了另一个种允许强类型方法让你去管理和校验你的配置


	package com.moregx;
	
	import java.net.InetAddress;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.List;
	
	import org.springframework.boot.context.properties.ConfigurationProperties;
	
	@ConfigurationProperties("foo")
	public class FooProperties {
	
		private boolean enabled;
	
		private InetAddress remoteAddress;
	
		private final Security security = new Security();
	
		public boolean isEnabled() {
			return enabled;
		}
	
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	
		public InetAddress getRemoteAddress() {
			return remoteAddress;
		}
	
		public void setRemoteAddress(InetAddress remoteAddress) {
			this.remoteAddress = remoteAddress;
		}
	
		public Security getSecurity() {
			return security;
		}
		
		public class Security {
	
			private String username;
	
			private String password;
	
			private List<String> roles = new ArrayList<String>(Collections.singleton("USER"));
	
			public String getUsername() {
				return username;
				}
		
				public void setUsername(String username) {
					this.username = username;
				}
		
				public String getPassword() {
					return password;
				}
		
				public void setPassword(String password) {
					this.password = password;
				}
		
				public List<String> getRoles() {
					return roles;
				}
		
				public void setRoles(List<String> roles) {
					this.roles = roles;
				}
				
				
				
		
			}
		
			
		}


	


上面这个POJO定义了以下几个properties：

- foo.enabled,默认为false
- foo.remote-address,可以从String强转
- foo.security.username 
- foo.security.password
- foo.security.roles 





**注：** Getters 和 setters通常是强制要求的，因为像在Spring MVC中一样，是通过标准的Java Beans property descriptors来绑定的.但是在有些情况下，setter是可以忽略的：

- Maps，因为它们在初始化的时候，需要getter,但是不需要setter,因为他们可以被binder转换的
- Collections 和 arrays可以通过index或者使用逗号分隔符。setter是必要的，建议你在这些类型中加一个setter,如果你初始化一个集合，确认它不是不可变的
- 如果嵌套的POJO属性初始化，setter是不需要的



你还需要列出的属性类注册在@EnableConfigurationProperties中：

	@Configuration
	@EnableConfigurationProperties(FooProperties.class)
		public class MyConfiguration {
	}



Even if the configuration above will create a regular bean for FooProperties, we recommend that @ConfigurationProperties only deal with the environment and in particular does not inject other beans from the context. Having said that, The @EnableConfigurationProperties annotation is also automatically applied to your project so that any existing bean annotated with @ConfigurationProperties will be configured from the Environment. You could shortcut MyConfiguration above by making sure FooProperties is a already a bean:


即使这个configuration将会为FooProperties创建一个正常的bean,我们也建议 `@ConfigurationProperties`只处理环境的问题，特别是从上下文中不注入其他bean的时候.话虽如此，@EnableConfigurationProperties注解也会自动用于项目以确保任何被@ConfigurationProperties注解的bean都会被环境配置。


	@Component
	@ConfigurationProperties(prefix="foo")
	public class FooProperties {
	
	    // ... see above
	
	}


### 第三方配置 ###

As well as using @ConfigurationProperties to annotate a class, you can also use it on public @Bean methods. This can be particularly useful when you want to bind properties to third-party components that are outside of your control.

像把@ConfigurationProperties注解到一个类上一样，你也可以在@Bean方法上使用它。。当你需要绑定属性到不受你控制的第三方组件时，这种方式非常有用。

为了从Environment属性配置一个bean，将@ConfigurationProperties添加到它的bean注册过程：
	
	@ConfigurationProperties(prefix = "bar")
	@Bean
	public BarComponent barComponent() {
	    ...
	}

所有以`bar`开头的属性都会被映射到BarComponent上

### 松散的绑定 ###

Spring Boot uses some relaxed rules for binding Environment properties to @ConfigurationProperties beans, so there doesn’t need to be an exact match between the Environment property name and the bean property name. Common examples where this is useful include dashed separated (e.g. context-path binds to contextPath), and capitalized (e.g. PORT binds to port) environment properties.


Spring Boot使用了一些松散的规则用于把环境属性绑定到`@ConfigurationProperties`上，所以不需要精确字符匹配环境属性名称和bean属性名称.在使用虚线分割和大小写的时候是很用的。

例如，下面这个@ConfigurationProperties类：
	
	@ConfigurationProperties(prefix="person")
	public class OwnerProperties {
	
	    private String firstName;
	
	    public String getFirstName() {
	        return this.firstName;
	    }
	
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }
	
	}


下面这些名称都是可以的：

	person.firstName 	标准的驼峰式大小写语法。
	person.first-name   虚线
	person.first_name   下划线
	PERSON_FIRST_NAME	大写，建议使用在环境变量中



### 属性转换 ## 

