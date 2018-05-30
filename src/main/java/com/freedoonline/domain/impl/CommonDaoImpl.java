package com.freedoonline.domain.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.freedoonline.domain.ICommonDao;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.NamedParameterJdbcDao;

@Repository(value="commonDaoImpl")
public class CommonDaoImpl implements ICommonDao {
	@Resource
	private BaseJdbcDao baseJdbcDao;
	@Resource(name="namedParameterJdbcDao")
	private NamedParameterJdbcDao namedParameterJdbcDao;
	
	 /**<p>功能描述：更新信息</p>
	  * @param columnNames    字段名集合数组
	  * @param columnValues   字段值集合数组
	  * @param whereNames    过滤字段名集合数组
	  * @param whereValues    过滤字段值集合数组
	  * @param whereFilter    过滤子句
	  * @return 更新结果：true更新成功，false更新失败
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2017年5月2日 下午1:05:37。</p>
	 */
	@Override
	public int updateColumns(String tableName, String[] columnNames, Object[] columnValues, String[] whereNames,
			Object[] whereValues, String whereFilter) {
		int result = baseJdbcDao.updateColumn(tableName, columnNames, columnValues,whereNames, whereValues,whereFilter);
		return result;
	}
	 /**<p>功能描述：批量更新</p>
	  * @param sql
	  * @since JDK1.8。
	  * <p>创建日期:2017年5月2日 下午1:06:13。</p>
	 */
	@Override
	public void batchUpdate(String[] sql) {
		baseJdbcDao.batchExecute(sql);
	}
	 /**<p>功能描述：删删除息</p>
	  * @param tableName 表名
	  * @param columnNames    字段名集合数组
	  * @param columnValues   字段值集合数组
	  * @param whereFilter    过滤子句
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月13日 下午6:10:41。</p>
	 */
	@Override
	public boolean delete(String tableName,
			String[] columnNames, Object[] columnValues,String whereFilter){
		baseJdbcDao.delete(tableName, columnNames, columnValues, whereFilter);
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int update(String sql,Object paramBean){
		if(paramBean instanceof Map){
			return namedParameterJdbcDao.update(sql, (Map)paramBean);
		}
		int result = namedParameterJdbcDao.update(sql, paramBean);
		return result;
	}
}
