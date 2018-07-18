package com.haisenberg.f1st.business.blog.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: BlogArticle.java
 * @Package: com.haisenberg.f1st.business.pojo
 * @Description:文章实体
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "blog_article")
public class BlogArticle implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long articleId;
	private Long userId;// 作者
	private String title;// 文章标题
	private String desc;// 文章描述
	private String content;// 文章内容
	private Long categoryId;//类别id
	private Long clickCount = 0L;// 点击次数
	private Boolean isRecommend = false;// 是否推荐给所有人
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	private Date publishTime;// 发布时间
	private Integer articleState;// 文章状态。 0.垃圾箱;1.正常;2.草稿

	public BlogArticle() {

	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	

	public Integer getArticleState() {
		return articleState;
	}

	public void setArticleState(Integer articleState) {
		this.articleState = articleState;
	}

	@Override
	public String toString() {
		return "BlogArticle [articleId=" + articleId + ", userId=" + userId + ", title=" + title + ", desc=" + desc
				+ ", content=" + content + ", categoryId=" + categoryId + ", clickCount=" + clickCount
				+ ", isRecommend=" + isRecommend + ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ ", publishTime=" + publishTime +", articleState=" + articleState + "]";
	}



}
