package com.haisenberg.f1st.business.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haisenberg.f1st.business.blog.pojo.BlogTag;

public interface BlogTagDao extends JpaRepository<BlogTag, Long>{

}
