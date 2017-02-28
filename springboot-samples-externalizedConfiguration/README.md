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
4. 使用命令行进行赋值,如：java -jar app.jar --name="name in command level 4"
5. 
    


  

