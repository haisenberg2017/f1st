package com.haisenberg.f1st.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haisenberg.f1st.sys.pojo.SysPermission;

public interface SysPermissionDao extends JpaRepository<SysPermission, Long>,JpaSpecificationExecutor<SysPermission>{

	SysPermission findByPermissionId(Long permissionId);

	SysPermission findByPermissionName(String permissionName);

}
