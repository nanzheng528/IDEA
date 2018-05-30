package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.service.bo.HiecsBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：室内健康业务层。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月29日 上午11:56:48。</p>
 */
public interface HiecsService {
	// 添加室内健康数据
	public Object addHiecs(Hiecs hiecs) throws BusinessException,Exception;
	// 获取详情
	public Hiecs queryHiecsById(String objectId) throws BusinessException,Exception;
	// 删除数据
	public void delHiecsById(Map<String, Object> param) throws BusinessException,Exception;
	// 更新数据
	public String updateHiecsById(Map<String,Object> paramMap) throws BusinessException,Exception;
	// 查询数据
	public List<Hiecs>  queryListHiecs(HiecsBo queryBo)throws BusinessException,Exception;
	// 分页查询室内环境
	public Page<Hiecs> queryListHiecs(PageRequest pageRequest,HiecsBo queryBo)throws BusinessException,Exception;
	// 综合评分
	public Map<String,Object>  colligation(HiecsBo queryBo)throws BusinessException,Exception;
	// 消息推送
	public Object pushMsg(Map<String, Object> param) throws BusinessException,Exception;
	// 获取单个类型不达标的数据
	public Object queryType(Map<String, Object> param) throws BusinessException,Exception;
	
}
