package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：维护计划。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月3日 下午2:35:41。</p>
  */
public class MaintenancePlan implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String name;			//任务名称
	private String equId;			//设备ID
	private String equName;			//设备名称
	private Integer equLevel;		//设备等级
	private String equModel;		//设备型号
	private String equSerialNum;	//设备序列号
	private String equNum;			//设备编号
	
	private Integer type;			//服务类型(1:一般检查 2:年检 3:日常维护 其他暂定)
	private Integer frequency;		//服务频率
	private String maintenanceId;	//维护人员ID
	private String maintenanceName;	//维护人员名称
	private Date planStartTime;			//计划时间
	private Date planEndTime;
	private Date actualTime;		//实际时间
	private Integer status;			//维护状态(1:维护 0:未维护)
	
	private Integer isOverdue;		//是否逾期(1:未逾期 0:逾期)
	
	private Integer fault;			//是否故障(1:修复 0故障 2初始状态未知)
	private String symptom;			//症状
	private String repairDetails;	//修复详情(解决方案)
	
	private Integer backlog;		//待办数量
	private Integer complete;		//完成数量
	private Integer remainder;		//剩余数量
	private Integer overdue;		//逾期数量
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	
	private String serviceArea;		//服务区域
	private String manufacturer;	//生产厂商
	private Date manufactureTime;	//生产日期
	
	private String buildingName;	//楼宇名称
	private String buildingId;		//楼宇ID
	private String buildingFloor;	//楼层
	private String buildingArea;	//楼层区域(安装区域)
	
	//---
	private String subNum;			//子系统编号
	private String sign;			
	//---
	
	public Integer getIsOverdue() {
		return isOverdue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSubNum() {
		return subNum;
	}
	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}
	public Integer getEquLevel() {
		return equLevel;
	}
	public void setEquLevel(Integer equLevel) {
		this.equLevel = equLevel;
	}
	public String getEquModel() {
		return equModel;
	}
	public void setEquModel(String equModel) {
		this.equModel = equModel;
	}
	public String getEquSerialNum() {
		return equSerialNum;
	}
	public void setEquSerialNum(String equSerialNum) {
		this.equSerialNum = equSerialNum;
	}
	public String getEquNum() {
		return equNum;
	}
	public void setEquNum(String equNum) {
		this.equNum = equNum;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getManufactureTime() {
		return manufactureTime;
	}
	public void setManufactureTime(Date manufactureTime) {
		this.manufactureTime = manufactureTime;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingFloor() {
		return buildingFloor;
	}
	public void setBuildingFloor(String buildingFloor) {
		this.buildingFloor = buildingFloor;
	}
	public String getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}
	public Integer getBacklog() {
		return backlog;
	}
	public void setBacklog(Integer backlog) {
		this.backlog = backlog;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getRemainder() {
		return remainder;
	}
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
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
	public Integer getFault() {
		return fault;
	}
	public void setFault(Integer fault) {
		this.fault = fault;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getRepairDetails() {
		return repairDetails;
	}
	public void setRepairDetails(String repairDetails) {
		this.repairDetails = repairDetails;
	}
	
}
