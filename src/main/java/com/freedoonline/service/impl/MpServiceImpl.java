package com.freedoonline.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.MpDao;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.MpService;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("mpServiceImpl")
public class MpServiceImpl implements MpService {

	@Resource(name = "mpDaoImpl")
	private MpDao mpDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object addMp(Map<String, Object> param, User user) throws BusinessException, Exception {
		
		Long planStartTime = (Long) param.get("planStartTime");
		Long planEndTime = (Long) param.get("planEndTime");
		
		if (!StringUtil.hasText((String)param.get("name"))) {
			throw new BusinessException("巡检名称不能为空！", "403");
		}
		List <String> list = (List<String>)param.get("equId");
		if (list !=null && list.size() < 0 ) {
			throw new BusinessException("设备ID不能为空！", "403");
		}
		if (!StringUtil.hasText(((Integer)param.get("type")).toString())) {
			throw new BusinessException("巡检类型不能为空！", "403");
		}
		if (!StringUtil.hasText(((Integer)param.get("frequency")).toString())) {
			throw new BusinessException("巡检频率不能为空！", "403");
		}
		if (!StringUtil.hasText(((String)param.get("maintenanceId")))) {
			throw new BusinessException("巡检人员不能为空！", "403");
		}
		if (planStartTime == null) {
			throw new BusinessException("计划开始时间不能为空！", "403");
		}
		if (planEndTime == null) {
			throw new BusinessException("计划结束时间不能为空！", "403");
		}
		if(planStartTime > planEndTime){
			throw new BusinessException("计划开始时间不能大于结束时间！", "403");
		}
		return mpDao.addMp(param, user);
	}
	
	@Override
	public Page<MaintenancePlan> queryListPlan(PageRequest pageRequest, MaintenancePlanBo queryBo,User user)
			throws BusinessException, Exception {
		Page<MaintenancePlan> resultPage = mpDao.queryListPlan(pageRequest, queryBo, user);
		return resultPage;
	}
	
	@Override
	public Object stats(PageRequest pageRequest, MaintenancePlanBo queryBo, User user)
			throws BusinessException, Exception {
		Map<String,Object> result = new HashMap<>();
		queryBo.setPlanStartTime(getCurrYearFirst());
		queryBo.setPlanEndTime(getCurrYearLast());
		Page<MaintenancePlan> resultPage = mpDao.queryListPlan(pageRequest, queryBo, user);
		List<MaintenancePlan> list = resultPage.getResult();
		
		int backlog = 0;		//待办数量
		int complete = 0;		//完成数量
		int remainder = 0;		//剩余数量
		int overdue = 0;		//逾期数量
		//================
		
		//================
		
		result.put("backlog", backlog);
		result.put("complete", complete);
		result.put("remainder", remainder);
		result.put("overdue", overdue);
		return result;
	}
	
    /** 
     * 获取当年的第一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }  
      
    /** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }  
      
    /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    } 
	
}
