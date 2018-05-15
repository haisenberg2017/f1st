package com.haisenberg.f1st.sys.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: SysPermission.java
 * @Package: com.haisenberg.f1st.sys.pojo
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午12:30:07
 * @Version:
 */
@Entity
@Table(name = "sys_permission")
public class SysPermission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long permissionId;// 主键.
	private String permissionName;// 名称.
	@Column(columnDefinition = "enum('menu','button')")
	private String permissionType;// 资源类型，[menu|button]
	private String url;// 资源路径.
	private String permission; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	private Long parentId; // 父编号
	private Long seq; // 排序
	private Date createTime;
	private Date modifyTime;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
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

}
