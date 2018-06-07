package com.freedoonline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freedoonline.common.util.VerifyCodeUtil;
import com.freedoonline.service.IMsmService;

import cn.cloudlink.core.common.base.controller.BaseController;
import cn.cloudlink.core.common.cache.RedisCacheService;
import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

/**
  *<p>类描述：校验码控制管理层。</p>
  * @version v1.0.0.1。
  * @since JDK1.8。
  *<p>创建日期：2017年4月15日 下午12:04:31。</p>
 */
@RestController()
@RequestMapping("verfy")
public class VerifyController extends BaseController{

	@Resource(name="redisCacheService")
	 private RedisCacheService redisCacheService;
	 @Resource(name="msmServiceImpl")
	 private IMsmService msmService;
	 
	/**
	  *<p>功能描述：生成注册验证码并发送（应用场景：注册、验证码登录、修改密码）。</p>
	  * @param request
	  * @param sendMode 发送号码类型（手机为"1",email为"2"）,默认为1
	  * @param useCategory 使用场景 注册 regist 登录login 修改密码general
	  * @param sceneType 场景类型: 注册为空,登录 verfyCode,修改密码为空
	  * @param sendNum 接收验证码账号(包括手机号、email)
	  * @param signName 短信签名，不可为空
	  * @return 成功：{success:1,rows:[{msg:"message", verifyCode:"验证码"}]}
	  *			失败：{success:-1,msg："对应错误信息",code:"对应错误编码"}
	  * @since JDK1.8。
	  * <p>创建日期:2016年10月24日 下午2:18:34。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@RequestMapping(value="getVerifyCode",method = RequestMethod.POST)
	public Object getVerifyCode(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
		String sendMode =paramMap.get("sendMode")!=null?(String) paramMap.get("sendMode"):"1";
		String useCategory = (String) paramMap.get("useCategory");
		String sceneType = (String) paramMap.get("sceneType");
		String sendNum = (String) paramMap.get("sendNum");
		String signName = (String) paramMap.get("signName");
		if(!StringUtil.hasText(sendMode)){
			return new BusinessResult(-1, "403", "验证类型不能为空！");
		}
		if(!StringUtil.hasText(useCategory)){
			return new BusinessResult(-1, "403", "使用场景不能为空！");
		}
		if(!StringUtil.hasText(sendNum)){
			return new BusinessResult(-1, "403", "接收号码不能为空！");
		}
		if(!StringUtil.hasText(signName)){
			return new BusinessResult(-1, "403", "短信签名不能为空！");
		}
		try{
			String verifyCode=VerifyCodeUtil.generateValidateCode();//生成验证码
			BusinessResult sendResult = new BusinessResult(); 
			Map<String, Object> sendParamMap = new HashMap<>(); 
			if(sendMode.equals("1")){//手机
				sendParamMap.put("useCategory", useCategory);
				sendParamMap.put("sceneType", sceneType);
				sendParamMap.put("mobileNum", sendNum);
				sendParamMap.put("code", verifyCode);
				sendParamMap.put("signName", signName);
				sendParamMap.put("effectiveTime", "5");
				sendParamMap.put("time", "5分钟");
				sendResult=(BusinessResult) msmService.sendMsm(sendParamMap);
			}else{//email
			}
			ArrayList<Object> list = new ArrayList<>();
			if(sendResult.getSuccess()==1){//发送成功
				redisCacheService.setKeyAndValue(sendNum, verifyCode);
				redisCacheService.expirse(sendNum, 5, TimeUnit.MINUTES);
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("verifyCode",verifyCode );
				list.add(dataMap);
			}
			sendResult.setRows(list);
			return sendResult;
			
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			getMicroserviceLogTemplate().error(e.getMessage());
			return new BusinessResult(-1, "400", e.getMessage());
		}
	}
	/**
	  *<p>功能描述：验证码校验。</p>
	  * @param request
	  * @param sendNum 接收验证码账号(包括手机号、email)
	  * @param verifyCode 验证码
	  * @return 成功：{success:1,rows:[]}
	  *			失败：{success:-1,msg："对应错误信息",code:"对应错误编码"}
	  * @since JDK1.8。
	  * <p>创建日期:2016年10月24日 下午2:18:56。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@RequestMapping(value="checkVerifyCode",method = RequestMethod.GET)
	public Object checkVerifyCode(HttpServletRequest request,@RequestParam(value="sendNum") String sendNum,
			@RequestParam(value="verifyCode") String verifyCode){
		if(!StringUtil.hasText(sendNum)){
			return new BusinessResult(-1, "403", "接收号码不能为空！");
		}
		if(!StringUtil.hasText(verifyCode)){
			return new BusinessResult(-1, "403", "验证码不能为空！");
		}
		try{
			String sessionVerifyCode =(String) redisCacheService.getValue(sendNum);
			if(!StringUtil.hasText(sessionVerifyCode)){
				return new BusinessResult(-1, "PU05001", "验证码超时或电话号码不一致");
			}
			if(!verifyCode.equals(sessionVerifyCode)){//验证码错误
				return new BusinessResult(-1, "PU05000", "验证码错误");
			}
			return new BusinessResult(Arrays.asList(new HashMap<>()));
		}catch(BusinessException e){
			return new BusinessResult(-1, e.getCode(), e.getMessage());
		}catch(Exception e){
			getMicroserviceLogTemplate().error(e.getMessage());
			return new BusinessResult(-1, "400", e.getMessage());
		}
		
	}
}
