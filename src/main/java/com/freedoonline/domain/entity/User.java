package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：用户实体类。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:06:34。</p>
  */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String  objectId;    	// 用户ID               
	private String  userName ;      // 姓名
	private String  password;      	// 密码 
	private String  mobileNum;      // 联系电话 
//	private String  account;      	// 登录账号
	private String  email;      	// 电子邮件 
	private Integer  status=1;      // 用户账号状态1激活 -1冻结 0未激活 -2移除
	//private Integer sex; //性别 0男 1女
	private String profilePhoto;  	//头像
	
	private String  createUser;     // 创建人 
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date  createTime;      	// 创建时间 
	private String  modifyUser;     // 修改人
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date  modifyTime ;      // 修改时间
	private Integer active;      	// 有效标识
	private String remark;			// 备注
	private String enpId;			// 企业ID
	private String enpName;
	
	
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