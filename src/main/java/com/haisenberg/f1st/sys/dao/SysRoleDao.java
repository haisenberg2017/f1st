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
	int batchDelete(@Param("ids")List<Long> ids);

}
