package com.freedoonline.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.MeterService;

import cn.cloudlink.core.common.base.controller.BaseController;
import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;

@RestController
@RequestMapping(value = "/meter")
public class MeterController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	
	@Resource
	private RestTemplate restTemplate;
	
	@Autowired
	private MeterService meterService;
	
	@PostMapping("/addMeter")
	public Object addMeter (HttpServletRequest request,@RequestBody Meter meter){
		try {
			ArrayList<String> arrayList = new ArrayList<String>();
			User user = ThreadLocalHolder.getUser();
			String creatUser = user.getObjectId();
			meter.setCreateUser(creatUser);
			meter.setModifyUser(creatUser);
			meter.setEnpId(user.getEnpId());
			String objectId = meterService.addMeter(meter);
			arrayList.add(objectId);
			logger.info("-----------添加Meter成功----------------");
			return GuardRresponseMessage.creatBySuccessData(objectId);
		} catch (BusinessException e) {
			logger.error("------------添加metery业务失敗----------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("------添加metery失败");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage() );
		}
	}
}
