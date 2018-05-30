package com.freedoonline.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
  * 
  *<p>类描述：Spring MVC配置。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午8:59:01。</p>
  */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	/**
	  * 
	  * <p>功能描述:添加自定义拦截器。</p>	
	  * @param registry
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月27日 下午8:59:46。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor());   //注册token拦截器
	}
	
	@Bean
	public TokenInterceptor tokenInterceptor(){
		TokenInterceptor tokenInterceptor = new TokenInterceptor();
		return tokenInterceptor;
	}
}
