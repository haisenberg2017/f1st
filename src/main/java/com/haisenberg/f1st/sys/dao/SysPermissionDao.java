package com.haisenberg.f1st.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.pojo.SysPermission;

public interface SysPermissionDao extends JpaRepository<SysPermission, Long>,JpaSpecificationExecutor<SysPermission>{

	SysPermission findByPermissionId(Long permissionId);

	SysPermission findByPermissionName(String permissionName);

	
	@Modifying
	@Transactional
	@Query(value = "delete from SysPermission where permissionId in :ids ", nativeQuery = false)
	int batchDelete(List<Long> ids);
	
	
	@Query(value = "select * from sys_permission parent_id=?1 order by seq asc ", nativeQuery = true)	
    public List<SysPermission> findByParentIdOrderBySeq(Long pid);
	
}
