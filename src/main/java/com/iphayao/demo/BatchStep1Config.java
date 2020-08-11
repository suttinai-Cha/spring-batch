package com.iphayao.demo;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class BatchStep1Config {
    @Value("${input}")
    private Resource inputFile;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean("step1ItemReader")
    public ItemReader<Transaction> fileReader() {
        return new FlatFileItemReaderBuilder<Transaction>()
                .resource(inputFile)
                .name("file-reader")
                .targetType(Transaction.class)
                .delimited().delimiter(",")
                .names(new String[]{"firstName", "userId", "transactionDate", "transactionAmount"})
                .linesToSkip(1)
                .build();
    }

    @Bean("step1ItemWriter")
    public ItemWriter<Transaction> dbWriter() {

        return new JdbcBatchItemWriterBuilder<Transaction>()
                .dataSource(dataSource)
                .sql("INSERT INTO TRANSACTION (userId,transactionDate,userName,transactionAmount) VALUES (:userId, :transactionDate, :userName, :transactionAmount)")
                .beanMapped()
                .build();
    }


    @Bean("step1ItemProcessor")
    public ItemProcessor<Transaction, Transaction> processor() {
        return new ItemProcessor<Transaction, Transaction>() {
            private static final double USD_THB_RATE = 32.5;

            @Override
            public Transaction process(Transaction item) {
                double bahtAmount = item.getTransactionAmount() * USD_THB_RATE;
                String userNameUpperCase = item.getUserName().toUpperCase();

                return Transaction.builder()
                        .userId(item.getUserId())
                        .userName(userNameUpperCase)
                        .transactionDate(item.getTransactionDate())
                        .transactionAmount(bahtAmount)
                        .build();
            }
        };
    }

    @Bean
    public Step step1(ItemReader<Transaction> step1ItemReader,
                      ItemProcessor<Transaction, Transaction> step1ItemProcessor,
                      ItemWriter<Transaction> step1ItemWriter) {
        return stepBuilderFactory.get("Step1 - Import Transaction Data")
                .<Transaction, Transaction>chunk(1000)
                .reader(step1ItemReader)
                .processor(step1ItemProcessor)
                .writer(step1ItemWriter)
                .build();
    }

}
