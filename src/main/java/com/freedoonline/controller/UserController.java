package com.freedoonline.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.UserService;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;


/**
  * 
  *<p>类描述：用户信息控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月28日 上午11:05:35。</p>
  */
@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	@Resource
	private RestTemplate restTemplate;
	
	/**
	  * 
	  * <p>功能描述：获取用户详情。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月28日 上午11:42:56。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/info")
	public Object userInfo(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		Map<String, Object> resultMap = new HashMap<>();
		String objectId = ThreadLocalHolder.getUser().getObjectId();
		if(!StringUtil.hasText(objectId)){
			return new BusinessResult(-1, "403", "用户ID不能为空！");
		}
		try{
			User user = (User)userService.validateUser(objectId);
			if(null == user){
				return new BusinessResult(-1, "403", "用户不存在！");
			}
			resultMap.put("val", user);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：用户信息更新。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param user
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月28日 下午2:09:08。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/update")
	public Object updateUser(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			String objectId = (String) paramMap.get("objectId");
			if(!StringUtil.hasText(objectId)){
	    		return new BusinessResult(-1, "403", "用户ID不能为空！");
			}
			//paramMap.remove("objectId");
			if(paramMap.isEmpty()){
				return new BusinessResult(-1, "403", "要更新的字段不能为空！");
			}
			//paramMap.put("objectId", objectId);
			//从当前线程中获取登录的用户
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			return GuardRresponseMessage.creatBySuccessData(userService.updateUser(paramMap));
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			User user = (User)userService.validateUser(objectId1);
//			dataMap.put("val", user);
//			dataMap.put("msg", "success");
//			dataMap.put("stat", 0);
//			return dataMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
}
