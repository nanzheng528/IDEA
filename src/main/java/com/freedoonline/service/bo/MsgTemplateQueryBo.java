package com.freedoonline.service.bo;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;

/**
  *<p>类描述：短信模板封装查询类。</p>
  * @author 赵杨                                          [zhaoyang]。
  * @version v1.0.0.1。
  * @since JDK1.8。
  *<p>创建日期：2017年4月15日 下午2:42:52。</p>
 */
public class MsgTemplateQueryBo  extends PageRequest{

	private String objectId;	//主键
	private String templateType;//模板类型,短信message 邮件 mail
	private String useCategory;//使用类别 ,0通用 注册为"1",验证码登录"2" 邀请用户"4"邀请注册时"5",企业认证审核通过"6",企业认证审核未通过"7",移交管理员"8"
	private String sceneType;	//场景类型:比如企业认证：成功1驳回-1
	private String templateNum; //模板编号
	private String status;     //启用状态 1启用0禁用,支持多组合
	private String active;     //有效标识 1有效 0删除，支持多组合
	private String supplier;  // 短信供应商
	private String signName;  // 短信签名，为区别各应用相同内容不同签名的模板，云片专用
	
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getUseCategory() {
		return useCategory;
	}
	public void setUseCategory(String useCategory) {
		this.useCategory = useCategory;
	}
	public String getSceneType() {
		return sceneType;
	}
	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}
	public String getTemplateNum() {
		return templateNum;
	}
	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
}
