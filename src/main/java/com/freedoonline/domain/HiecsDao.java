package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.service.bo.HiecsBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;


/**
  * 
  *<p>类描述：室内健康数据层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月29日 下午1:51:59。</p>
  */
public interface HiecsDao {
	
	// 增加室内健康数据
	public String addHiecs(Hiecs hiecs);
	// 根据ID查询详情
	public Hiecs queryHiecsById(String objectId);
	// 详情
	public List<Hiecs>  queryListHiecs(HiecsBo queryBo);
	// 分页查询室内环境
	public Page<Hiecs> queryListHiecs(PageRequest pageRequest,HiecsBo queryBo);
	// 消息推送
	public Object pushMsg(Map<String, Object> param);
	
	public Object queryType(Map<String, Object> param);
}
