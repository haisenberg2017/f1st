package com.haisenberg.f1st.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haisenberg.f1st.sys.dao.SysPermissionDao;
import com.haisenberg.f1st.sys.dao.SysRoleDao;
import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.service.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysPermissionDao sysPermissionDao;

	@Override
	public SysRole findByRoleName(String roleName) {
		return sysRoleDao.findByRoleName(roleName);
	}

	@Override
	public void save(SysRole sysRole) {
		sysRoleDao.save(sysRole);
	}

	@Override
	public void delete(Long roleId) {
		sysRoleDao.delete(roleId);
	}

	@Override
	public int batchDelete(List<Long> ids) {
		return sysRoleDao.batchDelete(ids);
	}

	@Override
	public Page<SysRole> findAllByPage(Map<String, Object> webData) {
		Integer page = (Integer) webData.get("page");
		Integer limit = (Integer) webData.get("limit");
		String sort = webData.get("sort") == null ? "createTime" : (String) webData.get("sort");
		String sortOrder = webData.get("sortOrder") == null ? Constants.SORT_DESC : (String) webData.get("sortOrder");
		Page<SysRole> pageList = sysRoleDao.findAll(new Specification<SysRole>() {

			@Override
			public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (webData.get("roleName") != null) {
					list.add(cb.like(root.get("roleName").as(String.class),
							"%" + webData.get("roleName").toString() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, PageUtils.initPageable(page, limit, sortOrder, sort));
		return pageList;
	}

	@Override
	public SysRole findByRoleId(Long roleId) {
		return sysRoleDao.findByRoleId(roleId);
	}

	@Override
	public List<SysRole> findAll() {
		return sysRoleDao.findAll(new Sort(Sort.Direction.ASC, "createTime"));
	}

	@Override
	public List<Long> getRoleIdByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysRoleDao.getRoleIdByUserId(userId);
	}

	
	@Override
	@Transactional
	public Boolean rolePermissionSave(Long roleId, List<Long> idList) {
		boolean flag = false;
		// 删除roleId在
		SysRole sysRole = sysRoleDao.findByRoleId(roleId);
		sysRole.getPermissions().clear();
		// 添加新的角色权限关联表数据
		List<SysPermission> list = sysPermissionDao.findByPermissionIds(idList);
		if (list != null && list.size() > 0) {
			sysRole.setPermissions(list);
			SysRole save = sysRoleDao.save(sysRole);
			if (save != null) {
				flag = true;
			}
		}
		return flag;
	}

}
