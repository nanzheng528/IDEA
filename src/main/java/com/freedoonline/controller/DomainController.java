package com.freedoonline.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.service.DomainService;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;

@RestController
@RequestMapping(value="/domain")
public class DomainController {
	
	@Resource(name="domainServiceImpl")
	private DomainService domainService;
	
	/**
	  * 
	  * <p>功能描述：获取阈值列表。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年6月1日 下午3:16:32。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@SuppressWarnings("unchecked")
	@PostMapping(value="queryThreshold")
	public Object queryThreshold(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		try{
			List<Object> result = (List<Object>)domainService.queryThreshold(paramMap);
			return GuardRresponseMessage.creatBySuccessData(result);
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
}
