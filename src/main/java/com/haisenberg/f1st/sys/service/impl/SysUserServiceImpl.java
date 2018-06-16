package com.haisenberg.f1st.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.haisenberg.f1st.sys.dao.SysUserDao;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.sys.service.SysUserService;
import com.haisenberg.f1st.utils.Constants;
import com.haisenberg.f1st.utils.PageUtils;

/**
 * @ClassName: SysUserServiceImpl.java
 * @Package: com.haisenberg.f1st.sys.service.impl
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午3:42:37
 * @Version:
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public SysUser findByUsernameAndState(String username,int state) {
		return sysUserDao.findByUsernameAndState(username,state);
	}

	@Override
	public void save(SysUser sysUser) {
		// 随机数生成盐
		SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		// 组合username,两次迭代，对密码进行加密
		String password_cipherText = new Md5Hash(sysUser.getPassword(), sysUser.getUsername() + salt, 2).toHex();
		sysUser.setPassword(password_cipherText);
		sysUser.setSalt(salt);
		sysUserDao.save(sysUser);
	}

	@Override
	public void delete(Long userId) {
		sysUserDao.delete(userId);
	}

	@Override
	public int batchDelete(List<Long> ids) {
		return sysUserDao.batchDelete(ids);
	}

	@Override
	public Page<SysUser> findAllByPage(Map<String, Object> webData) {
		Integer page = (Integer) webData.get("page");
		Integer limit = (Integer) webData.get("limit");
		String sort = webData.get("sort") == null ? "createTime" : (String) webData.get("sort");
		String sortOrder = webData.get("sortOrder") == null ? Constants.SORT_DESC : (String) webData.get("sortOrder");
		Page<SysUser> pageList = sysUserDao.findAll(new Specification<SysUser>() {

			@Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (webData.get("realName") != null) {
					list.add(cb.like(root.get("realName").as(String.class),
							"%" + webData.get("realName").toString() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, PageUtils.initPageable(page, limit, sortOrder, sort));
		return pageList;
	}

	@Override
	public SysUser findByUserId(Long userId) {

		return sysUserDao.findByUserId(userId);
	}

}
