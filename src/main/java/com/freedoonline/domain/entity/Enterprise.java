package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
  * 
  *<p>类描述：企业实体类。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月1日 下午12:19:42。</p>
  */
public class Enterprise implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//企业基本信息
	private String objectId; //企业ID
	private String enterpriseName; //企业名称
	private Integer authenticateStatus;  //认证状态
	private String registerNum;  	//企业注册号
	private Integer enterpriseScale; 	//企业规模
	private String province;  //省份（Code）
	private String city;  //市（Code）
	private String county;  //区（县）（Code）
	private String address; 	//地址（省、市、区）
	private String detailAddress; 	//详细地址
	private String telephoneNum; 	//联系电话
	private Integer status=1;   //企业状态，1正常 -1冻结
	//private String fromAppId; 	//注册来源
	//private String fromAppName; 	//注册来源名称
	private String remark; 	//备注
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime; 	//创建时间
	private String createUser; 	//创建人
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date modifyTime; 	//修改时间
	private String modifyUser; 	//修改人
	private Integer active; 	//有效标识
	private Integer enterpriseType; //企业类型 0测试企业 1正式企业
	private String enpAdmin; //企业管理员ID
	private String enpAdminName; //企业管理员姓名
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date authTime; //企业认证时间
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Integer getAuthenticateStatus() {
		return authenticateStatus;
	}
	public void setAuthenticateStatus(Integer authenticateStatus) {
		this.authenticateStatus = authenticateStatus;
	}
	public String getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}
	public Integer getEnterpriseScale() {
		return enterpriseScale;
	}
	public void setEnterpriseScale(Integer enterpriseScale) {
		this.enterpriseScale = enterpriseScale;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getTelephoneNum() {
		return telephoneNum;
	}
	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	public String getEnpAdmin() {
		return enpAdmin;
	}
	public void setEnpAdmin(String enpAdmin) {
		this.enpAdmin = enpAdmin;
	}
	public String getEnpAdminName() {
		return enpAdminName;
	}
	public void setEnpAdminName(String enpAdminName) {
		this.enpAdminName = enpAdminName;
	}
	public Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
}
