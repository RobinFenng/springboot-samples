package com.moregx.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.moregx.viewresolver.JsonViewResolver;
import com.moregx.viewresolver.PdfViewResolver;

@Configuration
public class ContentNegotiatingViewResolverConfig extends WebMvcConfigurerAdapter {

	 @Bean
	    public UrlBasedViewResolver viewResolver() {
	        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
	        resolver.setPrefix("/WEB-INF/views/");
	        resolver.setSuffix(".jsp");
	        resolver.setViewClass(JstlView.class);
	        return resolver;
	    }

	    // 静态资源映射
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	    }


	    // 在此---配置ContentNegotiationManager,在无后缀名情况下默认为jsp view resolver
	    @Override
	    public void configureContentNegotiation(
	            ContentNegotiationConfigurer configurer) {
	        configurer.ignoreAcceptHeader(true).defaultContentType(
	                MediaType.APPLICATION_JSON)
	        .mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("pdf", MediaType.valueOf("application/pdf"))
	        .mediaType("json", MediaType.APPLICATION_JSON)
	        .mediaType("xls", MediaType.valueOf("application/vnd.ms-excel"));
	    }

	    // 在此---配置ContentNegotiatingViewResolver,通过此代理到不同的viewResolover
	    @Bean
	    public ViewResolver contentNegotiatingViewResolver(
	            ContentNegotiationManager manager) {

	        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	        resolver.setContentNegotiationManager(manager);
	        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
	        resolvers.add(jsonViewResolver());
	        resolvers.add(pdfViewResolver());
	        resolver.setViewResolvers(resolvers);
	        return resolver;
	    }

	    //在此---json viewResolver
	    @Bean
	    public ViewResolver jsonViewResolver() {
	        return new JsonViewResolver();
	    }
	    //在此---pdf viewResolver
	    @Bean 
	    public ViewResolver pdfViewResolver() {
	        return new PdfViewResolver();
	    }

	
	
}
