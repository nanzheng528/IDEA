package com.freedoonline.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.EquSystemDao;
import com.freedoonline.domain.entity.EquSystem;
import com.freedoonline.service.bo.EquSystemBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;

@Repository
public class EquSystemDaoImpl implements EquSystemDao {
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	static String SELECT_SYSTEM = "";
	
	static {
		SELECT_SYSTEM = " SELECT es.object_id, es.system_name, es.system_num from equ_system es where es.active=1 ";
	}
	
	/**
	  * 
	  * <p>功能描述:根据企业获取主系统列表。</p>	
	  * @param system
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月12日 上午10:46:40。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object querySystemList(EquSystem system) {
		StringBuffer buffer = new StringBuffer(SELECT_SYSTEM);
		buffer.append(" and es.enp_id = ?");
		Object[] args = {system.getEnpId()};
		return baseJdbcDao.queryForList(buffer.toString(), args, EquSystemBo.class);
	}

}
