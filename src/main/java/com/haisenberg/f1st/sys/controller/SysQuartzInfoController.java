package com.haisenberg.f1st.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haisenberg.f1st.sys.pojo.SysQuartzInfo;
import com.haisenberg.f1st.sys.service.SysQuartzInfoService;
import com.haisenberg.f1st.utils.Constants;

/**
 * 任务管理
 * 
 * @author lance
 */
@RestController
@RequestMapping(value = "/quartz")
public class SysQuartzInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysQuartzInfoService sysQuartzInfoService;

	/**
	 * 任务列表
	 * 
	 * @return 2016年10月9日上午11:36:03
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> list() {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		List<SysQuartzInfo> infos = new ArrayList<>();
		try {
			infos = sysQuartzInfoService.list();
		} catch (Exception e) {
			logger.error("获取定时任务列表异常", e);
			resultMap.put("msg", "服务器错误");
		}
		resultMap.put("data", infos);
		resultMap.put("total", infos.size());
		return resultMap;
	}

	/**
	 * 保存定时任务
	 * 
	 * @param info
	 *            2016年10月9日下午1:36:59
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Map<String, Object> save(@RequestBody Map<String, Object> webData) {

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		SysQuartzInfo info = new SysQuartzInfo();
		if (webData.get("jobName") == null || webData.get("jobName").toString().length() < 1) {
			logger.error("jobName为空");
			return resultMap;
		} else {
			info.setJobName(webData.get("jobName").toString());
		}
		if (webData.get("jobGroup") == null || webData.get("jobGroup").toString().length() < 1) {
			logger.error("jobGroup为空");
			return resultMap;
		} else {
			info.setJobGroup(webData.get("jobGroup").toString());
		}
		if (webData.get("cronExpression") == null || webData.get("cronExpression").toString().length() < 1) {
			logger.error("cronExpression为空");
			return resultMap;
		} else {
			info.setCronExpression(webData.get("cronExpression").toString());
		}
		if (webData.get("jobDescription") != null) {
			info.setJobDescription(webData.get("jobDescription").toString());
		}
		if (webData.get("id") != null) {
			info.setId(webData.get("id").toString());
		}

		if (info.getId() == null || info.getId().length() < 1) {
			resultMap = sysQuartzInfoService.addJob(info);
		} else {
			resultMap = sysQuartzInfoService.edit(info);
		}
		return resultMap;
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月9日下午1:52:20
	 */
	@RequestMapping(value = "/delete", produces = "application/json; charset=UTF-8")
	public Map<String, Object> delete(@RequestBody Map<String, Object> webData) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if (webData.get("jobName") == null || webData.get("jobName").toString().length() < 1) {
			logger.error("jobName为空");
			return resultMap;
		}
		if (webData.get("jobGroup") == null || webData.get("jobGroup").toString().length() < 1) {
			logger.error("jobGroup为空");
			return resultMap;
		}
		try {
			resultMap = sysQuartzInfoService.delete(webData.get("jobName").toString(),webData.get("jobGroup").toString());
		} catch (Exception e) {
			logger.error("删除定时任务列表异常", e);
			resultMap.put("msg", "服务器错误");
		}
		return resultMap;
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:41:25
	 */
	@RequestMapping(value = "/pause", produces = "application/json; charset=UTF-8")
	public Map<String, Object> pause(@RequestBody Map<String, Object> webData) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if (webData.get("jobName") == null || webData.get("jobName").toString().length() < 1) {
			logger.error("jobName为空");
			return resultMap;
		}
		if (webData.get("jobGroup") == null || webData.get("jobGroup").toString().length() < 1) {
			logger.error("jobGroup为空");
			return resultMap;
		}
		try {
			resultMap = sysQuartzInfoService.pause(webData.get("jobName").toString(),webData.get("jobGroup").toString());
		} catch (Exception e) {
			logger.error("暂停定时任务列表异常", e);
			resultMap.put("msg", "服务器错误");
		}
		return resultMap;
	}

	/**
	 * 重新开始定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:41:40
	 */
	@RequestMapping(value = "/resume", produces = "application/json; charset=UTF-8")
	public Map<String, Object> resume(@RequestBody Map<String, Object> webData) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if (webData.get("jobName") == null || webData.get("jobName").toString().length() < 1) {
			logger.error("jobName为空");
			return resultMap;
		}
		if (webData.get("jobGroup") == null || webData.get("jobGroup").toString().length() < 1) {
			logger.error("jobGroup为空");
			return resultMap;
		}
		try {
			resultMap = sysQuartzInfoService.resume(webData.get("jobName").toString(),webData.get("jobGroup").toString());
		} catch (Exception e) {
			logger.error("重新开始定时任务列表异常", e);
			resultMap.put("msg", "服务器错误");
		}
		return resultMap;
	}
}
