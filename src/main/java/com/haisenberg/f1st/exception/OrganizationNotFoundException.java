package com.haisenberg.f1st.exception;

/**
 * 关联用户和部门的时候从数据库查询不到哦啊部门对象
 */
public class OrganizationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7178047833279352178L;

	/**
	 * 错误编码
	 */
	private String errorCode;

	/**
	 * 消息是否为属性文件中的Key
	 */
	private boolean propertiesKey = true;

	/**
	 * 构造一个基本异常.
	 *
	 * @param message
	 *            信息描述
	 */
	public OrganizationNotFoundException(String message) {
		super(message);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public OrganizationNotFoundException(String errorCode, String message) {
		this(errorCode, message, true);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public OrganizationNotFoundException(String errorCode, String message, Throwable cause) {
		this(errorCode, message, cause, true);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 * @param propertiesKey
	 *            消息是否为属性文件中的Key
	 */
	public OrganizationNotFoundException(String errorCode, String message, boolean propertiesKey) {
		super(message);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public OrganizationNotFoundException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
		super(message, cause);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
	}

	/**
	 * 构造一个基本异常.
	 *
	 * @param message
	 *            信息描述
	 * @param cause
	 *            根异常类（可以存入任何异常）
	 */
	public OrganizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isPropertiesKey() {
		return propertiesKey;
	}

	public void setPropertiesKey(boolean propertiesKey) {
		this.propertiesKey = propertiesKey;
	}
}
