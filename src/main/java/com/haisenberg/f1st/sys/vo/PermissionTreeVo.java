package com.haisenberg.f1st.sys.vo;

public class PermissionTreeVo {
	private Long pid;//父节点
	private Long id;// 主键.
	private String name;// 名称.
	private String permissionType;// 资源类型，[menu|button]
	private String url;// 资源路径.
	private String permissionPic;// 图标
	private String permission; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	private Long seq; // 排序
	private int level;
	private Object children;
	private int childSize;




	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Object getChildren() {
		return children;
	}

	public void setChildren(Object children) {
		this.children = children;
	}

	public String getPermissionPic() {
		return permissionPic;
	}

	public void setPermissionPic(String permissionPic) {
		this.permissionPic = permissionPic;
	}

	public int getChildSize() {
		return childSize;
	}

	public void setChildSize(int childSize) {
		this.childSize = childSize;
	}

	@Override
	public String toString() {
		return "PermissionTreeVo [pid=" + pid + ", id=" + id + ", name=" + name + ", permissionType=" + permissionType + ", url=" + url
				+ ", permissionPic=" + permissionPic + ", permission=" + permission + ", seq=" + seq + ", level="
				+ level + ", children=" + children + ", childSize=" + childSize + "]";
	}

}
