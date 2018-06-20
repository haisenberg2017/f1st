package com.haisenberg.f1st.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.pojo.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Long>,JpaSpecificationExecutor<SysRole>{

	SysRole findByRoleId(Long roleId);

	SysRole findByRoleName(String roleName);

	@Modifying
	@Transactional
	@Query(value = "delete from SysRole where roleId in :ids ", nativeQuery = false)
	public int batchDelete(@Param("ids")List<Long> ids);

	@Query(value = "select sys_role.role_id as id from sys_user_role join sys_role on sys_user_role.role_id=sys_role.role_id join sys_user on sys_user.user_id=sys_user_role.user_id where sys_user.user_id=?1 ", nativeQuery = true)
	public	List<Long> getRoleIdByUserId(Long userId);
	
	@Query(value = "select R from SysRole R where R.roleId in :ids ", nativeQuery = false)
	public List<SysRole> findByRoleIds(@Param("ids")List<Long> ids);

}
