package com.moregx.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;
@Deprecated
//@WebServlet(urlPatterns="/test", description="Servlet的说明",initParams={@WebInitParam(name= "name", value="name in ServletConfig init parameter level 6")})
public class MyServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;


	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		String name = config.getInitParameter("name");
		System.out.println("name:"+name);
		super.init(config);
	}
}
