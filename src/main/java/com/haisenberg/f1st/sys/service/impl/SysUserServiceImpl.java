package com.haisenberg.f1st.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.dao.SysRoleDao;
import com.haisenberg.f1st.sys.dao.SysUserDao;
import com.haisenberg.f1st.sys.pojo.SysRole;
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
	@Autowired
	private SysRoleDao sysRoleDao;
	@PersistenceContext
	private EntityManager em;

	@Override
	public SysUser findByUsernameAndState(String username, int state) {
		return sysUserDao.findByUsernameAndState(username, state);
	}

	@Override
	public Map<String, Object> save(SysUser sysUser) {
		Map<String, Object> resultMap = new HashMap<>();
		if (sysUser.getUserId() == null) {
			// 随机数生成盐
			SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
			String salt = secureRandomNumberGenerator.nextBytes().toHex();
			// 组合username,两次迭代，对密码进行加密
			String password_cipherText = new Md5Hash(sysUser.getPassword(), sysUser.getUsername() + salt, 2).toHex();
			sysUser.setPassword(password_cipherText);
			sysUser.setSalt(salt);
		}
		SysUser save = sysUserDao.save(sysUser);
		if (save != null) {
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			resultMap.put("msg", "用户保存成功！");
			resultMap.put("userId", save.getUserId());
		} else {
			resultMap.put("flag", Constants.ERROR_RESPONSE);
			resultMap.put("msg", "用户保存失败！");
		}
		return resultMap;
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

	@Override
	public Map<String, Object> update(SysUser sysUser) {
		Map<String, Object> resultMap = new HashMap<>();
		SysUser save = sysUserDao.save(sysUser);
		if (save != null) {
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			resultMap.put("msg", "用户保存成功！");
		} else {
			resultMap.put("flag", Constants.ERROR_RESPONSE);
			resultMap.put("msg", "用户保存失败！");
		}
		return resultMap;
	}

	@Override
	@Transactional
	public boolean userRoleSave(Long userId, String roleIds) {
		SysUser sysUser = sysUserDao.findByUserId(userId);
		if (roleIds != null && roleIds.length() > 0) {
			// 清除以前的角色
			sysUser.getRoleList().clear();
			String[] roleIdArr = roleIds.split(",");
			// 添加新的用户角色关联表数据
			List<Long> ids = new ArrayList<>();
			for (String str : roleIdArr) {
				Long roleId = Long.valueOf(str.trim());
				ids.add(roleId);
			}
			if (ids != null && ids.size() > 0) {
				List<SysRole> list = sysRoleDao.findByRoleIds(ids);
				sysUser.setRoleList(list);
				SysUser save = sysUserDao.save(sysUser);
				if (save != null) {
					return true;
				}
			}

		}
		return false;
	}

}
