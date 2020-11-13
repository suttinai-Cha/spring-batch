# spring-batch demo project
## Create database schema *"batch"*
## Create table *"TRANSACTION"*
``
CREATE TABLE `TRANSACTION` (
  `userId` int DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `transactionDate` datetime DEFAULT NULL,
  `transactionAmount` decimal(10,0) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
``
## Run by specify job in eclipse 
 Run --> Run configuration --> VM arguments: -Dspring.batch.job.names=JobName (see jobname in BatchJobConfig.java in annotation *@Bean("jobname")*)
 
 # execute job via command line
### Run all jobs

``` 
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar
```

### Run only specified jobs


#### Only job1

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job1
```

#### Only job2

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job2
```

#### job1 and job2

```
$ java -jar target/spring-boot-batch-multi-jobs-0.0.1.RELEASE.jar --spring.batch.job.names=job1,job2
```
