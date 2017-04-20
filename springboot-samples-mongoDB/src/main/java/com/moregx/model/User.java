package com.moregx.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	
	 @Id
	 private String id;
	 
	String name;
	Integer sex;
	Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public User(String name, Integer sex, Integer age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
	
	
	
}
