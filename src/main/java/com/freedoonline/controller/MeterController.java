package com.freedoonline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import cn.cloudlink.core.common.exception.BusinessException;

@RestController
@RequestMapping(value = "/meter")
public class MeterController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	
	@Resource
	private RestTemplate restTemplate;
	
	@Autowired
	private MeterService meterService;
	
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/addMeter")
	public GuardRresponseMessage addMeter (HttpServletRequest request,@RequestBody Meter meter){
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
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/delMeter/{objectId}")
	public GuardRresponseMessage delMeter (HttpServletRequest request,@PathVariable(value = "objectId") String objectId){
		try {
			User user = ThreadLocalHolder.getUser();
			Map<String, Object> delParamMap = new HashMap<>();
			delParamMap.put("modifyUser", user.getObjectId());
			delParamMap.put("objectId", objectId);
			if (meterService.delMeter(delParamMap)) {
				logger.info("-----------删除Meter成功----------------");
				return GuardRresponseMessage.creatBySuccessMessage();
			} else {
				logger.info("-----------删除Meter数据库内容失败----------------");
				return GuardRresponseMessage.creatByErrorMessage("400","删除失败");
			}
		} catch (BusinessException e) {
			logger.error("------------删除metery业务失敗----------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("------删除metery失败----------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage() );
		}
	}
}
