package com.haisenberg.f1st;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haisenberg.f1st.sys.dao.SysUserDao;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.sys.service.SysUserService;
import com.haisenberg.f1st.utils.Constants;

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
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
	/*	List<SysUser> findAll = userDao.findAll();
		for (SysUser sysUser : findAll) {
			System.out.println(sysUser.toString());
			List<SysRole> roleList = sysUser.getRoleList();
			for (SysRole sysRole : roleList) {
				System.out.println(sysRole.toString());
			}

		}*/
	/*	redisTemplate.opsForValue().set("aaa", "111");
	        Assert.assertEquals("111", redisTemplate.opsForValue().get("aaa"));*/
		SysUser sysUser=new SysUser();
				sysUser.setCreateTime(new Date());
		sysUser.setUsername("admin");
		sysUser.setModifyTime(new Date());
		sysUser.setRealName("海森堡格");
		sysUser.setPassword("admin");
		sysUser.setState(Constants.ABLE);
		sysUserService.save(sysUser);
		
	}
}
