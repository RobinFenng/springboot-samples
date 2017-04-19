package com.moregx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.model.User;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/putKey")
	String putKey() {
		
		List<String> s = new ArrayList<String>();
		s.add("11");
		s.add("22");
		s.add("33");
		
		
		ListOperations<String, String>  o = redisTemplate.opsForList();
		o.rightPushAll("oop", s);
		
		//返回list
		List<String> ss  = o.range("key", 0, -1);
		
		
		//存类型 id 
		redisTemplate.opsForHash().put("user", "1", new User("xiaoming",15));
		
		User u = (User)redisTemplate.opsForHash().get("tt", "123");  
		 System.out.println(u.getName());
		return "success";
	}
}
