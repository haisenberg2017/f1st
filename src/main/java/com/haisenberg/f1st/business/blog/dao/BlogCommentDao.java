package com.haisenberg.f1st.business.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haisenberg.f1st.business.blog.pojo.BlogComment;

public interface BlogCommentDao extends JpaRepository<BlogComment, Long>{

}
