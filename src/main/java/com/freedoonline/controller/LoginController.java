package com.freedoonline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.UserService;

import cn.cloudlink.core.common.base.controller.BaseController;
import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.CryptUtil;
import cn.cloudlink.core.common.utils.StringUtil;
/**
  * 
  *<p>类描述：登录管理控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:00:27。</p>
  */
@RestController
@RequestMapping(value="/login")
public class LoginController extends BaseController{
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	@Resource
	private RestTemplate restTemplate;
	 
	/**
	  * 
	  * <p>功能描述：登录。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月27日 下午9:01:28。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/loginByPassword")
	public Object loginByPassword(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		Map<String, Object> resultMap = new HashMap<>();
		String loginNum=(String)paramMap.get("loginNum");
		if(!StringUtil.hasText(loginNum)){
			return new BusinessResult(-1, "403", "登录号码不能为空！");
		}
		String password=(String)paramMap.get("password");
		if(!StringUtil.hasText(password)){
			return new BusinessResult(-1, "403", "密码不能为空！");
		}
		try{
			password=CryptUtil.md5Encrypt(password);
			//验证用户账号是否合法
			User user = (User)userService.validateUser(loginNum, password);
			if(null == user){
				return new BusinessResult(-1, "403", "用户名密码不正确！");
			}
			String token = userService.createUserToken(request, user);
			resultMap.put("val", token);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("rows", user);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			getMicroserviceLogTemplate().error(e.getMessage());
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：用户注册。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param user
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月28日 下午2:09:08。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
//	@PostMapping("/add")
//	public Object addUser(HttpServletRequest request,@RequestBody User user){
//		try{
//			User userBo = ThreadLocalHolder.getUser();
//			if( userBo != null ){
//				//从当前线程中获取登录的用户
//				user.setEnpId(userBo.getEnpId());//设置当前添加人的企业
//				user.setCreateUser(userBo.getObjectId());//设置创建人
//				user.setModifyUser(userBo.getObjectId());//设置修改人
//			}
//			//添加用户
//			User addSuccessUser =  (User) userService.addUser(user);
//			ArrayList<Object> arrayList = new ArrayList<>();
//			arrayList.add(addSuccessUser.getObjectId());
//			return new BusinessResult(arrayList);
//		}catch(BusinessException e){
//			return new BusinessResult(-1, e.getCode(), e.getMessage());
//		}catch(Exception e){
//			return new BusinessResult(-1, "400", e.getMessage());
//		}
//	}
}
