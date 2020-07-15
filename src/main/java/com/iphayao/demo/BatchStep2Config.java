package com.iphayao.demo;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.rmi.MarshalException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Configuration
public class BatchStep2Config {
    @Value("${output}")
    private Resource outputFile;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean("step2ItemReader")
    public ItemReader<TransactionSummary> dbReader() {
        return new JdbcCursorItemReaderBuilder<TransactionSummary>()
                .dataSource(dataSource)
                .name("db-reader")
                .sql("select count(userName) a,  sum(transactionAmount) b from TRANSACTION")
                .rowMapper((rs, rowNum) -> TransactionSummary.builder()
                        .count(rs.getInt("a"))
                        .totalAmount(rs.getInt("b"))
                        .build()
                )
                .build();
    }

    @Bean("step2ItemWriter")
    public ItemWriter<TransactionSummary> xmlWriter(Marshaller marshaller) {
        StaxEventItemWriter<TransactionSummary> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("summary");
        itemWriter.setResource(outputFile);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(TransactionSummary.class);
        return marshaller;
    }


    @Bean
    public Step step2(ItemReader<TransactionSummary> step2ItemReader,
                      ItemWriter<TransactionSummary> step2ItemWriter) {
        return stepBuilderFactory.get("Step1 - Import Transaction Data")
                .<TransactionSummary, TransactionSummary>chunk(1000)
                .reader(step2ItemReader)
                .writer(step2ItemWriter)
                .build();
    }

}
