package com.freedoonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.cloudlink.core.common.dataaccess.data.ControllerResult;

/**
  * 
  *<p>类描述：服务启动类。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:03:49。</p>
  */
@SpringBootApplication
@ComponentScan({"cn.cloudlink.core","com.freedoonline"})
@RestController
public class BuildingGuardApplication {
	
	public static void main(String[] args)  {
		   SpringApplication.run(BuildingGuardApplication.class, args);
		}
		
		@RequestMapping("/")
		public @ResponseBody ControllerResult check(){
			ControllerResult controllerResult = new ControllerResult();
			return controllerResult;
		}

		@Bean
		@LoadBalanced
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	
}
