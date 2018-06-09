package com.freedoonline.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.Process;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.ProcessService;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@RestController
@RequestMapping(value="/process")
public class ProcessController {
	
	@Resource(name = "processServiceImpl")
	private ProcessService processService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/addProcess")
	public GuardRresponseMessage addProcess (HttpRequest request,@RequestBody Process process){
		User user = ThreadLocalHolder.getUser();
		process.setCreateUser(user.getObjectId());
		process.setModifyUser(user.getObjectId());
		process.setEnpId(user.getEnpId());
		try{
			String objectId = processService.addProcess(process);
		if(StringUtil.hasText(objectId)){
			return GuardRresponseMessage.creatBySuccessData(objectId);
		} else {
			return GuardRresponseMessage.creatByErrorMessage("添加流程失败");
		}
		} catch (BusinessException e) {
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(),e.getMessage());
		} catch (Exception e){
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryUserListByCloumnName")
	public GuardRresponseMessage queryUserListByCloumnName (HttpRequest request,@RequestBody Map<String, Object> map){
		
		try{
			List<Map<String, Object>> userList = processService.queryUserListByCloumnName(map);
		
			return GuardRresponseMessage.creatBySuccessData(userList);
		
		} catch (BusinessException e) {
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(),e.getMessage());
		} catch (Exception e){
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage());
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryProcess")
	public GuardRresponseMessage queryProcess (HttpRequest request,@RequestBody Process process){
		
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			process.setEnpId(enpId);
			List<Map<String, Object>> userList = processService.queryProcess(process);
			return GuardRresponseMessage.creatBySuccessData(userList);
		
		} catch (BusinessException e) {
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(),e.getMessage());
		} catch (Exception e){
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/update")
	public GuardRresponseMessage updateProcess (HttpRequest request,@RequestBody Process process){
		try{
			User user = ThreadLocalHolder.getUser();
			process.setModifyUser(user.getObjectId());
			String objectId =  processService.updateProcess(process);
			return GuardRresponseMessage.creatBySuccessData(objectId);
		
		} catch (BusinessException e) {
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(),e.getMessage());
		} catch (Exception e){
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage());
		}
	}
	
}
