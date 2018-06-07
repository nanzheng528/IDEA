package com.freedoonline.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.SubSystemFault;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.EquipmentService;
import com.freedoonline.service.bo.EquipmentBo;
import com.freedoonline.service.bo.MaintenancePlanBo;
import com.freedoonline.service.bo.SubSystemBo;
import com.freedoonline.service.bo.SystemFaultBo;
import com.freedoonline.service.bo.SystemFaultPct;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.dataaccess.data.ControllerResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：设备相关控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月5日 下午5:20:49。</p>
  */
@RestController
@RequestMapping(value="/equipment")
public class EquipmentController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource(name="equipmentServiceImpl")
	private EquipmentService equipmentService;
	
	/**
	  * 
	  * <p>功能描述：设备列表。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月5日 下午5:20:27。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("queryList")
	public Object queryEquipment(HttpServletRequest request,@RequestBody EquipmentBo queryBo){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			queryBo.setEnpId(enpId);
			PageRequest pageRequest = new PageRequest(queryBo.getPageNum(),queryBo.getPageSize(),queryBo.getOrderBy(),queryBo.isCountTotal());
			Page<Equipment> resultPage = equipmentService.queryListEquipment(pageRequest, queryBo);
			Integer totalPages = (int) Math.ceil(resultPage.getTotalLength()*1.0/pageRequest.getPageSize());
			ControllerResult cResult = new ControllerResult(1, "200", "ok", (int) resultPage.getTotalLength(), totalPages, resultPage.getPageSize(), resultPage.getPageNum(), resultPage.getPageNum()==1, resultPage.getPageNum()==totalPages, resultPage.getResult());
			return cResult;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：设备增加。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月5日 下午5:20:04。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/add")
	public Object addEquipment(HttpServletRequest request,@RequestBody EquipmentBo queryBo){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			queryBo.setCreateUser(user.getObjectId());//设置创建人
			queryBo.setModifyUser(user.getObjectId());//设置修改人
			queryBo.setEnpId(user.getEnpId());
			String objectId = (String)equipmentService.addEquipment(queryBo);
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
	/**
	 * 
	  * <p>功能描述：设备设施更新。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月11日 上午9:40:56。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@PostMapping("/update")
	public Object update(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			paramMap.put("enpId", userBo.getEnpId());
			
			Map<String, Object> resultMap = new HashMap<>();
			String objectId = (String)equipmentService.updateEquById(paramMap);
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
	  * <p>功能描述：维护计划查询。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月5日 下午5:19:40。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("queryMaintenance")
	public Object queryMaintenancePlan(HttpServletRequest request,@RequestBody MaintenancePlanBo queryBo){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			queryBo.setEnpId(enpId);
			PageRequest pageRequest = new PageRequest(queryBo.getPageNum(),queryBo.getPageSize(),queryBo.getOrderBy(),queryBo.isCountTotal());
			Page<MaintenancePlan> resultPage = equipmentService.queryListMaintenancePlan(pageRequest, queryBo);
			Integer totalPages = (int) Math.ceil(resultPage.getTotalLength()*1.0/pageRequest.getPageSize());
			ControllerResult cResult = new ControllerResult(1, "200", "ok", (int) resultPage.getTotalLength(), totalPages, resultPage.getPageSize(), resultPage.getPageNum(), resultPage.getPageNum()==1, resultPage.getPageNum()==totalPages, resultPage.getResult());
			return cResult;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：增加维护计划。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月14日 下午8:19:21。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@PostMapping("/addMaintenancePlan")
	public Object addMaintenancePlan(HttpServletRequest request,@RequestBody MaintenancePlanBo queryBo){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			queryBo.setCreateUser(user.getObjectId());//设置创建人
			queryBo.setModifyUser(user.getObjectId());//设置修改人
			queryBo.setEnpId(user.getEnpId());
			
			String objectId = (String)equipmentService.addMaintenancePlan(queryBo);
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
	
	/**
	  * 
	  * <p>功能描述：更新维护计划状态。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月14日 下午9:26:01。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/equSatus")
	public Object updateMaintenancePlan(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			paramMap.put("enpId", userBo.getEnpId());
			Map<String, Object> resultMap = new HashMap<>();
			String objectId = (String)equipmentService.updateMaintenancePlan(paramMap);
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
	  * <p>功能描述：五大系统。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月14日 上午8:35:14。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("systemFault")
	public Object systemFault(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			param.put("user", user);
			List<SystemFaultBo> list = equipmentService.systemFault(param);
			resultMap.put("val", list);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			//resultMap.put("totalScore", list.get(0).getTotalScore());
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("querySub")
	public Object querySub(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			Integer systemNum = (Integer)param.get("systemNum");
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			List<SubSystemBo> list = equipmentService.querySub(systemNum+"",enpId);
			resultMap.put("val", list);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：子系统故障率。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月14日 上午8:31:42。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("subSystemFault")
	public Object subSystemFault(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			param.put("user", user);
			List<SubSystemFault> list = equipmentService.subSystemFault(param);
			resultMap.put("val", list);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("equSystemPct")
	public Object equSystemPct(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			param.put("user", user);
			List<SystemFaultPct> list = equipmentService.equSystemPct(param);
			resultMap.put("val", list);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
			return resultMap;
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	@PostMapping("score")
	public Object equScore(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = new HashMap<>();
			result.put("score", 90);
			result.put("eScore", 96);
			result.put("hScore", 91);
			result.put("sScore", 89);
			result.put("coScore", 85);
			result.put("enScore", 90);
//			User user = ThreadLocalHolder.getUser();
//			param.put("user", user);
//			List<SystemFaultPct> list = equipmentService.equSystemPct(param);
			resultMap.put("val", result);
			resultMap.put("msg", "success");
			resultMap.put("stat", 0);
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
			List<Object> resultList = (List)equipmentService.pushMsg(param);
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
	  * <p>功能描述：设备故障率(故障/总设备)。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月11日 上午9:00:00。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/faultRate")
	public Object faultRate(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = (Map<String, Object>)equipmentService.faultRate(param);
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
	  * <p>功能描述：维护人员列表。</p>
	  * <p> 刘建雨。</p>	
	  * @param response
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月14日 下午10:26:23。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@GetMapping("/queryBmUser")
	public Object queryMaintenanceUser(HttpServletResponse response){
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings("unchecked")
			List<Object> result = (List<Object>)equipmentService.queryMaintenanceUser(param);
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
