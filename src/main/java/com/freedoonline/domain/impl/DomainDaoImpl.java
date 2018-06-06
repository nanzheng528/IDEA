package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.DomainDao;
import com.freedoonline.domain.entity.Building;
import com.freedoonline.service.bo.DomainBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class DomainDaoImpl implements DomainDao {
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	static String SELECT_DOMAIN_SQL = "";
	
	static {
		SELECT_DOMAIN_SQL = " SELECT  dt.domain_name, dt.code, dt.value, dt.value_en  FROM  domain_table dt  " ; 
	}
	/**
	  * 
	  * <p>功能描述:获取阈值列表。</p>	
	  * @param paramMap
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年6月1日 下午3:25:09。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object queryThreshold(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List <Object> list = new ArrayList<Object>();
		
		String domainName = (String)paramMap.get("domainName");
		list.add(domainName);
		StringBuffer buffer = new StringBuffer(SELECT_DOMAIN_SQL);
		Integer keyword = (Integer)paramMap.get("keyword");
		buffer.append(" WHERE dt.domain_name=? ");
		if(null != keyword){
			list.add(keyword+"%"); 
			buffer.append(" AND dt.code like ? ");
		}
		@SuppressWarnings("unchecked")
		List<DomainBo> resultList = baseJdbcDao.queryForList(buffer.toString(), list.toArray(), DomainBo.class);
		return resultList;
		
	}
	
}
