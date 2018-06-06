package com.freedoonline.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.cloudlink.core.common.dataaccess.data.TreeItem;
import cn.cloudlink.core.common.exception.BusinessException;

public class CommonUtil {
	 /**lt;p>功能描述：判断第二个字符串中的任意一个是否存在第一个字符串中</p>
	  * <p> 申杰                                           [Shenjie]。</p>	
	  * @param first 第一个字符串，例如admin,superadmin
	  * @param second 第二个字符串，例如admin,user
	  * @return 如果存在返回true,不存在返回false
	  * @since JDK1.8。
	  * <p>创建日期:2016年10月25日 下午1:10:18。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][申杰][变更描述]。</p>
	 */
	public static boolean isExist(String first,String second) {
		String[] firstArray = first.split(",");
		String[] secondArray = second.split(",");
		boolean isExist = false;
		for (int i = 0; i < firstArray.length; i++) {
			for (int j = 0; j < secondArray.length; j++) {
				if (firstArray[i].equals(secondArray[j])) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}
	
	/**
	 * 方法描述：将用逗号隔开的字符串格式化为用每项加分号的用逗号隔开的字符串
	 * @param eventIds 用逗号隔开的字符串，如1111,2222,3333
	 * @return 如'1111','2222','3333'
	 */
	public static String getIdsFormat(String eventIds){
		String[] eventIdArray = eventIds.split(",");
		String result="";
		for(int i=0;i<eventIdArray.length;i++){
			if(i==eventIdArray.length-1){
				result+="'"+eventIdArray[i]+"'";
			}else{
				result+="'"+eventIdArray[i]+"',";
			}
		}
		return result;
	}
	
	/**
	 * 方法描述：去除重复数据
	 * @param list
	 * @return
	 */
	public static void reomoveDuplicatedStr(List<String> list){
		for(int i=0; i<list.size(); i++){
			for(int j=0; j<list.size();j++){
				if(i!=j&&list.get(i).equals(list.get(j))){
					list.remove(j);
					j--;
				}
			}
		}
	}
	/**
	 * 获取查询数据的 顶级节点集合
	 * 
	 * @param list
	 * @return
	 */
	public static  List<?> getParentList( List<?> list,Class<?> elementType){
		List<Object> parentList = new ArrayList<Object>();
		Boolean mark = true;
		if ( list != null && list.size() > 0 ){
			for( int h = 0 ; h < list.size() ; h ++ ){
				mark = true;
				Map<String, Object> parentMap = transBean2Map(list.get(h));
				for( int y = 0 ; y < list.size() ; y ++ ){
					Map<String, Object> obj = transBean2Map(list.get(y));
					if(obj.get("objectId").equals(parentMap.get("parentId")) ){
						mark = false;
						break;
					}
				}
				if(mark){
					try {
						Object parentObj = transMap2Bean(elementType,parentMap);
						parentList.add(parentObj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return parentList;
	}

	/**
	 * 
	 /**<p>功能描述：获取数据顶层节点集合</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param list 数据查询范围集合
	  * @param idKey 取值字段
	  * @param associatedField 关联的字段，一般为parentId
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午11:29:21。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static  List<?> getParentList( List<?> list,String idKey,String associatedField){
		List<Object> parentList = new ArrayList<Object>();
		if(list==null || list.size()==0){
			return parentList;
		}
		for( int i = 0 ; i < list.size() ; i ++ ){
			boolean parent = true;
			Map<String, Object> parentMap = transBean2Map(list.get(i));
			
			for( int j = 0 ; j < list.size() ; j ++ ){
				Map<String, Object> obj = transBean2Map(list.get(j));
				if(obj.get(idKey).equals(parentMap.get(associatedField)) ){
					parent = false;
					break;
				}
			}
			if(parent){
				parentList.add(list.get(i));
			}
		}
		return parentList;
	}
	

	
	 /**<p>功能描述：Bean转 Map</p>
	  * <p> Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map</p>
	  * <p> 赵杨                                          [zhaoyang]。</p>	
	  * @param Object
	  * @return Map<String, Object>
	  * @since JDK1.8。
	  * <p>创建日期:2017年3月7日 下午3:10:18。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][赵杨][变更描述]。</p>
	 */
   public static Map<String, Object> transBean2Map(Object obj) {
       if (obj == null) {
           return null;
       }
       if(obj instanceof Map){
       	return (Map<String, Object>) obj;
       }
       Map<String, Object> map = new HashMap<String, Object>();
       try {
           BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
           PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
           for (PropertyDescriptor property : propertyDescriptors) {
               String key = property.getName();
               // 过滤class属性
               if (!key.equals("class")) {
                   // 得到property对应的getter方法
                   Method getter = property.getReadMethod();
                   Object value = getter.invoke(obj);
                   map.put(key, value);
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return map;
   }
   /** <p>功能描述：将一个 Map 对象转化为一个 JavaBean </p>
	 * <p> 赵杨                                          [zhaoyang]。</p> 
    * @param type 要转化的类型  
    * @param map 包含属性值的 map  
    * @return 转化出来的 JavaBean 对象  
    * @throws IntrospectionException 如果分析类属性失败  
    * @throws IllegalAccessException 如果实例化 JavaBean 失败  
    * @throws InstantiationException 如果实例化 JavaBean 失败  
    * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
    * @return Map<String, Object>
	 * @since JDK1.8。
	 * <p>创建日期:2017年3月7日 下午3:10:18。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][赵杨][变更描述]。</p>
   */    
   public static Object transMap2Bean(Class type, Map map)    
           throws IntrospectionException, IllegalAccessException,InstantiationException, InvocationTargetException {    
       BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性    
       Object obj = type.newInstance(); // 创建 JavaBean 对象    
       // 给 JavaBean 对象的属性赋值    
       PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
       for (int i = 0; i< propertyDescriptors.length; i++) {    
           PropertyDescriptor descriptor = propertyDescriptors[i];    
           String propertyName = descriptor.getName();    
           if (map.containsKey(propertyName)) {    
               // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。    
               Object value = map.get(propertyName);    
   
               Object[] args = new Object[1];    
               args[0] = value;    
               descriptor.getWriteMethod().invoke(obj, args);    
           }    
       }    
       return obj;    
   } 
   /**
	* 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。
	* @author zxcvbnmzb
	* @param testStr 要+1的字符串
	* @return +1后的字符串
	* @exception NumberFormatException
	*/
	/**
	  * <p>功能描述：字符串后四位=1或-1。</p>
	  * <p> 赵杨                                           [zhaoyang]。</p>	
	  * @param testStr  要+1或-1的字符串
	  * @param args 标识：1为+1，-1为-1
	  * @return 修改后的字符串 
	  * @throws BusinessException
	  * @since JDK1.8。
	  * <p>创建日期:2017年4月12日 下午3:10:36。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
   public static String addOne(String testStr,Integer args)throws BusinessException{
	   // String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	  //  String numStr = strs[strs.length-1];//取出最后一组数字
		String numStr = testStr.substring(testStr.length()-4,testStr.length());
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	        int n = numStr.length();//取出字符串的长度
	        int num;
	        if(args==1){
	        	 num= Integer.parseInt(numStr)+1;//将该数字加一
	        }else{
	        	 num= Integer.parseInt(numStr)-1;//将该数字减一
	        }
	        String added = String.valueOf(num);
	        n = Math.min(n, added.length());
	        //拼接字符串
	        return testStr.subSequence(0, testStr.length()-n)+added;
	    }else{
	        throw new NumberFormatException();
	    }
	}
	 /**<p>功能描述：将属性字段和属性值转换为Map</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param columnNames
	  * @param columnValues
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月14日 下午4:17:47。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static Map<String,Object> columnToMap(String[] columnNames,Object[] columnValues){
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		int i=0;
		for(String name : columnNames){
			resultMap.put(name, columnValues[i]);
			i++;
		}
		return resultMap;
	}
	
	 /**<p>功能描述：生成随机数字符串</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param bit 位数
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月15日 下午3:26:31。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static String  generateRandomCode(int bit) {
		String vcode = "";
		for (int i = 0; i < bit; i++) {
			vcode = vcode + (int)(Math.random() * 9);
		}
		return vcode;
	}
	
	 /**<p>功能描述：封装树节点，并选中叶子节点</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param sourceList 被封装的源资源列表
	  * @param idKey ID取值字段
	  * @param textKey text取值字段
	  * @param packageAllItem 是否要封装所有节点，false只封装一级节点和二级节点
	  * @param leafList 叶子节点列表
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午9:58:24。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static List<TreeItem> packageTree(List<?> sourceList,String idKey,String textKey,
			String associatedField,boolean packageAllItem,List<?> leafList){
		//获取树节点
		List<TreeItem> items = packageTree(sourceList,idKey,textKey,associatedField,packageAllItem);
		packageTree(items,leafList,idKey);
		return items;
	}
	 /**<p>功能描述：选中叶子节点</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param treeItems 树节点集合
	  * @param leafList 叶子节点列表
	  * @param equalsKey  叶子节点取值字段
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午11:58:40。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static void packageTree(List<TreeItem> treeItems,List<?> leafList,String equalsKey){
		if(leafList==null || leafList.size()==0){
			return ;
		}
		for(TreeItem item : treeItems){
			boolean hasExist = hasExist(leafList,equalsKey,item.getId());
			if(hasExist){
				item.setChecked(true);
			}
			if(item.getChildren().size()>0){
				packageTree(item.getChildren(),leafList,equalsKey);
			}
		}
	}
	 /**<p>功能描述：封装树节点</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param sourceList 被封装的源资源列表
	  * @param idKey ID取值字段
	  * @param textKey text取值字段
	  * @param associatedField 关联属性字段，一般为parentId
	  * @param packageAllItem 是否要封装所有节点，false只封装一级节点和二级节点
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午9:58:24。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static List<TreeItem> packageTree(List<?> sourceList,String idKey,String textKey,
			String associatedField,boolean packageAllItem){
		//声明返回的结果
		List<TreeItem> items = new ArrayList<TreeItem>();
		if(sourceList.size()==0){
			return items;
		}
		//查找根节点，并封装
		List<?> rootList =getParentList(sourceList,idKey,associatedField);
		for(Object obj : rootList){
			Map<String, Object> objMap = transBean2Map(obj);
			TreeItem item = new TreeItem((String)objMap.get(idKey), (String)objMap.get(textKey));
			item.setAttributes(obj);
			item.setChildren(packageTreeChild(obj,sourceList,idKey,textKey,associatedField,packageAllItem));
			items.add(item);
		}
		return items;
	}
	/**<p>功能描述：封装孩子节点</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param parentItem 父元素
	  * @param sourceList 被封装的源资源列表
	  * @param idKey ID取值字段
	  * @param textKey text取值字段
	  * @param associatedField 关联属性字段，一般为parentId
	  * @param packageAllItem 是否封装所有子节点，false只封装直属节点
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午10:08:59。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static List<TreeItem> packageTreeChild(Object parentItem,List<?> sourceList,
			String idKey,String textKey,String associatedField,boolean packageAllItem){
		List<TreeItem> items = new ArrayList<>();
		Map<String, Object> parentItemMap = transBean2Map(parentItem);
		for(Object obj: sourceList){
			Map<String, Object> objMap = transBean2Map(obj);
			if(parentItemMap.get(idKey).equals(objMap.get(associatedField))){
				//如果遍历到直属孩子节点，则封装孩子节点
				TreeItem item = new TreeItem((String)objMap.get(idKey), (String)objMap.get(textKey));
				item.setAttributes(obj);
				//判断当前节点是否还有孩子节点，如果有，则设置state状态为closed
				boolean hasExist = hasExist(sourceList, associatedField, (String)objMap.get(idKey));
				if(hasExist){
					item.setState("closed");
				}
				//如果要封装所有节点，则递归遍历进行封装
				if(packageAllItem){
					item.setChildren(packageTreeChild(obj,sourceList,idKey,textKey,associatedField,packageAllItem));
				}
				items.add(item);
			}
		}
		return items;
	}


	/**
	 * 
	 /**<p>功能描述：获取叶子节点列表</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param list
	  * @param idKey 取值字段
	  * @param associatedField 关联的字段，一般为parentId
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午9:03:03。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static List<?> getLeafList(List<?> list,String idKey,String associatedField) {
		//叶子节点集合列表
		List<Object> leafList = new ArrayList<Object>();
		if(list==null || list.size()==0){
			return leafList;
		}
		for(int i = 0; i < list.size(); i++){
			boolean leaf = true;
			Map<String, Object> parentMap = transBean2Map(list.get(i));
			for( int j = 0 ; j < list.size() ; j ++ ){
				Map<String, Object> obj = transBean2Map(list.get(j));
				if(parentMap.get(idKey).equals(obj.get(associatedField))){
					leaf = false;
					break;
				}
			}
			if(leaf){
				leafList.add(list.get(i));
			}
		}
		return leafList;
	}
	
	/**
	 * 
	 /**<p>功能描述：判断元素是否存在</p>
	  * <p> Shenjie                                           [Shenjie]。</p>	
	  * @param list 查询范围
	  * @param equalsKey 要比较的属性字段
	  * @param equalsValue 要比较的属性值
	  * @return
	  * @since JDK1.6。
	  * <p>创建日期:2017年4月28日 上午9:46:13。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][Shenjie][变更描述]。</p>
	 */
	public static boolean hasExist(List<?> list,String equalsKey,Object equalsValue){
		if(list==null || list.size()==0){
			return false;
		}
		for(Object element : list){
			Map<String, Object> elementMap = transBean2Map(element);
			if(equalsValue.equals(elementMap.get(equalsKey))){
				return true;
			}
		}
		return false;
	}
	 /**
	   * <p>功能描述：基于HttpClient 4.3的通用POST方法。</p>
	   * @author Zhaoyang                       [Zhaoyang]。	
	   * @param url 提交的URL
	   * @param paramsMap 提交<参数，值>Map
	   * @return 提交响应
	   * @throws Exception
	   * @since JDK1.8。
	   * <p>创建日期:2017年9月15日 下午3:29:16。</p>
	   * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	 public static String post(String url, Map<String, String> paramsMap)throws Exception{
	     CloseableHttpClient client = HttpClients.createDefault();
	     String responseText = "";
	     CloseableHttpResponse response = null;
	     HttpPost method = new HttpPost(url);
	     if (paramsMap != null) {
	         List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	         for (Map.Entry<String, String> param : paramsMap.entrySet()) {
	             NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
	             paramList.add(pair);
	         }
	         method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
	     }
	     response = client.execute(method);
	     HttpEntity entity = response.getEntity();
	     if (entity != null) {
	         responseText = EntityUtils.toString(entity, "UTF-8");
	     }
	     return responseText;
	  }
	 /**
	  * <p>功能描述：将组织机构树转换成组织机构树Map
	  * </p>
	  * @return  map
	  * @since JDK1.8。
	  * <p>创建日期:2017年06月05日 下午1:53:08。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][赵杨][变更描述]。</p>
	 */
	public static Map<String,Object> turnTree2Map(List<TreeItem> treeList)throws Exception{
		Map<String,Object> resultMap = new HashMap<>();
		if(treeList == null || treeList.size()<=0){
			return resultMap;
		}
		treeList.forEach(item->{
			resultMap.put(item.getId(), item);
			List<TreeItem> childrenList = item.getChildren();
			try {
				Map<String, Object> result = turnTree2Map(childrenList);
				resultMap.putAll(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return resultMap;
	}
}
