package com.haisenberg.f1st.business.blog.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: BlogComment.java
 * @Package: com.haisenberg.f1st.business.pojo
 * @Description:评论实体
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "blog_comment")
public class BlogComment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;// 评论主键
	private Long pid;// 评论父级id
	private Long commentUserId;// 评论作者
	private String content;// 评论内容
	private Long articleId;// 评论文章id
	private Long userId;// 被评论作者
	private Date createTime;// 创建时间
	private Date midifyTime;// 修改时间

	public BlogComment() {
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(Long commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getMidifyTime() {
		return midifyTime;
	}

	public void setMidifyTime(Date midifyTime) {
		this.midifyTime = midifyTime;
	}

	@Override
	public String toString() {
		return "BlogComment [commentId=" + commentId + ", pid=" + pid + ", commentUserId=" + commentUserId
				+ ", content=" + content + ", articleId=" + articleId + ", userId=" + userId + ", createTime="
				+ createTime + ", midifyTime=" + midifyTime + "]";
	}

}
