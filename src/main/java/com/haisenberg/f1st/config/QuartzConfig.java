package com.haisenberg.f1st.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
	@Value("${spring.datasource.driverClassName}")
	private String DATASOURCE_DRIVER;
	@Value("${spring.datasource.url}")
	private String DATASOURCE_URL;
	@Value("${spring.datasource.username}")
	private String DATASOURC_USERNAME;
	@Value("${spring.datasource.password}")
	private String DATASOURC_PASSWORD;

	
	@Bean
	public Scheduler scheduler() throws IOException, SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}
	
	/**
	 * 设置quartz属性
	 * @throws IOException
	 * 2016年10月8日下午2:39:05
	 */
	public Properties quartzProperties() throws IOException {
		Properties prop = new Properties();
		prop.put("quartz.scheduler.instanceName", "ServerScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
		prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
		prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");

		prop.put("org.quartz.dataSource.quartzDataSource.driver", DATASOURCE_DRIVER);
		prop.put("org.quartz.dataSource.quartzDataSource.URL", DATASOURCE_URL);
		prop.put("org.quartz.dataSource.quartzDataSource.user", DATASOURC_USERNAME);
		prop.put("org.quartz.dataSource.quartzDataSource.password", DATASOURC_PASSWORD);
		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
		return prop;
	}
}
