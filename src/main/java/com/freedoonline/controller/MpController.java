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

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.MpService;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.dataaccess.data.ControllerResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：巡检计划控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年6月7日 下午4:49:18。</p>
  */
@RestController
@RequestMapping(value="/mp")
public class MpController {
	
	@Resource(name="mpServiceImpl")
	private MpService mpService;
	
	@PostMapping("addPlan")
	public Object addMp(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User user = ThreadLocalHolder.getUser();
			Map<String, Object> resultMap = new HashMap<>();
			List<String> list = (List<String>)mpService.addMp(paramMap,user);
			return GuardRresponseMessage.creatBySuccessData(list);
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("queryPlan")
	public Object queryPlan(HttpServletRequest request,@RequestBody MaintenancePlanBo queryBo){
		try{
			User user = ThreadLocalHolder.getUser();
			queryBo.setEnpId(user.getEnpId());
			//queryBo.setMaintenanceId(user.getObjectId());
			System.out.print(user.getRoleId());
			PageRequest pageRequest = new PageRequest(queryBo.getPageNum(),queryBo.getPageSize(),queryBo.getOrderBy(),queryBo.isCountTotal());
			Page<MaintenancePlan> resultPage = mpService.queryListPlan(pageRequest, queryBo,user);
			Integer totalPages = (int) Math.ceil(resultPage.getTotalLength()*1.0/pageRequest.getPageSize());
			ControllerResult cResult = new ControllerResult(1, "200", "ok", (int) resultPage.getTotalLength(), totalPages, resultPage.getPageSize(), resultPage.getPageNum(), resultPage.getPageNum()==1, resultPage.getPageNum()==totalPages, resultPage.getResult());
			return cResult;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
}
