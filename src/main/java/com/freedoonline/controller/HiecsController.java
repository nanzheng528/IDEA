package com.freedoonline.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.freedoonline.common.interceptor.ThreadLocalHolder;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.HiecsService;
import com.freedoonline.service.bo.HiecsBo;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.dataaccess.data.ControllerResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：室内健康控制层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月29日 上午11:53:04。</p>
  */
@RestController
@RequestMapping(value="/hiecs")
public class HiecsController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource(name="hiecsServiceImpl")
	private HiecsService hiecsService;
	
	@Value("${hiecs.temperatureSummer}")
	private double temperatureSummer;

	@Value("${hiecs.temperatureWinter}")
	private double temperatureWinter;

	@Value("${hiecs.humiditySummer}")
	private double humiditySummer;

	@Value("${hiecs.humidityWinter}")
	private double humidityWinter;

	@Value("${hiecs.CO2}")
	private double CO2;

	@Value("${hiecs.PM}")
	private double PM;
	
	@Value("${hiecs.HCHO}")
	private double HCHO;
	
	/**
	  * 
	  * <p>功能描述：室内健康数据增加。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param hiecs
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月29日 下午1:56:24。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/add")
	public Object addHiecs(HttpServletRequest request,@RequestBody Hiecs hiecs){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			User user = ThreadLocalHolder.getUser();
			hiecs.setCreateUser(user.getObjectId());//设置创建人
			hiecs.setModifyUser(user.getObjectId());//设置修改人
			String objectId = (String)hiecsService.addHiecs(hiecs);
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
	  * <p>功能描述：查询详情接口。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param objectId
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月29日 下午5:07:19。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@GetMapping(value="getById")
	public Object queryHiecsById(HttpServletRequest request,@RequestParam(value="objectId") String objectId){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			Hiecs hiecs = hiecsService.queryHiecsById(objectId);
			if(hiecs==null){
				return new BusinessResult();
			}
			resultMap.put("val", hiecs);
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
	  * <p>功能描述：室内健康数据删除(假删除,支持批量删除)。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param objectId
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月29日 下午8:21:47。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/del")
	public Object delHiecsById(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			hiecsService.delHiecsById(param);
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
	  * <p>功能描述：室内数据更新。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param objectId
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年4月29日 下午8:27:12。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/update")
	public Object updateHiecsById(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		try{
			User userBo = ThreadLocalHolder.getUser();
			paramMap.put("modifyUser", userBo.getObjectId());
			Map<String, Object> resultMap = new HashMap<>();
			hiecsService.updateHiecsById(paramMap);
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
	  * <p>功能描述：获取室内健康数据列表。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月1日 上午11:21:23。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@PostMapping("queryList")
	public Object queryHiecs(HttpServletRequest request,@RequestBody HiecsBo queryBo){
		try{
			if(queryBo.getPageNum()==-1){//不分页
				List<Hiecs> list = hiecsService.queryListHiecs(queryBo);
				return new BusinessResult(list);
			}else{
				//封装分页请求对象
				PageRequest pageRequest = new PageRequest(queryBo.getPageNum(),queryBo.getPageSize(),queryBo.getOrderBy(),queryBo.isCountTotal());
				Page<Hiecs> resultPage = hiecsService.queryListHiecs(pageRequest, queryBo);
				Integer totalPages = (int) Math.ceil(resultPage.getTotalLength()*1.0/pageRequest.getPageSize());
				ControllerResult cResult = new ControllerResult(1, "", "", (int) resultPage.getTotalLength(), totalPages, resultPage.getPageSize(), resultPage.getPageNum(), resultPage.getPageNum()==1, resultPage.getPageNum()==totalPages, resultPage.getResult());
				return cResult;
			}
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	
	/**
	  * 
	  * <p>功能描述：室内健康评分。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param queryBo
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月1日 下午5:45:41。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("queryScore")
	public Object query(HttpServletRequest request,@RequestBody HiecsBo queryBo){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = hiecsService.colligation(queryBo);
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
	  * <p>功能描述：不合规信息推送。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月12日 上午11:10:34。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/pushMsg")
	public Object pushMsg(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)hiecsService.pushMsg(param);
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
	  * <p>功能描述：单项不合规。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月12日 上午11:10:59。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/queryType")
	public Object queryType(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			String enpId = ThreadLocalHolder.getUser().getEnpId();
			param.put("enpId", enpId);
			Map<String, Object> resultMap = new HashMap<>();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> resultList = (List)hiecsService.queryType(param);
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
	  * <p>功能描述：标准值。</p>
	  * <p> 刘建雨。</p>	
	  * @param request
	  * @param param
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月12日 上午11:11:16。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/standard")
	public Object standard(HttpServletRequest request,@RequestBody Map<String, Object> param){
		try{
			Map<String, Object> result = new HashMap<>();
			result.put("temperature", temperatureSummer+"℃±1℃");
			result.put("humidity", humiditySummer+"%±10%");
			result.put("th", temperatureSummer+"℃±1℃/"+humiditySummer+"%±10%");
			result.put("pm", PM+"μm"); 
			result.put("co2", CO2+"ppm");
			result.put("hcho", HCHO+"mg");
			Map<String, Object> resultMap = new HashMap<>();
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
	
	@PostMapping("/detailScore")
	public Object hiecsDetailScore(HttpServletRequest request,@RequestBody HiecsBo queryBo){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			Map<String, Object> result = hiecsService.hiecsDetailScore(queryBo);
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
}
