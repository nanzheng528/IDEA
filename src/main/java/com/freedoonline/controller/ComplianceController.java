package com.freedoonline.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.ComplianceService;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：合规性控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月2日 下午7:39:01。</p>
  */

@RestController
@RequestMapping(value="/compliance")
public class ComplianceController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource(name="complianceServiceImpl")
	private ComplianceService complianceService;
	
	/**
	  * 
	  * <p>功能描述：合规性人员。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月2日 下午7:38:36。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/maintenace")
	public Object maintenace(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)complianceService.maintenace(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", resultList);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：合规性楼宇。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月2日 下午7:37:58。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/building")
	public Object building(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)complianceService.buildingLicense(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", resultList);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("/equipment")
	public Object equipment(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)complianceService.equipmentAuth(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", resultList);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("/pushMsg")
	public Object pushMsg(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)complianceService.pushMsg(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", resultList);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("/auth")
	public Object auth(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			User user = ThreadLocalHolder.getUser();
			param.put("user", user);
			Map<String, Object> resultMap = new HashMap<>();
			String objectId = (String)complianceService.auth(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", objectId);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/authScore")
	public Object authScore(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = (Map<String, Object>)complianceService.authScore(param);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			resultMap.put("val", result);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
}
