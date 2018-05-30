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
import com.freedoonline.domain.entity.Electricity;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.EnergyService;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;

@RestController
@RequestMapping(value="/energy")
public class EnergyController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource(name="energyServiceImpl")
	private EnergyService energyService;
	
	@SuppressWarnings("unchecked")
	@PostMapping("consumption")
	public Object consumption(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, List<String>> result = (Map<String, List<String>>)energyService.waterConsumption(param);
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
	
	@PostMapping("score")
	public Object score(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = new HashMap<>();
			result.put("water", 98);
			result.put("electricity", 90);
			result.put("Gas", 96);
			result.put("fuel", 92);
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
	
	/**
	  * 
	  * <p>功能描述：用电量增加。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param electricity
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月11日 下午4:40:43。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("eleAdd")
	public Object eleAdd(HttpServletRequest request,@RequestBody Electricity electricity){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			electricity.setCreateUser(user.getObjectId());//设置创建人
			electricity.setModifyUser(user.getObjectId());//设置修改人
			electricity.setEnpId(user.getEnpId());
			String objectId = (String)energyService.eleAdd(electricity);
			resultMap.put("val", objectId);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("eleUpdate")
	public Object eleUpdate(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			paramMap.put("enpId", userBo.getEnpId());
			Map<String, Object> resultMap = new HashMap<>();
			String objectId = energyService.eleUpdate(paramMap);
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
	
	/**
	  * 
	  * <p>功能描述：月季度年4种电量的明细值。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月22日 上午9:29:23。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("eleQuery")
	public Object eleQuery(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			paramMap.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings("unchecked")
			List<Object> result = (List<Object>)energyService.eleQuery(paramMap);
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
