package com.freedoonline.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freedoonline.domain.ICommonDao;
import com.freedoonline.service.ICommonService;

/**
 * 
  *<p>类描述：公共方法</p>
  * @author Shenjie                                          [Shenjie]。
  * @version v1.0.0.1。
  * @since JDK1.6。
  *<p>创建日期：2017年5月2日 下午1:24:19。</p>
 */

@Service("commonServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class CommonServiceImpl implements ICommonService {
	@Resource(name="commonDaoImpl")
	private ICommonDao commonDao;
	
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
	@Override
	public boolean updateColumns(String tableName, String[] columnNames, Object[] columnValues, String[] whereNames,
			Object[] whereValues, String whereFilter) {
		commonDao.updateColumns(tableName, columnNames, columnValues, whereNames, whereValues, whereFilter);
		return true;
	}
	/**<p>功能描述：批量更新</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param sql
	  * @since JDK1.8。
	  * <p>创建日期:2017年5月2日 下午1:06:13。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	@Override
	public void batchUpdate(String[] sql) {
		commonDao.batchUpdate(sql);

	}
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
			String[] columnNames, Object[] columnValues,String whereFilter){
		commonDao.delete(tableName, columnNames, columnValues, whereFilter);
		return true;
	}
	
	@Override
	public boolean update(String sql,Object paramBean){
		 commonDao.update(sql, paramBean);
		return true;
	}
}
