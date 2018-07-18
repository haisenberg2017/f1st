package com.haisenberg.f1st.business.blog.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: BlogTag.java
 * @Package: com.haisenberg.f1st.business.pojo
 * @Description:标签实体
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "blog_tag")
public class BlogTag implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tagId;// 标签主键
	private Long pid;// 标签父级id
	private Long userId;// 作者
	private String tagName;// 标签名称
	private Date createTime;// 创建时间
	private Date midifyTime;// 修改时间

	public BlogTag() {
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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
		return "BlogTag [tagId=" + tagId + ", pid=" + pid + ", userId=" + userId + ", tagName=" + tagName
				+ ", createTime=" + createTime + ", midifyTime=" + midifyTime + "]";
	}

}
