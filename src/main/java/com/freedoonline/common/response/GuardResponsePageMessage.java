package com.freedoonline.common.response;

import cn.cloudlink.core.common.dataaccess.data.Page;



/**
  * 
  *<p>类名：GuardResponsePageMessage.java</p>
  * @Descprition 响应返回分页类型
  * @author 南征
  * @version 1.0
  * @since JDK1.8
  *<p>创建日期：上午9:49:04</p>
  */
public class GuardResponsePageMessage<T> {
	/**
	 * 状态 1 成功  -1 失败
	 */
	private int stat;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 记录数
	 */
	private Integer totalElements;
	
	
	/**
	 * 页数
	 */
	private Integer totalPages;
	
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	
	/**
	 * 第几页
	 */
	private Integer pageNum;
	
	/**
	 * 是否是第一页
	 */
	private Boolean first;
	
	/**
	 * 是否是最后一页
	 */
	private Boolean last;
	/**
	 * 数据
	 */
	private T val;

	
	

	public int getStat() {
		return stat;
	}
	public String getMsg() {
		return msg;
	}
	public Integer getTotalElements() {
		return totalElements;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public Boolean getFirst() {
		return first;
	}
	public Boolean getLast() {
		return last;
	}
	public T getVal() {
		return val;
	}
	


	
	/** 
	* @Title: GuardResponsePageMessage
	* @Description: 
	* @param stat
	* @param msg
	* @param page 
	* @author 南征
	* @date 2018年6月14日上午9:42:25
	*/ 
	@SuppressWarnings("unchecked")
	private GuardResponsePageMessage(int stat, String msg, Page page){
		this.stat = stat;
		this.msg = msg;
		this.val = (T) page.getResult();
		this.totalElements = (int) page.getTotalLength();
		this.totalPages = getTotalPages(page);
		this.pageSize = page.getPageSize();
		this.pageNum = page.getPageNum();
		this.first = page.getPageNum() == 1;
		this.last = page.getPageNum() == totalPages;
		
	}
	
	/** 
	* @Title: getTotalPages 
	* @Description: 计算总页数
	* @param page
	* @return int
	* @author 南征
	* @date 2018年6月14日上午9:40:57
	*/ 
	private int getTotalPages (Page page){
		return (int) Math.ceil((page.getTotalLength() *1.0/ page.getPageSize())); 
	}
	
	
	/** 
	* @Title: 返回分页数据响应的静态方法
	* @Description 返回分页数据响应的静态方法
	* @param page
	* @return GuardResponsePageMessage<T>
	* @author 南征
	* @date 2018年6月14日上午9:50:33
	*/ 
	public static <T> GuardResponsePageMessage<T> creatBySuccessPageData(Page page){
		return new GuardResponsePageMessage<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),page);
	}
	
}
