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
import com.freedoonline.domain.entity.EquSystem;
import com.freedoonline.service.EquSystemService;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;

@RestController
@RequestMapping(value="/equSystem")
public class EquSystemController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource(name="equSystemServiceImpl")
	private EquSystemService equSystemService;
	
	/**
	  * 
	  * <p>功能描述：根据企业获取主系统列表。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param equSystem
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月12日 上午10:47:21。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@SuppressWarnings("unchecked")
	@PostMapping("/queryList")
	public Object querySystemList(HttpServletRequest request,@RequestBody EquSystem equSystem){
		try{
			//接口预留
			equSystem.setEnpId(ThreadLocalHolder.getUser().getEnpId());
			Map<String, Object> resultMap = new HashMap<>();
			List<Object> resultList = (List<Object>)equSystemService.querySystemList(equSystem);
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
}
