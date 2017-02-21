##Using the “default” package##

当创建一个Class而没有`package`声明的时候，它会被认为处于`default package`下。这种是不建议的做法，甚至是应该被禁止的。因为对于那些使用了`@ComponentScan`，`@EntityScan`或者`@SpringBootApplication`的Spring Boot 应用来说会引起其他问题，因为每个jar包中的每个类都会被read。

**注**：我们建议你遵循Java推荐的命名规则，即com.xxx.xxx 
。



##Locating the main application class##
我们通常建议你把main应用类放到其他类的根目录上.把`@EnableAutoConfiguration`注解加到你的main类上，这样会默认在这个‘package’搜索相关的内容.例如，如果你在写一个JPA应用，将会在这个被注解的类的包中搜索相关`@Entity`的内容.

在根包中，允许你使用`@ComponentScan`注解并且不使用`basePackage`属性.如果你的主类在根包中，你也可以使用`@SpringBootApplication`注解.

下面是一个规范的结构：

	com
	 +- example
	     +- myproject
	         +- Application.java
	         |
	         +- domain
	         |   +- Customer.java
	         |   +- CustomerRepository.java
	         |
	         +- service
	         |   +- CustomerService.java
	         |
	         +- web
	             +- CustomerController.java


Application.java 必须声明`main`方法，以及加上 `@Configuration`注解.
	
	package com.example.myproject;
	
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	
	@Configuration
	@EnableAutoConfiguration
	@ComponentScan
	public class Application {
	
	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	
	}


## Configuration classes(配置类) ##

Spring Boot 倾向于基于java的配置.尽管可以使用`SpringApplication.run()`调用一个xml,我们建议你使用一个`@Configuration`类作为主类.一般含有`main`方法的类可以作为一个很好的`@Configuration`类的候选.

**注**：在网上有很多例子基于xml的spring配置,但仍希望你尽可能使用基于java的配置.搜索以`enable*`开头的注解,可能是一个很好的切入点.


##Importing additional configuration classes（引入其他配置类）##

你不必把所有的配置都放入到一个配置类中,使用`@Import`注解可以引入其他配置类.尽管你可以使用`@ComponentScan`注解自动注册spring组件,包括`@Configuration`类.


## Importing XML configuration (引入xml配置)##

If you absolutely must use XML based configuration, we recommend that you still start with a @Configuration class. You can then use an additional @ImportResource annotation to load XML configuration files.

如果你必须要使用基于xml的配置，我们仍建议你开始于一个`@Configuration`类,你仍然可以使用`@ImportResource `引入xml的配置文件.



## Auto-configuration (自动配置)##

Spring Boot会依赖你已经加的jar进行自动配置，比如：如果你的classpath中含有`HSQLDB`
,并且你并没有配置数据库链接的bean,Spring Boot会自动配置一个内存型（in-memory）数据库.

你可以通过添加` @EnableAutoConfiguration`或`@SpringBootApplication`注解在你的`@Configuration`类上,从而选择自动配置.


**注**：你只能添加唯一一个`@EnableAutoConfiguration`注解，我们建议你加在你的主`@Configuration`类上.

##Gradually replacing auto-configuration（逐步替换自动配置）#


Auto-configuration 是非侵害的，任何时候你都可以定义自己的配置代替自动配置.比如你自己增加一个`DataSource` bean,默认内部的数据库将会被替代.

如果你想找出目前有哪些自动配置的bean,以及使用的原因,你可以使用`--debug`启动的你的应用,这样会打印出来这些信息到控制台上.


## Disabling specific auto-configuration（屏蔽自动配置） ##


如果你发现有些自动配置类是你不需要的，你可以使用`@EnableAutoConfiguration`注解的`exclude`的属性去屏蔽他们.例如：

	import org.springframework.boot.autoconfigure.*;
	import org.springframework.boot.autoconfigure.jdbc.*;
	import org.springframework.context.annotation.*;
	
	@Configuration
	@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
	public class MyConfiguration {
	}


如果在你的classpath上不存在这个类，你可以使用注解的`excludeName`的属性来替代(?)，你也可以用`spring.autoconfigure.exclude`属性来排除多个自动配置的类.


**注**：你在注解上和property属性上可以同时排除.


## Spring Beans and dependency injection ##

你现在可以任意使用spring定义你的bean以及他们的依赖.一般来说，spring会通过`@ComponentScan`注解找到你的bean,并结合` @Autowired`结构器进行依赖注入.

如果你的代码像上面一样（通过`@ComponentScan`注解,结合` @Autowired`结构器进行依赖注入.）,你可以使用`@ComponentScan`注解,并且不需要任何的参数.你应用所有的组件（`@Component`, `@Service`, `@Repository`, `@Controller` etc.）都会自动注册成spring beans.

下面是一个`@Service`的bean的示例，使用构造器注册一个`RiskAssessor`bean.
	
	package com.example.service;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	@Service
	public class DatabaseAccountService implements AccountService {
	
	    private final RiskAssessor riskAssessor;
	
	    @Autowired
	    public DatabaseAccountService(RiskAssessor riskAssessor) {
	        this.riskAssessor = riskAssessor;
	    }
	
	    // ...
	}



**注**：如果riskAssessor被定义为final,那么就表明不能被更改.

## Using the @SpringBootApplication annotation(使用@SpringBootApplication注解) ##


Many Spring Boot developers always have their main class annotated with @Configuration, @EnableAutoConfiguration and @ComponentScan. Since these annotations are so frequently used together (especially if you follow the best practices above), Spring Boot provides a convenient @SpringBootApplication alternative.


许多Spring Boot开发者都会在他们的主类上面加上`@Configuration`, `@EnableAutoConfiguration` and `@ComponentScan`.因为这些注解通常都一起用.另外Spring Boot还提供了一个 `@SpringBootApplication` 用于替代这三个注解的组合.以下是`@SpringBootApplication`的源码.


	@Documented
	@Inherited
	@SpringBootConfiguration
	@EnableAutoConfiguration
	@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
	public @interface SpringBootApplication {
	
	
	}




`@SpringBootApplication`注解与使用默认的`@Configuration`, `@EnableAutoConfiguration` and` @ComponentScan`组合的作用是等价的.

	package com.example.myproject;
	
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	
	@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
	public class Application {
	
	    public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
	
	}



**注**：`@SpringBootApplication`同时也为`@EnableAutoConfiguration` and `@ComponentScan`提供了别名用于自定义属性.