package com.iphayao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) {
		System.setProperty("input", "file://" + new File("/Users/Phayao/Data/transaction.csv").getAbsolutePath());
		System.setProperty("output", "file://" + new File("/Users/Phayao/Data/summary.xml").getAbsolutePath());
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
