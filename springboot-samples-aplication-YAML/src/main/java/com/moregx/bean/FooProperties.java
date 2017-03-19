package com.moregx.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="foo")
@Component
public class FooProperties {
	
	String name;
	
	List<MyPojo> list = new ArrayList<MyPojo>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MyPojo> getList() {
		return list;
	}

	public void setList(List<MyPojo> list) {
		this.list = list;
	}
	
	

}
