package com.freedoonline.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.UserDao;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.ICommonService;
import com.freedoonline.service.IMsmService;
import com.freedoonline.service.UserService;

import cn.cloudlink.core.common.cache.RedisCacheService;
import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.CryptUtil;
import cn.cloudlink.core.common.utils.StringUtil;

/**
 * 
 * <p>
 * 类描述：用户管理服务层实现。
 * </p>
 * 
 * @author 刘建雨。
 * @version v1.0。
 * @since JDK1.8。
 *        <p>
 * 		创建日期：2018年4月27日 下午9:15:05。
 *        </p>
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "redisCacheService")
	private RedisCacheService redisCacheService;

	@Resource(name = "commonServiceImpl")
	private ICommonService commonService;

	@Resource(name = "msmServiceImpl")
	private IMsmService msmService;

	/**
	 * fieldType: String field: fileServer
	 * 
	 * @Description 文件服务器地址
	 */
	@Value("${fileConfig.diskFile.fileServer}")
	private String fileServer;

	@Value("${messageSendParam.sendMode}")
	private String sendMode;

	@Value("${messageSendParam.useCategory}")
	private String useCategory;

	@Value("${messageSendParam.signName}")
	private String signName;

	/**
	 * 
	 * <p>
	 * 功能描述:验证用户。
	 * </p>
	 * 
	 * @param loginNum
	 * @param password
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月27日 下午9:15:38。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public Object validateUser(String loginNum, String password) throws BusinessException, Exception {
		return userDao.validateUser(loginNum, password);
	}

	/**
	 * 
	 * <p>
	 * 功能描述:生成token。
	 * </p>
	 * 
	 * @param request
	 * @param user
	 * @return
	 *         <p>
	 *         刘建雨
	 *         </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月27日 下午9:16:15。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@SuppressWarnings("unused")
	@Override
	public String createUserToken(HttpServletRequest request, User user) {
		String token = UUID.randomUUID().toString();
		if (request != null) {
			token = request.getParameter("token");
			String userAgent = request.getHeader("USER-AGENT").toLowerCase() == null
					? request.getHeader("USER-AGENT").toLowerCase() : "";
		}
		if (!StringUtil.hasText(token)) {
			token = UUID.randomUUID().toString();
		}
		redisCacheService.setKeyAndValue(token, user);// loginUser放到redis里
		redisCacheService.expirse(token, 7, TimeUnit.DAYS);
		return token;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:获取用户详情。
	 * </p>
	 * 
	 * @param objectId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月28日 上午11:53:53。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public Object validateUser(String objectId) throws BusinessException, Exception {
		return userDao.validateUser(objectId);
	}

	/**
	 * 
	 * <p>
	 * 功能描述:用户注册。
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月28日 下午1:26:06。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public Object addUser(User user) throws BusinessException, Exception {
		if (!StringUtil.hasText(user.getMobileNum())) {
			throw new BusinessException("手机号码不能为空！", "403");
		} else {
			User isUser = (User) userDao.validateMobileNum(user.getMobileNum());
			if (isUser != null) {
				throw new BusinessException("手机号码已注册！", "403");
			}
		}
		if (!StringUtil.hasText(user.getUserName())) {
			throw new BusinessException("姓名不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getEmpId())) {
			throw new BusinessException("员工工号不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getDepartmentId())) {
			throw new BusinessException("员工部门不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getBuildingId())) {
			throw new BusinessException("工作楼盘不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getSuperiorId())) {
			throw new BusinessException("直接主管不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getSex())) {
			throw new BusinessException("性别不能为空！", "403");
		}
		if (null == user.getEmpDate()) {
			throw new BusinessException("聘用日期不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getIdCard())) {
			throw new BusinessException("身份证不能为空！", "403");
		}
		if (null == user.getRoleId()) {
			throw new BusinessException("权限不能为空！", "403");
		}
		if (!StringUtil.hasText(user.getEmail())) {
			throw new BusinessException("邮箱不能为空！", "403");
		}
		User addSuccessUser = userDao.addUser(user);
		if (StringUtil.hasText(addSuccessUser.getObjectId()) && null != user) {
			logger.info("-------------插入用户成功--------------");
			try {
				logger.info("-------------开始发送短信---------------");
				Map<String, Object> sendMessageParamMap = new HashMap<>();
				sendMessageParamMap.put("useCategory", useCategory);
				sendMessageParamMap.put("mobileNum", user.getMobileNum());
				sendMessageParamMap.put("pwd", addSuccessUser.getOrginalPwd());
				sendMessageParamMap.put("signName", signName);
				sendMessageParamMap.put("effectiveTime", "5");
				sendMessageParamMap.put("time", "5分钟");
				sendMessageParamMap.put("user", user.getMobileNum());
				BusinessResult sendMsmResult = (BusinessResult) msmService.sendMsm(sendMessageParamMap);
				if (sendMsmResult.getSuccess() != null && sendMsmResult.getSuccess() == 1) {
					logger.info("-------------发送短信成功-------------");
				} else {
					logger.error("--------------发送短信失败-------------");
					throw new BusinessException("发送短信失败！", "501");
				}
				return addSuccessUser;
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("发送短信失败！", "501");
			}
		}
		return addSuccessUser;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:更新用户信息。
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>创建日期2018年4月28日 下午4:55:14。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public String updateUser(Map<String, Object> paramMap) throws BusinessException, Exception {
		String objectId = (String) paramMap.get("objectId");
		String password = (String) paramMap.get("password");
		String profilePhoto = (String) paramMap.get("profilePhoto");
		// String remark = (String) paramMap.get("mobileNum");//暂时不开启手机号更新
		if (password != null && !("".equals(password))) {
			password = CryptUtil.md5Encrypt(password);
			paramMap.put("password", password);
		} else {
			paramMap.remove("password");
		}
		if (profilePhoto != null && !("".equals(profilePhoto))) {
			profilePhoto = fileServer + profilePhoto;
			paramMap.put("profilePhoto", profilePhoto);
		} else {
			paramMap.remove("profilePhoto");
		}
		paramMap.remove("objectId");
		paramMap.remove("createUser");
		paramMap.remove("createTime");
		paramMap.remove("modifyTime");
		// paramMap.remove("active");
		// paramMap.remove("status");
		if (paramMap.size() == 0) {
			return objectId;
		}
		User oldUser = (User) userDao.validateUser(objectId);
		if (null == oldUser) {
			throw new BusinessException("用户不存在！", "403");
		}

		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		if (null != paramMap.get("birthday")) {
			columnNames.add("birthday");
			columnValues.add(DateTransform((Long) paramMap.get("birthday")));
			paramMap.remove("birthday");
		}
		if (null != paramMap.get("empDate")) {
			columnNames.add("emp_date");
			columnValues.add(DateTransform((Long) paramMap.get("empDate")));
			paramMap.remove("empDate");
		}
		if (null != paramMap.get("empEndDate")) {
			columnNames.add("emp_end_date");
			columnValues.add(DateTransform((Long) paramMap.get("empEndDate")));
			paramMap.remove("empEndDate");
		}
		for (String key : paramMap.keySet()) {
			columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
			columnValues.add(paramMap.get(key));
		}

		columnNames.add("modify_time");
		columnValues.add(new Date());

		if (this.updateUser(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
				new String[] { "object_id" }, new Object[] { objectId }, null) == true) {
			return objectId;
		}
		;
		return null;
	}

	public boolean updateUser(String[] columnNames, Object[] columnValues, String[] whereNames, Object[] whereValues,
			String whereFilter) throws BusinessException, Exception {
		boolean updateResult = commonService.updateColumns("user", columnNames, columnValues, whereNames, whereValues,
				whereFilter);
		return updateResult;
	}

	/** 
	* @Title: queryUserList 
	* @Description 查询分页的用户列表
	* @param pageRequest
	* @param user
	* @return Page
	* @throws BusinessException
	* @throws Exception 
	* @author 南征
	* @date 2018年6月14日上午10:55:49
	*/ 
	@Override
	public Page queryUserList(PageRequest pageRequest, User user) throws BusinessException, Exception {
		return userDao.queryUserList(pageRequest, user);
	}

	/** 
	* @Title: DateTransform 
	* @Description 时间戳转化为Date类型
	* @param longTime
	* @return
	* @throws ParseException Date
	* @author 南征
	* @date 2018年6月14日上午10:44:26
	*/ 
	private Date DateTransform(Long longTime) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(longTime);
		String d = format.format(time);
		Date date = format.parse(d);
		return date;
	}
}
