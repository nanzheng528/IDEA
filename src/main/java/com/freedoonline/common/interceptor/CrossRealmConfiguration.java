package com.freedoonline.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
  * 
  *<p>类描述：CrossRealm。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午8:55:28。</p>
  */
@Configuration
public class CrossRealmConfiguration {
	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
		 return new WebMvcConfigurerAdapter() {
			 @Override  
             public void addCorsMappings(CorsRegistry registry) {  
                 registry.addMapping("/**")
                 .allowedOrigins("*")
                 .allowCredentials(true)
                 .allowedMethods("GET","POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
                 .allowedHeaders("*");
             } 
		 };
	 }
	 
}
