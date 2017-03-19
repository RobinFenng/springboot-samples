#Spring Boot之ConfigurationProperties与Value#


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



你还需要列出的属性类注册在`@EnableConfigurationProperties`中：

	@Configuration
	@EnableConfigurationProperties(FooProperties.class)
		public class MyConfiguration {
	}





即使这个configuration将会为FooProperties创建一个正常的bean,我们也建议 `@ConfigurationProperties`只处理环境的问题，特别是从上下文中不注入其他bean的时候.话虽如此，`@EnableConfigurationProperties`注解也会自动用于项目以确保任何被`@ConfigurationProperties`注解的bean都会被环境配置。


	@Component
	@ConfigurationProperties(prefix="foo")
	public class FooProperties {
	
	    // ... see above
	
	}


### 第三方配置 ###



像把@ConfigurationProperties注解到一个类上一样，你也可以在@Bean方法上使用它。。当你需要绑定属性到不受你控制的第三方组件时，这种方式非常有用。

为了从Environment属性配置一个bean，将`@ConfigurationProperties`添加到它的bean注册过程：
	
	@ConfigurationProperties(prefix = "bar")
	@Bean
	public BarComponent barComponent() {
	    ...
	}

所有以`bar`开头的属性都会被映射到`BarComponent`上

### 松散绑定 ###



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



### 属性转换 ###


当绑定 `@ConfigurationProperties` bean的时候，Spring会尝试转换属性为正确的类型。如果你需要定制化类型，你要提供一个`ConversionService `bean或者一个通过`CustomEditorConfigurer` bean提供一个自定义属性编辑，或者自定义转换器。

### @ConfigurationProperties 校验 ###


Spring Boot会对加上` @Validated` 注解的 `@ConfigurationProperties`的类做校验。默认使用`JSR-303`，你可以轻松的为你的`@ConfigurationProperties`类添加JSR-303 javax.validation约束注解：


	@ConfigurationProperties(prefix="foo")
	@Validated
	public class FooProperties {
	
	    @NotNull
	    private InetAddress remoteAddress;
	
	    // ... getters and setters
	
	}



如果校验嵌套属性的话，你要在字段加 @Valid，例如上面那个例子:

	@ConfigurationProperties(prefix="foo")
	@Validated
	public class FooProperties {
	
	    @NotNull
	    private InetAddress remoteAddress;
	
	    @Valid
	    private final Security security = new Security();
	
	    // ... getters and setters
	
	    public static class Security {
	
	        @NotEmpty
	        public String username;
	
	        // ... getters and setters
	
	    }
	
	}


### @ConfigurationProperties 和 @Value 的对比###

	特性					@ConfigurationProperties		  		  @Value 	

	松散绑定					支持									不支持
	
	Meta-data support		   支持									支持

	SpEL表达式				    不支持								     支持




如果你在你的组件里定义了多个configuration key,我们建议你把他们放到一个`@ConfigurationProperties `POJO对象里面。因为@Value不支持relaxed binding，所有当你使用环境变量的时候，@Value并不是一个好的选择。


示例：[https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-typesafe](https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-typesafe)