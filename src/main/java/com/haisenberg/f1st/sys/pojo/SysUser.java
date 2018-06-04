package com.haisenberg.f1st.sys.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @ClassName: User.java
 * @Package: com.self.model
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 上午9:47:31
 * @Version:
 */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 5096416055767275633L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String username;
	private String password;
	private String realName;
	private String salt;// 加密密码的盐
	private Integer state;// 用户状态,0:可用 1：不可用
	private Date createTime;
	private Date modifyTime;
	@ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据;
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<SysRole> roleList;// 一个用户具有多个角色

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
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

	@Override
	public String toString() {
		return "SysUser [userId=" + userId + ", username=" + username + ", password=" + password + ", realName="
				+ realName + ", salt=" + salt + ", state=" + state + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + ", roleList=" + roleList + "]";
	}

	/**
	 * @Title:
	 * @Description:
	 * @return
	 * @Date: 2018年5月9日 下午3:40:04
	 * @Author: 张翔
	 */
	public String getCredentialsSalt() {
		return username + salt;
	}

}
