-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: excoin
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exkepper_users`
--

DROP TABLE IF EXISTS `exkepper_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exkepper_users` (
  `user_name` varchar(100) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `api_key` varchar(45) NOT NULL,
  `auto_delegate` tinyint(4) NOT NULL DEFAULT '0',
  `buy_1_index` float NOT NULL,
  `buy_2_index` float NOT NULL,
  `buy_3_index` float NOT NULL,
  `buy_4_index` float NOT NULL,
  `sell_1_index` float NOT NULL,
  `sell_2_index` float NOT NULL,
  `sell_3_index` float NOT NULL,
  `sell_4_index` float NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `api_key_UNIQUE` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exkepper_users`
--

LOCK TABLES `exkepper_users` WRITE;
/*!40000 ALTER TABLE `exkepper_users` DISABLE KEYS */;
INSERT INTO `exkepper_users` VALUES ('yuanbo','2222','sdfasdfsdfsdf',0,0.98,0.95,0.93,0.92,1.4,1.3,1.2,1.1);
/*!40000 ALTER TABLE `exkepper_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-07 18:32:49
