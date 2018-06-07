package com.freedoonline.common.util;



/**
 * 类描述: 声明常量
 *
 * @author 赵建忠
 * @version 1.0
 * 创建时间： 2012-10-19 下午6:08:40 
 * JDK版本：sun jdk 1.6
 *********************************更新记录******************************
 * 版本：  <版本号>        修改日期：  <日期>        修改人： <修改人姓名>
 * 修改内容：  <修改内容描述>
 **********************************************************************
 */
public final class Constants
{
	public static final String ENTERPRISE_ADMIN_ROLE_ID = "af1ef12c-774e-4a76-bdf2-564267082d7d";   //企业管理员角色ID
	public static final String ENTERPRISE_APP_ADMIN_ROLE_ID = "b6e2e68b-c715-4f1f-b25f-83cc9edaad94"; //企业应用管理员角色ID
	public static final String SUPERADMIN_ROLE_CODE = "superadmin";   //超级管理员角色Code
	public static final String TREE_ROOTNODE_DEFAULT_HIERARCHY = "0001";  //默认树形结构根节点层级编号
	public static final int TREE_NODE_MAX_HIERARCHY_LENGTH = 12;   //树形结构最大层级
	public static final String DEFAULT_APP_ENP_ADMIN_ROLECODE = "defaultAppEnpAdmin";  //各应用分配给企业管理员的角色编码
	public static final String DEFAULT_APP_ENP_ADMIN_ROLENAME = "应用管理员";  //各应用分配给企业管理员的角色名称
	
	
    public static final String PASSWORD_INITIALIZATION = "111111";
    public static final String PRIVILEGEBOURL = "privilegebourl";
    public static final String MOKUAI_FUNCTION_TYPE_CAIDAN = "1";
    public static final String MOKUAI_FUNCTION_TYPE_SEARCH = "8";
    public static final String MOKUAI_FUNCTION_TYPE_CUT = "9";
    public static final String MOKUAI_FUNCTION_TYPE_GROUP = "10";
    public static final String I18N = "i18n";
    public static final String USER_IN_SESSION = "user_in_session";
    public static final String USER_PRIVILEGE_IN_SESSION = "user_privilege_in_session";
    public static final String USER_MENU_IN_SESSION = "user_menu_in_session";
    // 报表导出路径
    public static final String EXPORT_PATH = "expfile";
    public static final String RAQ_PATH = "reportFiles";

    public static final int PAGE_SIZE = 327670000;

    /**
     * 权限中类型， 1 为菜单
     */
    public static final int FUNCTION_TYPE_MENU = 1;
    /**
     * 2 为功能点按钮
     */
    public static final int FUNCTION_TYPE_BUTTON = 2;
    /**
     * 3 表单列
     */
    public static final int FUNCTION_TYPE_COLUM = 3;
    /**
     * 4 为功能点
     */
    public static final int FUNCTION_TYPE_BUTTON_GROUP = 4;
    /**
     * 5 为表单
     */
    public static final int FUNCTION_TYPE_GRID = 5;
    
    /**
     * 
     */
    //国际化
    public static final String I18N_HOME_PROPERTIES="jasframework-common-home";
    public static final String I18N_USER_PROPERTIES="jasframework-privilege-user";
    public static final String I18N_PRIVILEGE_PROPERTIES="jasframework-privilege-privilege";
    public static final String I18N_ROLE_PROPERTIES="jasframework-privilege-role";
    public static final String I18N_UNIT_PROPERTIES="jasframework-privilege-organization";
    
    public static final String I18N_COMMON="jasframework-common";
    
    //云片：智能匹配模板发送接口的http地址
    public static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    
}
