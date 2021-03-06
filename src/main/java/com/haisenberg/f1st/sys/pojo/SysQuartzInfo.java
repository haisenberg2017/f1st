package com.haisenberg.f1st.sys.pojo;

import java.io.Serializable;

/**
 * 管理定时任务
 * @author Administrator
 */
public class SysQuartzInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;

	/**任务名称*/
	private String jobName;
	
	/**任务分组*/
	private String jobGroup;
	
	/**任务描述*/
	private String jobDescription;
	
	/**任务状态*/
	private String jobStatus;
	
	/**任务表达式*/
	private String cronExpression;
	
	private String createTime;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}