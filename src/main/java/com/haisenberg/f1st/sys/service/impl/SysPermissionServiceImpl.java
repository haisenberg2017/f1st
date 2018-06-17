package com.haisenberg.f1st.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.haisenberg.f1st.sys.dao.SysPermissionDao;
import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.service.SysPermissionService;
import com.haisenberg.f1st.sys.vo.PermissionTreeVo;
import com.haisenberg.f1st.sys.vo.SelectTreeVo;
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
	@PersistenceContext
	private EntityManager em;

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
		Integer page = (Integer) webData.get("page");
		Integer limit = (Integer) webData.get("limit");
		String sort = webData.get("sort") == null ? "createTime" : (String) webData.get("sort");
		String sortOrder = webData.get("sortOrder") == null ? Constants.SORT_DESC : (String) webData.get("sortOrder");
		Page<SysPermission> pageList = sysPermissionDao.findAll(new Specification<SysPermission>() {

			@Override
			public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (webData.get("permissionName") != null) {
					list.add(cb.like(root.get("permissionName").as(String.class),
							"%" + webData.get("permissionName").toString() + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, PageUtils.initPageable(page, limit, sortOrder, sort));
		return pageList;
	}

	@Override
	public SysPermission findByPermissionId(Long permissionId) {

		return sysPermissionDao.findByPermissionId(permissionId);
	}

	@Override
	public String permissionTreeTable() {
		JSONArray list = new JSONArray();
		list = treeJson(Constants.TREE_ROOT, list, null, 1);
		return JSON.toJSONString(list);
	}

	public JSONArray treeJson(Long pid, JSONArray plist, String username, int level) {
		List<SysPermission> parents = findByParentIdOrderBySeq(pid, null, username);
		for (SysPermission parent : parents) {
			PermissionTreeVo vo = new PermissionTreeVo();
			vo.setPid(pid);
			vo.setPermission(parent.getPermission());
			vo.setId(parent.getPermissionId());
			vo.setName(parent.getPermissionName());
			vo.setPermissionType(parent.getPermissionType());
			vo.setSeq(parent.getSeq());
			vo.setUrl(parent.getUrl());
			vo.setPermissionPic(parent.getPermissionPic());
			vo.setLevel(level);
			JSONArray clist = new JSONArray();
			clist = treeJson(parent.getPermissionId(), clist, username, level++);
			if (clist != null && clist.size() > 0) {
				vo.setChildren(clist);
				vo.setChildSize(clist.size());
			} else {
				vo.setChildSize(0);
			}
			plist.add(JSON.toJSON(vo));
		}
		return plist;
	}

	@SuppressWarnings("unchecked")
	private List<SysPermission> findByParentIdOrderBySeq(Long pid, String permissionType, String username) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	sys_permission.*  ");
		sb.append(" FROM ");
		if (username != null && username.length() > 0) {
			sb.append(" 	sys_user ");
			sb.append(" 	LEFT JOIN sys_user_role ON sys_user.user_id = sys_user_role.user_id ");
			sb.append(" 	LEFT JOIN sys_role ON sys_user_role.role_id = sys_role.role_id ");
			sb.append(" 	LEFT JOIN sys_role_permission ON sys_role.role_id = sys_role_permission.role_id ");
			sb.append(
					" 	LEFT JOIN sys_permission ON sys_role_permission.permission_id = sys_permission.permission_id  ");
		} else {
			sb.append(" 	sys_permission ");
		}
		sb.append(" WHERE ");
		sb.append(" 	sys_permission.parent_id = " + pid + " ");
		if (permissionType != null && permissionType.length() > 0) {
			sb.append(" 	AND sys_permission.permission_type = '" + permissionType + "'  ");
		}
		if (username != null && username.length() > 0) {
			sb.append(" 	AND sys_user.state = 0  ");
			sb.append(" 	AND sys_user.username = 'admin'  ");
			sb.append(" 	AND sys_role.state = 0  ");
		}
		sb.append(" ORDER BY sys_permission.seq ");

		Query query = em.createNativeQuery(sb.toString(), SysPermission.class);
		List<SysPermission> list = query.getResultList();
		return list;
	}

	@Override
	public String selectTree() {
		JSONArray list = new JSONArray();
		list = selectTreeJson(Constants.TREE_ROOT, list, null);
		return JSON.toJSONString(list);
	}

	public JSONArray selectTreeJson(Long pid, JSONArray plist, String username) {
		List<SysPermission> parents = findByParentIdOrderBySeq(pid, "menu", username);
		for (SysPermission parent : parents) {
			SelectTreeVo vo = new SelectTreeVo();
			vo.setId(parent.getPermissionId());
			vo.setText(parent.getPermissionName());
			JSONArray clist = new JSONArray();
			clist = selectTreeJson(parent.getPermissionId(), clist, username);
			if (clist != null && clist.size() > 0) {
				vo.setNodes(clist);
			}
			plist.add(JSON.toJSON(vo));
		}
		return plist;
	}

	@Override
	public String findMenuByUserName(String username) {
		JSONArray list = new JSONArray();
		list = treeJson(Constants.TREE_ROOT, list, username, 0);
		return JSON.toJSONString(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPermissionForZtree() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	permission_id as id,permission_name as name,parent_id as pId ");
		sb.append(" FROM ");
		sb.append(" sys_permission ");
		sb.append(" ORDER BY sys_permission.seq ");
		Query query = em.createNativeQuery(sb.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> resultList = query.getResultList();
		JSONArray jsonArray = new JSONArray();
		for (Map<String, Object> map : resultList) {
			jsonArray.add(JSON.toJSON(map));
		}
		return jsonArray;
	}

	@Override
	public List<Long> checkPermission(Map<String, Object> webData) {
		Long roleId = Long.valueOf(webData.get("roleId").toString());
		List<Long> list = sysPermissionDao.findIdByRoleId(roleId);
		return list;
	}

	@Transactional
	@Override
	public Boolean rolePermissionSave(Long roleId, List<Long> idList) {
		boolean flag=false;
		// 删除roleId在
		sysPermissionDao.deleteRolePermission(roleId);
		StringBuffer sb=new StringBuffer();
		sb.append(" INSERT into sys_role_permission (role_id,permission_id) VALUES ");
		for (int i = 0; i < idList.size(); i++) {
			if(i==0){
				sb.append("("+roleId+","+idList.get(i)+")");	
			}else{
				sb.append(",("+roleId+","+idList.get(i)+")");	
			}		
		}
		Query query = em.createNativeQuery(sb.toString());
		int update = query.executeUpdate();
		if(update>0){
			flag=true;
		}
		return flag;
	}
}
