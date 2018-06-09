package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class Process implements Serializable {

	private static final long serialVersionUID = 1L;

	private String objectId; // 流程管理id
	private String processName; // 流程名称
	private String processUser; // 流程所属人员
	private String maintenaceUser; // 巡检人员
	private String approvalUser; // 审批人员
	private Date startTime; // 流程开始时间
	private Date endTime; // 流程结束时间
	private Integer active; // 1:有效 0失效
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private String enpId;
	private Integer maintenaceType;
	private String maintenaceTypeName;

	
	

	public Integer getMaintenaceType() {
		return maintenaceType;
	}

	public void setMaintenaceType(Integer maintenaceType) {
		this.maintenaceType = maintenaceType;
	}

	public String getMaintenaceTypeName() {
		return maintenaceTypeName;
	}

	public void setMaintenaceTypeName(String maintenaceTypeName) {
		this.maintenaceTypeName = maintenaceTypeName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public String getMaintenaceUser() {
		return maintenaceUser;
	}

	public void setMaintenaceUser(String maintenaceUser) {
		this.maintenaceUser = maintenaceUser;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
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

	public String getEnpId() {
		return enpId;
	}

	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}

}
