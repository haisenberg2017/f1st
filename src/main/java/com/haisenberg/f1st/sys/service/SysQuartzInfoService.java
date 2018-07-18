package com.haisenberg.f1st.sys.service;

import java.util.List;
import java.util.Map;

import com.haisenberg.f1st.sys.pojo.SysQuartzInfo;

public interface SysQuartzInfoService {
	/**
	 * 所有任务列表
	 * 2016年10月9日上午11:16:59
	 * @throws Exception 
	 */
	public List<SysQuartzInfo> list() throws Exception;
	/**
	 * 保存定时任务
	 * @param info
	 * 2016年10月9日上午11:30:40
	 */
	public Map<String, Object> addJob(SysQuartzInfo info);
	/**
	 * 修改定时任务
	 * @param info
	 * 2016年10月9日下午2:20:07
	 */
	public Map<String, Object> edit(SysQuartzInfo info);
	/**
	 * 删除定时任务
	 * @param jobName
	 * @param jobGroup
	 * 2016年10月9日下午1:51:12
	 */
	public Map<String, Object> delete(String jobName, String jobGroup) throws Exception;
	/**
	 * 暂停定时任务
	 * @param jobName
	 * @param jobGroup
	 * 2016年10月10日上午9:40:19
	 */
	public  Map<String, Object>  pause(String jobName, String jobGroup)  throws Exception;
	/**
	 * 重新开始任务
	 * @param jobName
	 * @param jobGroup
	 * 2016年10月10日上午9:40:58
	 */
	public Map<String, Object> resume(String jobName, String jobGroup)throws Exception;
}
