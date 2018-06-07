package com.freedoonline.common.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import cn.cloudlink.core.common.utils.web.WebContextFactoryUtil;

/**
  *<p>类描述：短信公共方法。</p>
  * @version v1.0.0.1。
  * @since JDK1.8。
  *<p>创建日期：2016年10月22日 下午2:41:14。</p>
 */
public class MessageUtil{
	

	/**
	  * <p>功能描述：阿里云短信接口。</p>
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2016年11月15日 上午8:51:35。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public static String sendShortMessage(SingleSendSmsRequest request)throws Exception{
		String regionId = WebContextFactoryUtil.getProperty("user.message.aliyuns.regionId");
		String accessKeyId = WebContextFactoryUtil.getProperty("user.message.aliyuns.accessKeyId");
		String secret = WebContextFactoryUtil.getProperty("user.message.aliyuns.secret");
		IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId,secret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
        String requestId = httpResponse.getRequestId();
        return requestId;
	}
	/**
	  * <p>功能描述：阿里大于发送短信接口。</p>
	  * @param aLiRequest
	  * @return
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2016年11月15日 上午8:48:57。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public static  SendSmsResponse sendShortMessage(SendSmsRequest request)throws Exception{
		// 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		String accessKeyId = WebContextFactoryUtil.getProperty("user.message.aliyuns.accessKeyId");
		String secret = WebContextFactoryUtil.getProperty("user.message.aliyuns.secret");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
	}
	
	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();
	}
}
