package com.app.stock.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class PortFolioLinkJobListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution arg0) {
		System.out.println(" after Job");

	}

	@Override
	public void beforeJob(JobExecution arg0) {
		System.out.println(" before Job");

	}

}
