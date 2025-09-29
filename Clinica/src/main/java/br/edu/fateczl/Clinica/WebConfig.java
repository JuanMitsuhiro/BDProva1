package br.edu.fateczl.Clinica;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "br.edu.fateczl.Clinica")
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
        		.setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/gestao").setViewName("gestao");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
        		.addResourceLocations("/WEB-INF/resources");
	}

	
}
