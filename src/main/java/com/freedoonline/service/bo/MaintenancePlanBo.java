package com.freedoonline.service.bo;

import java.util.Date;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;

/**
  * 
  *<p>类描述：维护计划。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月3日 下午2:35:41。</p>
  */
public class MaintenancePlanBo extends PageRequest{
	
	private String objectId;
	private String name;			//任务名称
	private String equId;			//设备ID
	private String equName;			//设备名称
	private Integer type;			//服务类型(1:一般检查 2:年检 3:日常维护 其他暂定)
	private Integer frequency;		//服务频率
	private String maintenanceId;	//维护人员ID
	private String maintenanceName;	//维护人员名称
	private String maintenanceContent;	//维护内容
	private Date planStartTime;		//计划开始时间
	private Date planEndTime;		//计划结束时间
	private Date actualTime;		//实际时间
	
	private Integer status;			//维护状态(1:维护 0:未维护)
	private Integer overdue;		//是否逾期(1:未逾期 0:逾期)
	private String overdueRate;		//逾期率
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	private Integer fault;			//1:运行 0:故障 2:停止
	private String subNum;
	private String buildingId;
	private String systemNum;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getSubNum() {
		return subNum;
	}
	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}
	public Integer getFault() {
		return fault;
	}
	public void setFault(Integer fault) {
		this.fault = fault;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getOverdueRate() {
		return overdueRate;
	}
	public void setOverdueRate(String overdueRate) {
		this.overdueRate = overdueRate;
	}
	public String getEquName() {
		return equName;
	}
	public void setEquName(String equName) {
		this.equName = equName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getEquId() {
		return equId;
	}
	public void setEquId(String equId) {
		this.equId = equId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public String getMaintenanceId() {
		return maintenanceId;
	}
	public void setMaintenanceId(String maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	public String getMaintenanceName() {
		return maintenanceName;
	}
	public void setMaintenanceName(String maintenanceName) {
		this.maintenanceName = maintenanceName;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	public Date getActualTime() {
		return actualTime;
	}
	public void setActualTime(Date actualTime) {
		this.actualTime = actualTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
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
	
}
