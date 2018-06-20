package com.haisenberg.f1st.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.pojo.SysPermission;

public interface SysPermissionDao extends JpaRepository<SysPermission, Long>, JpaSpecificationExecutor<SysPermission> {

	SysPermission findByPermissionId(Long permissionId);

	SysPermission findByPermissionName(String permissionName);

	@Modifying
	@Transactional
	@Query(value = "delete from SysPermission where permissionId in :ids ", nativeQuery = false)
	int batchDelete(@Param("ids")List<Long> ids);

	@Query(value = "select * from sys_permission parent_id=?1 order by seq asc ", nativeQuery = true)
	public List<SysPermission> findByParentIdOrderBySeq(Long pid);

	@Query(value = "select sys_permission.permission_id as id from sys_role_permission join sys_role on sys_role.role_id=sys_role_permission.role_id join sys_permission on sys_permission.permission_id=sys_role_permission.permission_id where sys_role_permission.role_id=?1 ", nativeQuery = true)
	public List<Long> findIdByRoleId(Long roleId);

	@Query(value = "select P from SysPermission P where P.permissionId in :ids ", nativeQuery = false)
	public List<SysPermission> findByPermissionIds(@Param("ids")List<Long> ids);

}
