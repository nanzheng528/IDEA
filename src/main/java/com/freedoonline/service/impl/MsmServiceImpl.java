package com.freedoonline.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.freedoonline.common.util.CommonUtil;
import com.freedoonline.common.util.Constants;
import com.freedoonline.common.util.MessageUtil;
import com.freedoonline.domain.IMsmDao;
import com.freedoonline.service.IMsmService;
import com.freedoonline.service.bo.MsgTemplateQueryBo;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;
import cn.cloudlink.core.common.utils.web.WebContextFactoryUtil;
import cn.cloudlink.core.framework.domain.entity.MsgTemplate;
import net.sf.json.JSONObject;

/**
 *<p>类描述：短信服务SMS业务接口实现类。</p>
 * @version v1.0.0.1。
 * @since JDK1.8。
 *<p>创建日期：2016年11月16日 下午5:46:04。</p>
*/
@Service
public class MsmServiceImpl implements IMsmService {
	
	@Resource(name="msmDaoImpl")
	private IMsmDao msmDao;
	
	/**
	  * <p>功能描述：发送信息。</p>
	  * @param  短信服务商(阿里云消息为"aliyun",阿里云短信为"alidayu",云片为"yunpian")
	  * @param paramMap 参数
	  * @return
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2017年4月15日 下午1:55:44。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@Override
	public Object sendMsm(Map<String, Object> paramMap) throws BusinessException,Exception {
		BusinessResult result = new BusinessResult(); 
		String suppliers = WebContextFactoryUtil.getProperty("user.message.suppliers");
		if(!StringUtil.hasText(suppliers)){
			throw new BusinessException("短信供应商配置不合法！", "406");
		}
		String[] supplierArray =suppliers.split(","); 
		String mobileNum = (String) paramMap.get("mobileNum");
		boolean isIntl = mobileNum.startsWith("+")?true:false;//true为发国际短信
		if(isIntl){//发国际短信
			if(supplierArray.length<=1){
				throw new BusinessException("短信供应商配置不合法！", "406");
			}
			String supplier= supplierArray[1];
			if("yunpian".equals(supplier)){//云片
				return this.sendSmsByYunPian(paramMap);
			}
		}
		//发国内短信
		String supplier= supplierArray[0];
		/*if("aliyun".equals(supplier)){//阿里云
			return this.sendSmsByAliYun(paramMap);
		}*/
		if("aliyun".equals(supplier)){//阿里大于
			return this.sendSmsByAliDaYu(paramMap);
		}
		if("yunpian".equals(supplier)){//云片
			return this.sendSmsByYunPian(paramMap);
		}
		return result;
	}
	/**
	  * <p>功能描述:阿里云发送验短信。</p>	
	  * @param UseCategory 用途类型
	  * 		用户注册验证码"1",登录确认验证码"2",验证码通用模版"3",邀请用户"4",邀请注册时"5",企业认证审核通过"6",企业认证审核未通过"7",移交管理员"8"
	  * @param mobileNum 电话号码
	  * @param verifyCode 验证码
	  * @param paramMap 其他参数的map集合
	  * @return  阿里云短信接口统一返回参数requestId
	  * <p> 赵杨                                          [zhaoyang]</p>
	  * @since JDK1.8。
	  * <p>创建日期2016年10月22日 下午12:20:53。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public BusinessResult sendSmsByAliYun(Map<String,Object> paramMap)throws Exception{
		String mobileNum = (String) paramMap.get("mobileNum");
		String privateMobileNum = mobileNum.substring(0, 3)+"****"+mobileNum.substring(mobileNum.length()-4,mobileNum.length());
		paramMap.put("privateMobileNum", privateMobileNum);
		
		MsgTemplateQueryBo queryBo = new MsgTemplateQueryBo();
		queryBo.setTemplateType("message");//查找短信模板
		queryBo.setUseCategory((String) paramMap.get("useCategory")); 
		queryBo.setSceneType((String) paramMap.get("sceneType"));
		queryBo.setSupplier("aliyun"); 
		MsgTemplate tempalte = getMsgTemplate(queryBo);
		//发送短信参数绑定
		SingleSendSmsRequest request = new SingleSendSmsRequest();
		request.setSignName((String) paramMap.get("signName"));
		request.setRecNum(mobileNum);
		request.setTemplateCode(tempalte.getTemplateNum());//短信编码
		Map<String,Object> param =JSONObject.fromObject(tempalte.getTemplateParam());
		Set<String> keyset=  param.keySet();
		for(String key : keyset){
			param.put(key, paramMap.get(param.get(key)));
		}
		String paramString =param.toString();
		request.setParamString(paramString);
		MessageUtil.sendShortMessage(request);
		return new BusinessResult();
	}
	/**
	  * <p>功能描述：云片单条短信发送。</p>
	  * @param paramMap
	  * @return
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2017年9月25日 上午9:31:31。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public BusinessResult sendSmsByYunPian(Map<String,Object> paramMap)throws Exception{
		String mobileNum = (String) paramMap.get("mobileNum");
		String privateMobileNum = mobileNum.substring(0, 3)+"****"+mobileNum.substring(mobileNum.length()-4,mobileNum.length());
		paramMap.put("privateMobileNum", privateMobileNum);
		
		MsgTemplateQueryBo queryBo = new MsgTemplateQueryBo();
		queryBo.setTemplateType("message");//查找短信模板
		queryBo.setUseCategory((String) paramMap.get("useCategory")); 
		queryBo.setSceneType((String) paramMap.get("sceneType"));
		queryBo.setSupplier("yunpian"); 
		queryBo.setSignName((String) paramMap.get("signName"));
		MsgTemplate tempalte = getMsgTemplate(queryBo);
		Map<String,Object> tempalteParam =JSONObject.fromObject(tempalte.getTemplateParam());
		String text = tempalte.getTemplateContent();//模板内容
		Set<String> keyset=  tempalteParam.keySet();
		for(String key : keyset){
			text = text.replace(key, (String) paramMap.get(tempalteParam.get(key)));
		}
		text = text.replace("#", "");
		String apikey = WebContextFactoryUtil.getProperty("user.yunpian.apikey");
		Map<String,String> sendParams= new HashMap<>();
		sendParams.put("text", text);
		sendParams.put("mobile", mobileNum);
		sendParams.put("apikey", apikey);
		String responseText = CommonUtil.post(Constants.URI_SEND_SMS, sendParams);
		JSONObject response = JSONObject.fromObject(responseText);
		BusinessResult result = new BusinessResult();
		if((Integer)response.get("code")!=0){
			result.setSuccess(-1);
			result.setCode(String.valueOf((Integer)response.get("code")));
			result.setMsg((String)response.get("msg"));
			return result;
		}
		return result;
	}
	/**
	  * <p>功能描述：获取短信模板。</p>
	  * @param queryBo
	  * @return
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2017年9月15日 上午11:14:50。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	private MsgTemplate getMsgTemplate(MsgTemplateQueryBo queryBo)throws Exception{
 		List<MsgTemplate> templateList = msmDao.queryListMsgTemplate(queryBo);
		if(templateList.size()!=1){
			throw new Exception("模板异常");
		}
		return templateList.get(0);
	}
	/**
	  * <p>功能描述:阿里大于发送验证码。</p>	
	  * @param useMode 用途类型（注册为"1",验证码登录"2",通用模板"3"）
	  * @param mobileNum 电话号码
	  * @param verifyCode 验证码
	  * @param paramMap 其他参数的map集合
	  * @return  BizResult
	  * <p> 赵杨                                          [zhaoyang]</p>
	  * @since JDK1.8。
	  * <p>创建日期2016年10月22日 下午12:20:53。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public BusinessResult sendSmsByAliDaYu(Map<String,Object> paramMap)throws Exception{
		String mobileNum = (String) paramMap.get("mobileNum");
		String privateMobileNum = mobileNum.substring(0, 3)+"****"+mobileNum.substring(mobileNum.length()-4,mobileNum.length());
		paramMap.put("privateMobileNum", privateMobileNum);
		
		MsgTemplateQueryBo queryBo = new MsgTemplateQueryBo();
		queryBo.setTemplateType("message");//查找短信模板
 		queryBo.setUseCategory((String) paramMap.get("useCategory")); 
		queryBo.setSceneType((String) paramMap.get("sceneType"));
		queryBo.setSupplier("aliyun"); 
		MsgTemplate tempalte = getMsgTemplate(queryBo);
		//模板参数
		Map<String,Object> param =JSONObject.fromObject(tempalte.getTemplateParam());
		Set<String> keyset=  param.keySet();
		for(String key : keyset){
			param.put(key, paramMap.get(param.get(key)));
		}
		String templateParamString =param.toString();
		//发送短信参数绑定
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
        request.setPhoneNumbers(mobileNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName((String) paramMap.get("signName"));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(tempalte.getTemplateNum());
        //可选:模板中的变量替换JSON串
        request.setTemplateParam(templateParamString);
        SendSmsResponse response= MessageUtil.sendShortMessage(request);
        BusinessResult result = new BusinessResult();
        if(response.getCode() != null && "OK".equals(response.getCode())){
        	return result;
        }
        result.setSuccess(-1);
        result.setCode(response.getCode());
        result.setMsg(response.getMessage());
        return result;
	}
	

}
