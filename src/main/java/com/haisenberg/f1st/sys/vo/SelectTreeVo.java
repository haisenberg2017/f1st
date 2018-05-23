package com.haisenberg.f1st.sys.vo;

public class SelectTreeVo {
	private long id;
	private String text;
	private Object nodes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getNodes() {
		return nodes;
	}

	public void setNodes(Object nodes) {
		this.nodes = nodes;
	}
}
