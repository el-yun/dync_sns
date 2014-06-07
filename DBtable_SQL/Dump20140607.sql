CREATE DATABASE  IF NOT EXISTS `dyncdb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `dyncdb`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: dyncdb
-- ------------------------------------------------------
-- Server version	5.6.16-enterprise-commercial-advanced

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code` (
  `CODE_ID` int(50) NOT NULL AUTO_INCREMENT,
  `CODE_REPOSITORY` int(20) DEFAULT NULL,
  `CODE_SUBJECT` varchar(255) DEFAULT NULL,
  `BASE_LANGUAGE` varchar(100) DEFAULT NULL,
  `CODE_CONTENTS` text,
  `REVISION` int(50) NOT NULL,
  `USING` tinyint(1) DEFAULT NULL,
  `REG_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CODE_ID`),
  UNIQUE KEY `CODE_ID` (`CODE_ID`),
  UNIQUE KEY `REVISION` (`REVISION`),
  KEY `CODE_REPOSITORY` (`CODE_REPOSITORY`),
  CONSTRAINT `code_ibfk_1` FOREIGN KEY (`CODE_REPOSITORY`) REFERENCES `code_repository` (`CODE_REPOSITORY`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
INSERT INTO `code` VALUES (4,1402129701,'658686','text','JTJGJTJGJUVDJUJEJTk0JUVCJTkzJTlDJUVCJUE1JUJDKyVFQiVBRiVCOCVFQiVBNiVBQyslRUMlOUUlODUlRUIlQTAlQTUlRUQlOTUlOTglRUMlODQlQjglRUMlOUElOTQlMjE4Njc4Njc4OCUwQTY4NSUwQTY4NiUwQTglMEE2NSUwQTg1NjclMEE4Njc4Njc=',0,0,'2014-06-07 17:54:25');
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code_repository`
--

DROP TABLE IF EXISTS `code_repository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code_repository` (
  `CODE_REPOSITORY` int(20) NOT NULL,
  `USER_ID` int(50) NOT NULL,
  PRIMARY KEY (`CODE_REPOSITORY`),
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `code_repository_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_repository`
--

LOCK TABLES `code_repository` WRITE;
/*!40000 ALTER TABLE `code_repository` DISABLE KEYS */;
INSERT INTO `code_repository` VALUES (1402129701,200923397);
/*!40000 ALTER TABLE `code_repository` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue` (
  `ISSUE_ID` int(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(50) DEFAULT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  `SUBJECT` text,
  `CONTENTS` text,
  `DISPLAY` tinyint(1) DEFAULT NULL,
  `RECOMMAND` int(50) DEFAULT NULL,
  `REG_DATE` datetime DEFAULT NULL,
  `UPLOAD` varchar(50) DEFAULT NULL,
  `TAG` text,
  PRIMARY KEY (`ISSUE_ID`),
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `issue_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100013 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES (100012,200923397,'ISSUE','이슈 등록 테스트 이슈 등록 테스트이슈 등록 테스트ㅍ이슈 등록 테스트 이슈 등록 테스트 이슈 등록 테스트 ..','이슈 등록 테스트<br>이슈 등록 테스트이슈 등록 테스트ㅍ이슈 등록 테스트<br>이슈 등록 테스트<br>이슈 등록 테스트<br>',1,0,'2014-06-07 17:55:10','','ABC');
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `USER_ID` int(50) NOT NULL,
  `TAG_NAME` text,
  `ISSUE_ID` int(20),
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `tag_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (200923397,'ABC',100012);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `USER_ID` int(50) NOT NULL AUTO_INCREMENT,
  `USER_NAVERHASH` text,
  `USER_KAKAOHASH` text,
  `USER_NAME` varchar(10) DEFAULT NULL,
  `USER_DESCRIPTION` text,
  `CODE_REPOSITORY` int(20) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=200923398 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (200923397,NULL,'454051','윤지환',NULL,1402129701);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `window`
--

DROP TABLE IF EXISTS `window`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `window` (
  `ISSUE_ID` int(20) NOT NULL,
  `CODE_ID` int(50) NOT NULL,
  `START_LINE` int(20) DEFAULT NULL,
  `END_LINE` int(20) DEFAULT NULL,
  `WINDOW_PATH` varchar(200) DEFAULT NULL,
  UNIQUE KEY `ISSUE_ID` (`ISSUE_ID`),
  UNIQUE KEY `CODE_ID` (`CODE_ID`),
  CONSTRAINT `window_ibfk_1` FOREIGN KEY (`ISSUE_ID`) REFERENCES `issue` (`ISSUE_ID`),
  CONSTRAINT `window_ibfk_2` FOREIGN KEY (`CODE_ID`) REFERENCES `code` (`CODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `window`
--

LOCK TABLES `window` WRITE;
/*!40000 ALTER TABLE `window` DISABLE KEYS */;
/*!40000 ALTER TABLE `window` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-07 18:01:43
