package com.haisenberg.f1st.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.pojo.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
	SysUser findByUsername(String userName);

	SysUser findByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query(value="delete from SysUser where userId in :ids ",nativeQuery=false)
	int batchDelete(@Param("ids")List<Long> ids);
}
