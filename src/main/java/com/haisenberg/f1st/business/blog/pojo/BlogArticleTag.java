package com.haisenberg.f1st.business.blog.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: BlogArticleTag.java
 * @Package: com.haisenberg.f1st.business.pojo
 * @Description:文章标签实体
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "blog_article_tag")
public class BlogArticleTag implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long articleId;
	private Long tagId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
}
