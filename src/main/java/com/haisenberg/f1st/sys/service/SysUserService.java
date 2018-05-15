package com.haisenberg.f1st.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.haisenberg.f1st.sys.pojo.SysUser;

/**
 * @ClassName: SysUserService.java
 * @Package: com.haisenberg.f1st.sys.service
 * @Description: 
 * @author 张翔
 * @date 2018年5月9日 下午3:42:24
 * @Version: 
 */
public interface SysUserService {

	/**
	 * @Title:
	 * @Description: 
	 * @param username
	 * @return 
	 * @Date: 2018年5月9日 下午3:50:51
	 * @Author: 张翔
	 */
	SysUser findByUsername(String username);

	void save(SysUser sysUser);

	void delete(Long userId);

	Page<SysUser> findAllByPage(Map<String, Object> webData);
	
	SysUser findByUserId(Long userId);

	int batchDelete(List<Long> ids);

}
