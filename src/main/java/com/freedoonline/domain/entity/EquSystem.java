package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class EquSystem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String systemName;	//系统名称
	private Integer systemNum;	//系统编号
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	
	private String enpId;		//企业预留字段
	private String enpName;		//企业预留字段
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public Integer getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(Integer systemNum) {
		this.systemNum = systemNum;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
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
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public String getEnpId() {
		return enpId;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}
	public String getEnpName() {
		return enpName;
	}
	public void setEnpName(String enpName) {
		this.enpName = enpName;
	}
	
}
