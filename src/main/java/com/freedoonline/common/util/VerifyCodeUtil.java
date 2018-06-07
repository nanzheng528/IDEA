package com.freedoonline.common.util;

public class VerifyCodeUtil {
	/**
	  *lt;p>功能描述：生成4位验证码</p>
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2016年10月22日 下午2:43:44。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public static String  generateValidateCode() {
       String vcode = "";
       for (int i = 0; i < 4; i++) {
           vcode = vcode + (int)(Math.random() * 9);
       }
       return vcode;
	}
	/**
	  * <p>功能描述：生成6位随机数字符串。</p>
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2016年12月6日 上午10:12:50。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public static String  generateSixCode() {
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int)(Math.random() * 9);
		}
		return vcode;
	}
}
