package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;

/**
  * 
  *<p>类描述：用户实体类。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:06:34。</p>
  */
public class User extends PageRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String  objectId;    	// 用户ID   
	private String  account; 		//系统账号
	private String  empId;			//员工工号
	private String  departmentId;	//部门id
	private String  departmentName; //部门名称
	private String  positionId;	//职位id
	private String  positionName;	//职位名称
	private String  address;		//地址
	private String  buildingId;			//工作楼盘
	private String  buildingName;			//工作楼盘名称
	private String  superiorId;			//直接主管
	private String  superiorName;			//直接主管名称

	private String  orginalPwd;   //加密前密码
	private String  userName ;      // 姓名
	private String  password;      	// 密码 
	private String  mobileNum;      // 工作绑定电话 
	private String  mainMobileNum;	//主要电话号码
	private String  language;		//语言
	private String  sex;		//0:男 1:女
	//@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date  birthday;		//出生日期
	private Date  empDate;		//聘用日期
	private Date  empEndDate;		//终止日期
	private String  approvalLimits;		//审批限额
	private String  idCard;		//身份证号
	private String  job;		//岗位
	private String  isOutsourcing;		//是否外包( 0 : 不是 1: 是)
	private String  email;      	// 电子邮件 
	private Integer  status=1;      // 用户账号状态1激活 -1冻结 0未激活 -2移除
	//private Integer sex; //性别 0男 1女
	private String profilePhoto;  	//头像
	private Integer roleId; //角色id（2：职工 1：管理员）
	
	private String  createUser;     // 创建人 
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date  createTime;      	// 创建时间 
	private String  modifyUser;     // 修改人
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date  modifyTime ;      // 修改时间
	private Integer active;      	// 有效标识
	private String remark;			// 备注
	private String enpId;			// 企业ID
	private String enpName;         //企业组织名称
	
	
	
	public String getEnpName() {
		return enpName;
	}

	public void setEnpName(String enpName) {
		this.enpName = enpName;
	}

	public String getEnpId() {
		return enpId;
	}

	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getObjectId() {
		return objectId;
	}

	

	public String getOrginalPwd() {
		return orginalPwd;
	}

	public void setOrginalPwd(String orginalPwd) {
		this.orginalPwd = orginalPwd;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	
//	public Integer getSex() {
//		return sex;
//	}
//
//	public void setSex(Integer sex) {
//		this.sex = sex;
//	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}

	public String getMainMobileNum() {
		return mainMobileNum;
	}

	public void setMainMobileNum(String mainMobileNum) {
		this.mainMobileNum = mainMobileNum;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getEmpDate() {
		return empDate;
	}

	public void setEmpDate(Date empDate) {
		this.empDate = empDate;
	}

	public Date getEmpEndDate() {
		return empEndDate;
	}

	public void setEmpEndDate(Date empEndDate) {
		this.empEndDate = empEndDate;
	}

	public String getApprovalLimits() {
		return approvalLimits;
	}

	public void setApprovalLimits(String approvalLimits) {
		this.approvalLimits = approvalLimits;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getIsOutsourcing() {
		return isOutsourcing;
	}

	public void setIsOutsourcing(String isOutsourcing) {
		this.isOutsourcing = isOutsourcing;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
//	/** default constructor */
//	public User() {
//		this.status=0;
//		this.createTime = new Date();
//		this.modifyTime = new Date();
//		this.active = 1;
//		this.objectId = UUID.randomUUID().toString();
//	}
//
//	public User(String userName,String mobileNum,String password){
//		this();
//		this.userName = userName;
//		this.mobileNum = mobileNum;
//		this.password = password;
//	}
//	public User(String userName,String mobileNum,String password,String account,String email,Integer status){
//		this(userName,mobileNum,password);
//		this.account = account;
//		this.email = email;
//		this.status = status;
//	}
}