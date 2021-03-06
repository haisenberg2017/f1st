package com.haisenberg.f1st.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.pojo.SysUser;

/**
 * @ClassName: SysUserService.java
 * @Package: com.haisenberg.f1st.sys.service
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午3:42:24
 * @Version:
 */
public interface SysPermissionService {

	/**
	 * @Title:
	 * @Description:
	 * @param username
	 * @return
	 * @Date: 2018年5月9日 下午3:50:51
	 * @Author: 张翔
	 */
	SysPermission findByPermissionName(String permissionName);

	void save(SysPermission sysPermission);

	void delete(Long RoleId);

	Page<SysPermission> findAllByPage(Map<String, Object> webData);

	SysPermission findByPermissionId(Long permissionId);

	int batchDelete(List<Long> ids);

	public String permissionTreeTable();
	
	public String selectTree();	
	
	public JSONArray getPermissionForZtree();

	public List<Long> checkPermission(Map<String, Object> webData);

	public String menu(SysUser sysUser);



}
