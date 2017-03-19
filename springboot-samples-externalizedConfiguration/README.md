#Spring Boot之属性读取顺序#

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


示例：

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

**ps:** ？的表示没懂，大伙给点建议啊




## 获得命令行参数 ##

`SpringApplication`会转换命令行参数并把他们添加到`Spring`环境中去.综上所述，命令行参数会优先于其他配置文件中的参数.

如果你不想命令行参数添加到环境中，你可以使用`SpringApplication.setAddCommandLineProperties(false)`禁掉.



