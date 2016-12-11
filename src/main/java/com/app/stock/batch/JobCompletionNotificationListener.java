package com.app.stock.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("before job");
		// TODO Auto-generated method stub
		super.beforeJob(jobExecution);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("after Job");
		super.afterJob(jobExecution);
	}
}
