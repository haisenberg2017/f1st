package com.haisenberg.f1st.business.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.haisenberg.f1st.business.blog.pojo.BlogArticle;

public interface BlogArticleDao extends JpaRepository<BlogArticle, Long>,JpaSpecificationExecutor<BlogArticle>{

}
