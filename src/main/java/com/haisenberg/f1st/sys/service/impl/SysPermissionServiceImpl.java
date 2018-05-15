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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.haisenberg.f1st.sys.dao.SysPermissionDao;
import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.service.SysPermissionService;
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
public class SysPermissionServiceImpl implements SysPermissionService {
	@Autowired
	private SysPermissionDao sysPermissionDao;

	@Override
	public SysPermission findByPermissionName(String permissionName) {
		return sysPermissionDao.findByPermissionName(permissionName);
	}

	@Override
	public void save(SysPermission sysPermission) {
		sysPermissionDao.save(sysPermission);
	}

	@Override
	public void delete(Long userId) {
		sysPermissionDao.delete(userId);
	}
	@Override
	public int batchDelete(List<Long> ids) {
		return sysPermissionDao.batchDelete(ids);
	}
	@Override
	public Page<SysPermission> findAllByPage(Map<String, Object> webData) {
		Integer page =(Integer)webData.get("page");
		Integer limit =(Integer)webData.get("limit");
		String sort=webData.get("sort")==null?"createTime":(String)webData.get("sort");
		String sortOrder=webData.get("sortOrder")==null?Constants.SORT_DESC:(String)webData.get("sortOrder");
		 Page<SysPermission> pageList=sysPermissionDao.findAll(new Specification<SysPermission>() {
			
			@Override
			public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				  List<Predicate> list = new ArrayList<>();
				  if(webData.get("permissionName")!=null){
					  list.add(cb.like(root.get("permissionName").as(String.class), "%" + webData.get("permissionName").toString() + "%"));
				  }		
				  Predicate[] predicates = new Predicate[list.size()];  
	              predicates = list.toArray(predicates);  
	              return cb.and(predicates);
			}
		},PageUtils.initPageable(page, limit, sortOrder, sort));
		return pageList;
	}

	@Override
	public SysPermission findByPermissionId(Long permissionId) {
		
		return sysPermissionDao.findByPermissionId(permissionId);
	}

}
