CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password` (
  `idPassword` int NOT NULL AUTO_INCREMENT,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPassword`),
  UNIQUE KEY `idVoter_UNIQUE` (`idPassword`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password`
--

LOCK TABLES `password` WRITE;
/*!40000 ALTER TABLE `password` DISABLE KEYS */;
INSERT INTO `password` VALUES (1,'1111'),(2,'2222'),(3,'3333'),(4,'4444'),(5,'5555');
/*!40000 ALTER TABLE `password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voteitems`
--

DROP TABLE IF EXISTS `voteitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voteitems` (
  `idvote` int NOT NULL AUTO_INCREMENT,
  `voteName` varchar(45) DEFAULT NULL,
  `voteCount` int DEFAULT NULL,
  `voteStatus` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`idvote`),
  UNIQUE KEY `idvote_UNIQUE` (`idvote`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voteitems`
--

LOCK TABLES `voteitems` WRITE;
/*!40000 ALTER TABLE `voteitems` DISABLE KEYS */;
INSERT INTO `voteitems` VALUES (1,'Computer',3,1),(2,'Mouse',3,1),(3,'KeyBoard',2,1),(4,'pp',0,0);
/*!40000 ALTER TABLE `voteitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voterecord`
--

DROP TABLE IF EXISTS `voterecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voterecord` (
  `recordid` int NOT NULL AUTO_INCREMENT,
  `idVote` int DEFAULT NULL,
  `idVoter` int DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`recordid`),
  UNIQUE KEY `record_UNIQUE` (`recordid`),
  KEY `idVote_idx` (`idVote`),
  KEY `idVoter_idx` (`idVoter`),
  CONSTRAINT `idVote` FOREIGN KEY (`idVote`) REFERENCES `voteitems` (`idvote`),
  CONSTRAINT `idVoter` FOREIGN KEY (`idVoter`) REFERENCES `voterlist` (`idVoter`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voterecord`
--

LOCK TABLES `voterecord` WRITE;
/*!40000 ALTER TABLE `voterecord` DISABLE KEYS */;
INSERT INTO `voterecord` VALUES (1,1,2,1),(2,1,3,1),(3,2,3,1),(4,2,4,1),(5,2,5,1);
/*!40000 ALTER TABLE `voterecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voterlist`
--

DROP TABLE IF EXISTS `voterlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voterlist` (
  `idVoter` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `LoginStatus` int NOT NULL DEFAULT '0',
  `permission` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`idVoter`),
  UNIQUE KEY `idVoter_UNIQUE` (`idVoter`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voterlist`
--

LOCK TABLES `voterlist` WRITE;
/*!40000 ALTER TABLE `voterlist` DISABLE KEYS */;
INSERT INTO `voterlist` VALUES (1,'Admin',0,1),(2,'Leo',0,0),(3,'Sandy',0,0),(4,'Randy',0,0),(5,'RSY',0,0);
/*!40000 ALTER TABLE `voterlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-02  1:18:16
