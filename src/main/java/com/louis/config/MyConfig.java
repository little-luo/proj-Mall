package com.louis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MyConfig implements WebMvcConfigurer {

	/*
	 * @Override public void configureViewResolvers(ViewResolverRegistry registry) {
	 * InternalResourceViewResolver internalResourceViewResolver = new
	 * InternalResourceViewResolver();
	 * internalResourceViewResolver.setPrefix("/WEB-INF/");
	 * internalResourceViewResolver.setSuffix(".html");
	 * registry.viewResolver(internalResourceViewResolver); }
	 */
	// 重導向 到 /home
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/home");
	}

}
