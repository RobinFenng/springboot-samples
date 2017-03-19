#Spring Boot之Application与YAML#


----------





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



如果spring.config.location contains包含目录(而不是文件),那他们应该以/结尾，（在加载前，spring.config.name产生的名称将被追加到后面）。


无论spring.config.location设置什么值，它默认会搜索`classpath:,classpath:/config,file:,file:config/ `目录.搜索目录是从低到高排序的（`file:config/` 优先），如果你想指定你自己的目录优先于默认的加载目录，你可以在`application.properties`中设置一个默认值，然后在运行的时候使用不同的文件覆盖它，同时保留默认配置。



## 属性文件中的占位符 ##


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

示例：[ https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-aplication-YAML](https://github.com/RobinFenng/springboot-samples/tree/master/springboot-samples-aplication-YAML)