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
