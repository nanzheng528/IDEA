package com.freedoonline.domain;

public interface ICommonDao {
	 /**<p>功能描述：更新信息</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param columnNames    字段名集合数组
	  * @param columnValues   字段值集合数组
	  * @param whereNames    过滤字段名集合数组
	  * @param whereValues    过滤字段值集合数组
	  * @param whereFilter    过滤子句
	  * @return 更新结果：true更新成功，false更新失败
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2017年5月2日 下午1:05:37。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public int updateColumns(String tableName,String[] columnNames, Object[] columnValues, 
			String[] whereNames,Object[] whereValues,String whereFilter);
	 /**<p>功能描述：批量更新</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param sql
	  * @since JDK1.8。
	  * <p>创建日期:2017年5月2日 下午1:06:13。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public void batchUpdate(String[] sql);
	 /**<p>功能描述：删除记录</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param tableName 表名
	  * @param columnNames    字段名集合数组
	  * @param columnValues   字段值集合数组
	  * @param whereFilter    过滤子句
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月13日 下午6:10:41。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public boolean delete(String tableName,
			String[] columnNames, Object[] columnValues,String whereFilter);
	
	 /**
	  * <p>功能描述：修改记录，应用于"update..."语句。</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param sql SQL语句，参数以变量代替。如"column1=:col1"
	  * @param paramBean 参数变量Map，变量名需和sql中一致
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年10月30日 下午5:04:23。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public int update(String sql, Object paramBean);
}
