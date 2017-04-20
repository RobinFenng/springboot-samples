package com.moregx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.model.User;

@SpringBootApplication
@RestController
public class Application {

	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	@RequestMapping("/getData")
	String getData(){
		Query query = new Query(Criteria.where("name").is("jack"));
		//query.
		
		//mongoTemplate.insert(new User("张三",1,20),"student");
		User u = mongoTemplate.findOne(query, User.class,"student");
		mongoTemplate.updateMulti(query, new Update().set("age", 101), User.class,"student");
		return u.getAge()+"";
	}
	
	
}
