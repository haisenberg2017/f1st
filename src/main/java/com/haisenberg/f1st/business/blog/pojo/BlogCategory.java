package com.haisenberg.f1st.business.blog.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: BlogCategory.java
 * @Package: com.haisenberg.f1st.business.pojo
 * @Description:类别实体
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "blog_category")
public class BlogCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long categoryId;// 类别主键
	private Long pid;// 类别父级id
	private Long userId;// 作者
	private String categoryName;// 类别名称
	private Date createTime;// 创建时间
	private Date midifyTime;// 修改时间

	public BlogCategory() {
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
		return "BlogCategory [categoryId=" + categoryId + ", pid=" + pid + ", userId=" + userId + ", categoryName="
				+ categoryName + ", createTime=" + createTime + ", midifyTime=" + midifyTime + "]";
	}

}
