package com.freedoonline.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.common.response.GuardRresponseMessage;
import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.BuildingArea;
import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.BuildingService;
import com.freedoonline.service.bo.BuildingAreaBo;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.dataaccess.data.ControllerResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@RestController
@RequestMapping(value="/building")
public class BuildingController {
	
	@Resource(name="buildingServiceImpl")
	private BuildingService buildingService;
	
	@Resource
	private RestTemplate restTemplate;
	
	/**
	  * 
	  * <p>功能描述：企业下所有的楼宇。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月29日 上午10:19:07。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@GetMapping("/infoAll")
	public Object info(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		String objectId = ThreadLocalHolder.getUser().getObjectId();
		if(!StringUtil.hasText(objectId)){
			return new BusinessResult(-1, "403", "用户ID不能为空！");
		}
		try{
			List<Building> building = buildingService.infoAll(objectId);
			if(null == building){
				return new BusinessResult(-1, "403", "用户不存在！");
			}
			resultMap.put("val", building);
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
	  * <p>功能描述：获取楼宇详情。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月29日 上午10:18:28。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping(value="info")
	public Object queryHiecsById(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		try{
			String buildingId = (String)paramMap.get("buildingId");
			String enpId= ThreadLocalHolder.getUser().getEnpId();
			Map<String, Object> resultMap = new HashMap<>();
			Building building = (Building)buildingService.info(buildingId,enpId);
			if(building==null){
				return new BusinessResult(-1,"403","楼宇信息不存在!");
			}
			resultMap.put("val", building);
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
	  * <p>功能描述：根据楼宇ID获取楼层。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月29日 上午10:18:06。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value="queryFloor")
	public Object queryBuildingFloor(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		try{
			String buildingId = (String)paramMap.get("buildingId");
			String enpId= ThreadLocalHolder.getUser().getEnpId();
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = (Map<String, Object>)buildingService.queryBuildingFloor(buildingId,enpId);
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
	
	/**
	  * 
	  * <p>功能描述：增加楼宇区域。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param buildingAreaBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月29日 上午10:44:57。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping(value="addBuildingArea")
	public Object addBuildingArea(HttpServletRequest request,@RequestBody BuildingArea buildingArea){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			buildingArea.setCreateUser(user.getObjectId());//设置创建人
			buildingArea.setModifyUser(user.getObjectId());//设置修改人
			buildingArea.setEnpId(user.getEnpId());
			String objectId = (String)buildingService.addBuildingArea(buildingArea);
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
	  * <p>功能描述：查询楼宇区域。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param buildingAreaBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月29日 上午10:44:57。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping(value="queryBuildingArea")
	public Object queryBuildingArea(HttpServletRequest request,@RequestBody BuildingArea buildingArea){
		try{
			PageRequest pageRequest = new PageRequest(buildingArea.getPageNum(),buildingArea.getPageSize(),buildingArea.getOrderBy(),buildingArea.isCountTotal());
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			buildingArea.setEnpId(enpId);
			@SuppressWarnings("unchecked")
			Page<BuildingAreaBo> resultPage = (Page<BuildingAreaBo>)buildingService.queryBuildingArea(pageRequest,buildingArea);
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
	  * <p>功能描述：区域更新。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param paramMap
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年6月5日 上午6:41:44。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping(value="updateBuildingArea")
	public Object update(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			paramMap.put("enpId", userBo.getEnpId());
			
			String objectId = (String)buildingService.updateBuildingArea(paramMap);
			return GuardRresponseMessage.creatBySuccessData(objectId);
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
}
