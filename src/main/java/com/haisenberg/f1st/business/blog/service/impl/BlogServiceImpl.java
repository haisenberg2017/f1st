package com.haisenberg.f1st.business.blog.service.impl;

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

import com.haisenberg.f1st.business.blog.dao.BlogArticleDao;
import com.haisenberg.f1st.business.blog.pojo.BlogArticle;
import com.haisenberg.f1st.business.blog.service.BlogService;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.utils.Constants;
import com.haisenberg.f1st.utils.PageUtils;
@Service
public class BlogServiceImpl implements BlogService{
	//private final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
	@Autowired
	private BlogArticleDao blogArticleDao;

	@Override
	public Page<BlogArticle> findAllByPage(Map<String, Object> webData, SysUser loginUser) throws Exception {
		/**
		 *type=0, 垃圾箱
		 *type=1,正常
		 *type=2,草稿
		 * 
		 */
		Integer type = webData.get("type")==null?1:Integer.valueOf(webData.get("type").toString());
		if(loginUser==null){
			throw new Exception("获取用户登录信息失败");
		}
		Integer page = (Integer) webData.get("page");
		Integer limit = (Integer) webData.get("limit");
		String sort = webData.get("sort") == null ? "createTime" : (String) webData.get("sort");
		String sortOrder = webData.get("sortOrder") == null ? Constants.SORT_DESC : (String) webData.get("sortOrder");
		Page<BlogArticle> pageList = blogArticleDao.findAll(new Specification<BlogArticle>() {
			@Override
			public Predicate toPredicate(Root<BlogArticle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (!loginUser.isAdmin()) {//不是超级管理员
					list.add(cb.equal(root.get("userId").as(Long.class),loginUser.getUserId()));
				}
				if (webData.get("title")!=null){
					list.add(cb.like(root.get("title").as(String.class),
							"%" + webData.get("title").toString() + "%"));		
				}
				if (webData.get("categoryId")!=null){
					list.add(cb.equal(root.get("categoryId").as(Long.class),Long.valueOf(webData.get("categoryId").toString())));		
				}
				if (webData.get("tagId")!=null){
					list.add(cb.equal(root.get("tagId").as(Long.class),Long.valueOf(webData.get("tagId").toString())));		
				}
				if (type!= null) {
					list.add(cb.equal(root.get("articleState").as(Integer.class),type));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, PageUtils.initPageable(page, limit, sortOrder, sort));
		return pageList;
	}

}
