package com.louis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
