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



