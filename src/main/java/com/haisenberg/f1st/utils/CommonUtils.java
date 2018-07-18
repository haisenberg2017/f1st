package com.haisenberg.f1st.utils;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.pojo.SysUser;

public class CommonUtils {
	private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * shiro获取用户登录信息
	 * 
	 */
	public static SysUser getLoginUser() {
		Subject currentUser = SecurityUtils.getSubject();
		SysUser sysUser = null;
		try {
			String userJson = (String) currentUser.getPrincipal();
			sysUser = JSONUtils.json2pojo(userJson, SysUser.class);
		} catch (Exception e) {
			logger.error("获取用户登录信息失败", e);
		}
		return sysUser;
	}

	/**
	 * 判断用户是否是超级管理员
	 * 
	 */
	public static boolean isAdmin(SysUser sysUser) {

		List<SysRole> roleList = sysUser.getRoleList();
		for (SysRole sysRole : roleList) {
			if (Constants.YES == sysRole.getIsAdmin()) {
				return true;
			}
		}
		return false;
	}

	public static String UUIDGenerator() {
		String idStr = UUID.randomUUID().toString().replace("-", "");
		return idStr.toUpperCase();

	}

}
