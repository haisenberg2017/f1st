package com.haisenberg.f1st.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.haisenberg.f1st.sys.pojo.SysRole;

/**
 * @ClassName: SysUserService.java
 * @Package: com.haisenberg.f1st.sys.service
 * @Description: 
 * @author 张翔
 * @date 2018年5月9日 下午3:42:24
 * @Version: 
 */
public interface SysRoleService {

	/**
	 * @Title:
	 * @Description: 
	 * @param username
	 * @return 
	 * @Date: 2018年5月9日 下午3:50:51
	 * @Author: 张翔
	 */
	SysRole findByRoleName(String roleName);

	void save(SysRole sysRole);

	void delete(Long RoleId);

	Page<SysRole> findAllByPage(Map<String, Object> webData);
	
	SysRole findByRoleId(Long userId);
	
	int batchDelete(List<Long> ids);

	List<SysRole> findAll();

}
