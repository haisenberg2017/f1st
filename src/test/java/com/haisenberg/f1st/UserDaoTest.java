package com.haisenberg.f1st;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haisenberg.f1st.sys.dao.SysUserDao;
import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.pojo.SysUser;

/**
 * @ClassName: UserDaoTest.java
 * @Package: com.self
 * @Description:
 * @author 张亚兵
 * @date 2018年3月31日 下午4:15:27
 * @Version:
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest {
	@Autowired
	private SysUserDao userDao;

	@Test
	public void test() throws Exception {
		List<SysUser> findAll = userDao.findAll();
		for (SysUser sysUser : findAll) {
			System.out.println(sysUser.toString());
			List<SysRole> roleList = sysUser.getRoleList();
			for (SysRole sysRole : roleList) {
				System.out.println(sysRole.toString());
			}
			
		}
		
	}
}
