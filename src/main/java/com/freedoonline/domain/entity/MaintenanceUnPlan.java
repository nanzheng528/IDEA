package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：故障信息。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月3日 下午3:17:17。</p>
 */
public class MaintenanceUnPlan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String equId;			//设备ID
	private String equName;			//设备名称
	private Integer equLevel;		//设备等级
	private String equModel;		//设备型号
	private String equSerialNum;	//设备序列号
	private String symptom;			//故障症状
//	private Date faultTime;			//故障时间
//	private Date repairTime;		//修复时间
	private Integer status;			//修复状态(1:修复 0:未修复)
	private String repairDetails;	//修复详情(解决方案)
	private String buildingId;		//楼宇ID
	private String buildingName;	//楼宇名称
	private String buildingFloor;	//楼层
	private String buildingArea;	//楼层区域(安装区域)
	private String serviceArea;		//服务区域
	private String manufacturer;	//生产厂商
	private Date manufactureTime;	//生产日期
	
	private String enpId;			//所属企业
	private String maintenanceId;	//维护人员ID
	private String maintenanceName;	//维护人员名称
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	
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
	public String getEquName() {
		return equName;
	}
	public void setEquName(String equName) {
		this.equName = equName;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
//	public Date getFaultTime() {
//		return faultTime;
//	}
//	public void setFaultTime(Date faultTime) {
//		this.faultTime = faultTime;
//	}
//	public Date getRepairTime() {
//		return repairTime;
//	}
//	public void setRepairTime(Date repairTime) {
//		this.repairTime = repairTime;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
//	public Integer getIsReplace() {
//		return isReplace;
//	}
//	public void setIsReplace(Integer isReplace) {
//		this.isReplace = isReplace;
//	}
//	public String getReplace() {
//		return replace;
//	}
//	public void setReplace(String replace) {
//		this.replace = replace;
//	}
	public String getEnpId() {
		return enpId;
	}
	public String getRepairDetails() {
		return repairDetails;
	}
	public void setRepairDetails(String repairDetails) {
		this.repairDetails = repairDetails;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
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
	
}
