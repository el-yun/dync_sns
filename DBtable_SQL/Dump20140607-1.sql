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
  `REVISION` int(50) DEFAULT NULL,
  `USING` tinyint(1) DEFAULT NULL,
  `REG_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CODE_ID`),
  UNIQUE KEY `CODE_ID` (`CODE_ID`),
  KEY `CODE_REPOSITORY` (`CODE_REPOSITORY`),
  CONSTRAINT `code_ibfk_1` FOREIGN KEY (`CODE_REPOSITORY`) REFERENCES `code_repository` (`CODE_REPOSITORY`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
INSERT INTO `code` VALUES (25,1402129701,'111111111111111111111111','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC+%EB%AF%B8%EB%A6%AC+%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%21%E3%85%88%E3%84%BB%E3%84%B9%0A%E3%84%B9%0A%E3%85%88%E3%84%B7%E3%84%B9%E3%85%88%E3%84%B9',0,0,'2014-06-08 00:01:00'),(26,1402129701,'566666666666666666','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%AF%B8%EB%A6%AC%20%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%21%0A657%0A57%0A56%0A7%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20567%0A%0A657657%20%0A5%20756',0,0,'2014-06-08 00:06:52'),(27,1402129701,'555555555555555555','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%AF%B8%EB%A6%AC%20%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%21%0A%0A67567',0,0,'2014-06-08 00:10:37'),(28,1402129701,'777777777777','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%AF%B8%EB%A6%AC%20%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%21%0A%E3%84%B7%E3%85%88%E3%84%B9%0A%E3%84%B7%E3%84%B9%0A%E3%84%B7%E3%85%88%E3%84%B9%E3%84%B7%0A%E3%84%B9',0,0,'2014-06-08 00:11:14'),(29,1402129701,'5555555555555','text','',0,0,'2014-06-08 00:11:54'),(30,1402129701,'444444444444444444','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%AF%B8%EB%A6%AC%20%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%21567',0,0,'2014-06-08 00:12:54'),(31,1402129701,'444444444444444444444','text','',0,0,'2014-06-08 00:13:43'),(32,1402129701,'6666666666666666666666666','text','',0,0,'2014-06-08 00:16:31'),(33,1402129701,'657567','text','%2F%2F%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%AF%B8%EB%A6%AC%20%EC%9E%85%EB%A0%A5%ED%95%98%EC%84%B8%EC%9A%94%2165%0A56%0A756%0A756',0,0,'2014-06-08 00:17:12'),(34,1402129701,'567567','text','',0,0,'2014-06-08 00:20:55'),(35,1402129701,'555555555555555555','text','',0,0,'2014-06-08 00:24:20'),(36,1402129701,'6666666666666666666666','text','',0,0,'2014-06-08 00:34:37'),(37,1402129701,'66666666666666888','text','',0,0,'2014-06-08 00:34:57'),(38,1402129701,'6666666666666555555555555555444444','text','',0,0,'2014-06-08 00:35:50'),(39,1402129701,'777777777777777777777777777','text','',0,0,'2014-06-08 00:39:41'),(40,1402129701,'56757567567575756','text','',0,0,'2014-06-08 00:40:09'),(41,1402129701,'5675756777','text','',0,0,'2014-06-08 00:40:18'),(42,1402129701,'5767567','text','56756767%0A657%0A657%0A65%0A7657',0,0,'2014-06-08 00:42:23');
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
INSERT INTO `code_repository` VALUES (1402129701,200923397),(999999999,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=100025 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES (100012,200923397,'ISSUE','이슈 등록 테스트 이슈 등록 테스트이슈 등록 테스트ㅍ이슈 등록 테스트 이슈 등록 테스트 이슈 등록 테스트 ..','이슈 등록 테스트<br>이슈 등록 테스트이슈 등록 테스트ㅍ이슈 등록 테스트<br>이슈 등록 테스트<br>이슈 등록 테스트<br>',1,0,'2014-06-07 17:55:10','','ABC'),(100013,200923397,'ISSUE','등록 테스트..','등록 테스트',1,0,'2014-06-07 18:07:39','C:/DEVSNS/saveFile/\\null',''),(100014,200923397,'ISSUE','등록 테스트 2222..','등록 테스트<br>2222',1,0,'2014-06-07 18:11:43','C:/DEVSNS/saveFile/\\null',''),(100015,200923397,'ISSUE','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㄷ ㄷㅈㄹㄷㅈ ㄹㄷㅈ ㄷ ㄹㄷ..','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㄷ<br>ㄷㅈㄹㄷㅈ<br>ㄹㄷㅈ<br>ㄷ<br>ㄹㄷ',1,0,'2014-06-07 18:12:25','C:/DEVSNS/saveFile/\\null',''),(100016,200923397,'ISSUE','afwefwef ㅁ가가닺..','afwefwef<br>ㅁ가가닺',1,0,'2014-06-07 18:14:00','C:/DEVSNS/saveFile/\\null',''),(100017,200923397,'ISSUE','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ 가나다..','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ<br>가나다',1,0,'2014-06-07 18:15:51','C:/DEVSNS/saveFile/\\null',''),(100018,200923397,'ISSUE','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ ㄱㄷ ㄷ ㄳ..','ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ<br>ㄱㄷ<br>ㄷ<br>ㄳ',1,0,'2014-06-07 18:19:30','C:/DEVSNS/saveFile/\\null',''),(100019,200923397,'ISSUE','46574575ㅅ ㅛ ㅓ쇼 ㅓㅅ..','46574575ㅅ<br>ㅛ<br>ㅓ쇼<br>ㅓㅅ',1,0,'2014-06-07 18:20:04','C:/DEVSNS/saveFile/\\null',''),(100020,200923397,'ISSUE','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa..','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1,0,'2014-06-07 18:27:38','C:/DEVSNS/saveFile/\\null',''),(100021,200923397,'ISSUE','TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST TESTTESTTEST TEST TEST..','TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST<br>TESTTESTTEST<br>TEST<br>TEST',1,0,'2014-06-07 18:57:46','C:/DEVSNS/saveFile/\\null',''),(100022,200923397,'ISSUE','afwf131 321 31..','afwf131<br>321<br>31',1,0,'2014-06-07 19:00:05','C:/DEVSNS/saveFile/\\null',''),(100023,200923397,'ISSUE','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa..','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1,0,'2014-06-07 19:43:09','',''),(100024,200923397,'ISSUE','waefawfwef faw fw fa fe..','waefawfwef<br>faw<br>fw<br>fa<br>fe',1,0,'2014-06-07 19:44:17','C:/DEVSNS/saveFile/\\null','');
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
  `ISSUE_ID` int(20) DEFAULT NULL,
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `tag_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (200923397,'ABC',100012),(200923397,'',100013),(200923397,'',100014),(200923397,'',100015),(200923397,'',100016),(200923397,'',100017),(200923397,'',100018),(200923397,'',100019),(200923397,'',100019),(200923397,'',100021),(200923397,'',100022),(200923397,'',100023),(200923397,'',100024);
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
INSERT INTO `user` VALUES (200923397,NULL,'454051','윤지환',NULL,1402129701),(1,'admin','admin','admin','admin',999999999);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `COMMENT_ID` int(50) NOT NULL AUTO_INCREMENT,
  `ISSUE_ID` int(20) NOT NULL,
  `USER_ID` int(50) NOT NULL,
  `COMMENT_CONTENTS` text DEFAULT NULL,
  `REG_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`COMMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (10002,100012,200923397,'코멘트 입니다','2014-06-07 17:55:10');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
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

-- Dump completed on 2014-06-08  1:04:51
