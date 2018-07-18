package com.haisenberg.f1st.sys.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Minute2Job implements Job{
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("JobName2: {}", context.getJobDetail().getKey().getName());
	}
}
