package com.iphayao.demo;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MarkdownSolrBatchScheduler {
    private JobLauncher jobLauncher;
    private Job indexMarkdownDocumentsJob;
    
    @Scheduled(cron = "0 * * * * *")
    public void schedule() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
    	 jobLauncher.run(indexMarkdownDocumentsJob, new JobParametersBuilder()
    		        .addDate("date", new Date())
    		        .toJobParameters());
    }

}