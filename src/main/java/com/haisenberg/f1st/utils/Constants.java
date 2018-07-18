package com.haisenberg.f1st.utils;

public class Constants {
	// 全局是、否
	public final static int YES = 0;//是
	public final static int NO = 1;	//否
	
	// 请求返回flag
	public final static int SUCCESS_RESPONSE = 0;
	public final static int ERROR_RESPONSE = 1;

	// 是否可用
	public final static int DISABLE = 1;
	public final static int ABLE = 0;

	// 分页参数，每页显示默认10条，起始页默认0
	public static Integer LIMIT = 10;
	public static Integer PAGE = 0;

	// 查询排序
	public static String SORT_ASC = "asc";// 升序
	public static String SORT_DESC = "desc";// 降序

	// 博客文章状态
	public static Integer ARTICLE_TRASH = 0;//垃圾箱
	public static Integer ARTICLE_NORMAL = 1;//正常
	public static Integer ARTICLE_DRAFT = 2;//草稿箱
	
	//角色状态
	public static Integer role_uncheck = 0;//等待验证的用户
	public static Integer role_NORMAL = 1;//正常状态
	public static Integer role_lock = 2;//用户被锁定

	public static long TREE_ROOT = 0;// 根节点

	public static String SESSION_COOKIE = "JSESSIONID";// cookie中session的序列号对应Key
}
