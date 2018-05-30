package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class SubSystem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private Integer systemNum;	//子系统的所属系统
	private String subName;		//子系统名称
	private Integer subNum;		//子系统编号
	
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
	public Integer getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(Integer systemNum) {
		this.systemNum = systemNum;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public Integer getSubNum() {
		return subNum;
	}
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
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
