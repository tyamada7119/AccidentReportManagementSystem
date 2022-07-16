-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: accident_report
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `deletetable`
--

DROP TABLE IF EXISTS `deletetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deletetable` (
  `deleteYN` enum('Y','N') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'N',
  KEY `KEY` (`deleteYN`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deletetable`
--

LOCK TABLES `deletetable` WRITE;
/*!40000 ALTER TABLE `deletetable` DISABLE KEYS */;
INSERT INTO `deletetable` VALUES ('Y'),('N');
/*!40000 ALTER TABLE `deletetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `family-name` char(50) DEFAULT NULL,
  `first-name` char(50) DEFAULT NULL,
  `age` tinyint DEFAULT NULL,
  `gender` char(50) DEFAULT NULL,
  `position` char(50) DEFAULT NULL,
  `year` smallint DEFAULT NULL,
  `month` tinyint DEFAULT NULL,
  `date` tinyint DEFAULT NULL,
  `day-of-the-week` char(50) DEFAULT NULL,
  `time` tinyint DEFAULT NULL,
  `situationID` int DEFAULT NULL,
  `typeID` int DEFAULT NULL,
  `abstract` text,
  `deleteYN` enum('Y','N') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'N',
  PRIMARY KEY (`ID`),
  KEY `KEY situationID` (`situationID`),
  KEY `KEY typeID` (`typeID`),
  KEY `KEY deleteYN` (`deleteYN`),
  CONSTRAINT `FK deleteYN` FOREIGN KEY (`deleteYN`) REFERENCES `deletetable` (`deleteYN`),
  CONSTRAINT `FK situationID` FOREIGN KEY (`situationID`) REFERENCES `situation` (`situationID`),
  CONSTRAINT `FK typeID` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situation`
--

DROP TABLE IF EXISTS `situation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `situation` (
  `situationID` int DEFAULT NULL,
  `situation` char(50) DEFAULT NULL,
  KEY `KEY` (`situationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situation`
--

LOCK TABLES `situation` WRITE;
/*!40000 ALTER TABLE `situation` DISABLE KEYS */;
INSERT INTO `situation` VALUES (1,'実験実習中'),(2,'日常生活中'),(3,'通勤通学中'),(4,'サークル活動中');
/*!40000 ALTER TABLE `situation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `typeID` int DEFAULT NULL,
  `type` char(50) DEFAULT NULL,
  KEY `KEY` (`typeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'はさまれ、巻き込まれ'),(2,'切れ、こすれ'),(3,'墜落、転落'),(4,'交通事故'),(5,'飛来、落下'),(6,'激突'),(7,'転倒'),(8,'高温、低温物との接触'),(9,'有害物との接触');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-28 19:05:27
