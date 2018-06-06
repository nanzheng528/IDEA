package com.freedoonline.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.freedoonline.domain.entity.User;

import antlr.collections.List;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

/**
  * 
  *<p>类描述：用户服务层接口。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:12:19。</p>
  */
public interface UserService {
	
	// 验证用户(用户密码)
	public Object validateUser(String loginNum,String password)throws BusinessException,Exception;
	// 生成Token
	public String createUserToken(HttpServletRequest request,User user);
	// 用户详情
	public Object validateUser(String objectId) throws BusinessException,Exception;
	// 增加用户
	public Object addUser(User user) throws BusinessException,Exception;
	// 更新用户信息
	public String updateUser(Map<String,Object> paramMap) throws BusinessException,Exception;
	// 查询用户信息列表
	public Page queryUserList(PageRequest pageRequest,User user) throws BusinessException,Exception;
	
}
