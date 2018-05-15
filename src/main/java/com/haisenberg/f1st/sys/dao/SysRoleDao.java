package com.haisenberg.f1st.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haisenberg.f1st.sys.pojo.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Long>,JpaSpecificationExecutor<SysRole>{

	SysRole findByRoleId(Long roleId);

	SysRole findByRoleName(String roleName);

}
