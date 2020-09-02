package com.iphayao.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

//    @Bean
//    public Job transactionJob(Step step1, Step step2) {
//        return jobBuilderFactory.get("transactionJob")
//                .incrementer(new RunIdIncrementer())
//                .start(step1)
//                .next(step2)
//                .build();
//    }
    
    @Bean("transactionJob1")
    public Job transactionJob1( @Qualifier("step1")Step step1 ) {
        return jobBuilderFactory.get("transactionJob1")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
    @Bean("transactionJob2")
    public Job transactionJob2( @Qualifier("step2")Step step2 ) {
        return jobBuilderFactory.get("transactionJob2")
                .incrementer(new RunIdIncrementer())
                .start(step2)
                .build();
    }
}
