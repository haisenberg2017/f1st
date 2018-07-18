package com.haisenberg.f1st.business.blog.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.haisenberg.f1st.business.blog.pojo.BlogArticle;
import com.haisenberg.f1st.sys.pojo.SysUser;

public interface BlogService {

	Page<BlogArticle> findAllByPage(Map<String, Object> webData, SysUser loginUser) throws Exception;

}
