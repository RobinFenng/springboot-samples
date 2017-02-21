package com.moregx.analyzers;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.moregx.exception.CustomerException;

@Component
public class CustomerLoaderAware
implements BeanClassLoaderAware, EnvironmentAware, InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		
		throw new CustomerException("加载");
		
	}

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		// TODO Auto-generated method stub
		
	}

}
