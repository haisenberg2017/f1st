package com.haisenberg.f1st.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haisenberg.f1st.sys.pojo.SysQuartzInfo;
import com.haisenberg.f1st.sys.service.SysQuartzInfoService;
import com.haisenberg.f1st.utils.CommonUtils;
import com.haisenberg.f1st.utils.Constants;

@Service
public class SysQuartzInfoServiceImpl implements SysQuartzInfoService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private Scheduler scheduler;

	/**
	 * 所有任务列表 2016年10月9日上午11:16:59
	 * 
	 * @throws SchedulerException
	 */
	public List<SysQuartzInfo> list() throws Exception {
		List<SysQuartzInfo> list = new ArrayList<>();
		for (String groupJob : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey> groupEquals(groupJob))) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					JobDetail jobDetail = scheduler.getJobDetail(jobKey);

					String cronExpression = "", createTime = "";

					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						cronExpression = cronTrigger.getCronExpression();
						createTime = cronTrigger.getDescription();
					}
					SysQuartzInfo info = new SysQuartzInfo();
					info.setId(CommonUtils.UUIDGenerator());
					info.setJobName(jobKey.getName());
					info.setJobGroup(jobKey.getGroup());
					info.setJobDescription(jobDetail.getDescription());
					info.setJobStatus(triggerState.name());
					info.setCronExpression(cronExpression);
					info.setCreateTime(createTime);
					list.add(info);
				}
			}
		}

		return list;
	}

	/**
	 * 保存定时任务
	 * 
	 * @param info
	 *            2016年10月9日上午11:30:40
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> addJob(SysQuartzInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info.getCronExpression(),
				jobDescription = info.getJobDescription(),
				createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		try {
			if (checkExists(jobName, jobGroup)) {
				logger.info("===> AddJob fail, job already exist, jobName:{}, jobGroup:{}", jobName, jobGroup);
				resultMap.put("msg", String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
				return resultMap;
			}

			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
					.withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
					.withSchedule(schedBuilder).build();

			Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobName);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException | ClassNotFoundException e) {
			logger.info("类名不存在或执行表达式错误", e);
			resultMap.put("msg", "类名不存在或执行表达式错误");
			return resultMap;
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("msg", "添加job成功");
		return resultMap;
	}

	/**
	 * 修改定时任务
	 * 
	 * @param info
	 *            2016年10月9日下午2:20:07
	 */
	public Map<String, Object> edit(SysQuartzInfo info) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info.getCronExpression(),
				jobDescription = info.getJobDescription(),
				createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		try {
			if (!checkExists(jobName, jobGroup)) {
				logger.info("===> EditJob fail, job is not exist, jobName:{}, jobGroup:{}", jobName, jobGroup);
				resultMap.put("msg", String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
				return resultMap;
			}
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			JobKey jobKey = new JobKey(jobName, jobGroup);
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
					.withMisfireHandlingInstructionDoNothing();
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
					.withSchedule(cronScheduleBuilder).build();

			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			jobDetail.getJobBuilder().withDescription(jobDescription);
			HashSet<Trigger> triggerSet = new HashSet<>();
			triggerSet.add(cronTrigger);

			scheduler.scheduleJob(jobDetail, triggerSet, true);
		} catch (SchedulerException e) {
			logger.info("类名不存在或执行表达式错误", e);
			resultMap.put("msg", "类名不存在或执行表达式错误");
			return resultMap;
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("msg", "添加job成功");
		return resultMap;
	}

	/**
	 * 删除定时任务
	 * @param jobName
	 * @param jobGroup
	 * 2016年10月9日下午1:51:12
	 * @throws Exception 
	 */
	public Map<String, Object> delete(String jobName, String jobGroup) throws Exception{
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			if (checkExists(jobName, jobGroup)) {
				scheduler.pauseTrigger(triggerKey);
			    scheduler.unscheduleJob(triggerKey);
			    logger.info("===> delete, triggerKey:{}", triggerKey);
			    resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			    resultMap.put("msg", "删除job成功");
			}else{
				resultMap.put("msg", "删除的job不存在");
			}
			return resultMap;
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:40:19
	 * @throws SchedulerException 
	 */
	public Map<String, Object>  pause(String jobName, String jobGroup) throws SchedulerException {
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			if (checkExists(jobName, jobGroup)) {
				scheduler.pauseTrigger(triggerKey);
				logger.info("===> Pause success, triggerKey:{}", triggerKey);
				 resultMap.put("flag", Constants.SUCCESS_RESPONSE);
				 resultMap.put("msg", "暂停job成功");
			}else{
				resultMap.put("msg", "暂停的job不存在");
			}
			return resultMap;
	}

	/**
	 * 重新开始任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:40:58
	 * @throws Exception 
	 */
	public Map<String, Object> resume(String jobName, String jobGroup) throws Exception {
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			if (checkExists(jobName, jobGroup)) {
				scheduler.resumeTrigger(triggerKey);
				logger.info("===> Resume success, triggerKey:{}", triggerKey);
				resultMap.put("flag", Constants.SUCCESS_RESPONSE);
				 resultMap.put("msg", "重新开始job成功");
			}else{
				resultMap.put("msg", "重新开始的job不存在");
			}
			return resultMap;
	}

	/**
	 * 验证是否存在
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @throws SchedulerException
	 *             2016年10月8日下午5:30:43
	 */
	private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return scheduler.checkExists(triggerKey);
	}
}