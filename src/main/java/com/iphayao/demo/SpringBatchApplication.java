package com.iphayao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@SpringBootApplication
@EnableScheduling
public class SpringBatchApplication {

	public static void main(String[] args) {
		System.setProperty("input", "file:" + new File("Data/transaction.csv").getAbsolutePath());
		System.setProperty("output", "file:" + new File("Data/summary.xml").getAbsolutePath());
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
