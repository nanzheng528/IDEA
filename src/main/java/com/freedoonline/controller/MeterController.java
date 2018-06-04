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
import com.freedoonline.service.bo.MeterBo;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.null_predicate_return;

import cn.cloudlink.core.common.base.controller.BaseController;
import cn.cloudlink.core.common.dataaccess.data.ControllerResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import freemarker.core.ReturnInstruction.Return;

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
			User user = ThreadLocalHolder.getUser();
			String creatUser = user.getObjectId();
			meter.setCreateUser(creatUser);
			meter.setModifyUser(creatUser);
			meter.setEnpId(user.getEnpId());
			String objectId = meterService.addMeter(meter);
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
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/updateMeter")
	public GuardRresponseMessage updateMeter (HttpServletRequest request,@RequestBody Meter meter){
		try {
			User user = ThreadLocalHolder.getUser();
			String creatUser = user.getObjectId();
			meter.setModifyUser(creatUser);
			if(meterService.updateMeter(meter)){
				logger.info("-----------更新Meter成功----------------");
				return GuardRresponseMessage.creatBySuccessMessage();
			} else {
				logger.info("-----------更新Meter数据库内容失败----------------");
				return GuardRresponseMessage.creatByErrorMessage("400","更新失败");
			}
		} catch (BusinessException e) {
			logger.error("------------更新metery业务失敗----------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("------更新metery失败--------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage() );
		}
	}
	
	@PostMapping("/queryMeterList")
	public Object queryMeter(HttpServletRequest request,@RequestBody MeterBo meter){
		try {
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			meter.setEnpId(enpId);
			PageRequest pageRequest = new PageRequest(meter.getPageNum(), meter.getPageSize(), meter.getOrderBy(), meter.isCountTotal());
			Page<Meter> queryMeter = meterService.queryMeter(pageRequest, meter);
			Integer totalPages = (int) Math.ceil(queryMeter.getTotalLength()*1.0/pageRequest.getPageSize());
			return new ControllerResult(1, "200", "ok", (int)queryMeter.getTotalLength(), totalPages, queryMeter.getPageSize(), 
					queryMeter.getPageNum(), queryMeter.getPageNum() == 1,
					queryMeter.getPageNum() == totalPages, queryMeter.getResult());
			
		} catch (BusinessException e) {
			logger.error("------------查询metery业务失敗----------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("------查询metery失败-------");
			e.printStackTrace();
			return GuardRresponseMessage.creatByErrorMessage("400",e.getMessage() );
		}
	}
}
