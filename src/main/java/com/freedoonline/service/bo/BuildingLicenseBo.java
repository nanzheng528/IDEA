package com.freedoonline.service.bo;

import java.util.Date;

public class BuildingLicenseBo {
	
	private String objectId;
	private String buildingId;		//楼宇ID
	private String buildingName;	//楼宇名称
	private Integer buildingType;	//楼宇类型
	private Integer authType;		//认证编号
	private String authName;		//认证名称
	private Integer authStatus;		//认证状态 1:认证 2:未认证
	private Date endTime;			//过期时间
	//private String enpId;			//企业ID
	private Integer overdue;		//是否过期 1:未过期 2:过期
	
	//private String createUser;
	private Date createTime;
	//private String modifyUser;
	private Date modifyTime;
	private String remark;
	//private Integer active;
	private String sign;
	
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(Integer buildingType) {
		this.buildingType = buildingType;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public Integer getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
//	public String getEnpId() {
//		return enpId;
//	}
//	public void setEnpId(String enpId) {
//		this.enpId = enpId;
//	}
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}
//	public String getCreateUser() {
//		return createUser;
//	}
//	public void setCreateUser(String createUser) {
//		this.createUser = createUser;
//	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
//	public String getModifyUser() {
//		return modifyUser;
//	}
//	public void setModifyUser(String modifyUser) {
//		this.modifyUser = modifyUser;
//	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
//	public Integer getActive() {
//		return active;
//	}
//	public void setActive(Integer active) {
//		this.active = active;
//	}
	
}
