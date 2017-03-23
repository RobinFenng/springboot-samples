package com.moregx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "apple")
public class Apple {

	String name;
	
	String color;
	
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	 @XmlElement
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
