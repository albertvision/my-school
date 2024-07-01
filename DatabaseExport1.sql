-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: myschool
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `absence`
--

DROP TABLE IF EXISTS `absence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `absence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `absence_date` date NOT NULL,
  `absence_type` enum('SICK','FAMILY_REASONS','LATE','ABSENT') NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `status` enum('UNPROCESSED','UNEXCUSED','EXCUSED') DEFAULT NULL,
  `student_number_in_class` int NOT NULL,
  `educ_obj_id` bigint NOT NULL,
  `school_class_id` bigint NOT NULL,
  `study_period_id` bigint NOT NULL,
  `teacher_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtis9ubxdyvenj2b3n6e56mtmn` (`study_period_id`,`school_class_id`,`student_number_in_class`,`absence_date`,`educ_obj_id`,`absence_type`),
  KEY `FK7nx7ar6cobcikdrx026ts0i3t` (`educ_obj_id`),
  KEY `FK93rpkff6p8a1tru2sccx706ss` (`school_class_id`),
  KEY `FK5km7gn0qh16vyl40vqxfpxbu5` (`teacher_id`),
  CONSTRAINT `FK2tfnaqi66w92k66fk0w9m4rhw` FOREIGN KEY (`study_period_id`) REFERENCES `study_period` (`id`),
  CONSTRAINT `FK5km7gn0qh16vyl40vqxfpxbu5` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK7nx7ar6cobcikdrx026ts0i3t` FOREIGN KEY (`educ_obj_id`) REFERENCES `educ_obj` (`id`),
  CONSTRAINT `FK93rpkff6p8a1tru2sccx706ss` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence`
--

LOCK TABLES `absence` WRITE;
/*!40000 ALTER TABLE `absence` DISABLE KEYS */;
/*!40000 ALTER TABLE `absence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_student`
--

DROP TABLE IF EXISTS `class_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_number_in_class` int NOT NULL,
  `school_class_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  `study_period_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKprmfy28c18ntxj7q1gst1r8o1` (`school_class_id`),
  KEY `FKehoe5qmc3ro5nxlcwwa7kf2et` (`student_id`),
  KEY `FKnbpawy8w1t3dgk9i83cjk8lvp` (`study_period_id`),
  CONSTRAINT `FKehoe5qmc3ro5nxlcwwa7kf2et` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKnbpawy8w1t3dgk9i83cjk8lvp` FOREIGN KEY (`study_period_id`) REFERENCES `study_period` (`id`),
  CONSTRAINT `FKprmfy28c18ntxj7q1gst1r8o1` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_student`
--

LOCK TABLES `class_student` WRITE;
/*!40000 ALTER TABLE `class_student` DISABLE KEYS */;
INSERT INTO `class_student` VALUES (1,1,5,7,2),(2,2,5,13,2),(3,3,5,6,2),(4,4,5,4,2),(5,5,5,8,2),(6,6,5,9,2),(7,7,5,5,2),(8,8,5,1,2),(9,9,5,10,2),(10,10,5,12,2),(11,11,5,3,2),(12,12,5,11,2),(13,13,5,2,2),(14,1,11,15,2),(15,2,11,16,2),(16,3,11,22,2),(17,4,11,23,2),(18,5,11,24,2),(19,6,11,14,2),(20,7,11,17,2),(21,8,11,19,2),(22,9,11,18,2),(23,10,11,26,2),(24,11,11,21,2),(25,12,11,25,2),(26,13,11,20,2),(27,1,12,28,2),(28,2,12,30,2),(29,3,12,35,2),(30,4,12,31,2),(31,5,12,29,2),(32,6,12,33,2),(33,7,12,38,2),(34,8,12,34,2),(35,9,12,39,2),(36,10,12,36,2),(37,11,12,32,2),(38,12,12,37,2),(39,13,12,27,2),(40,1,23,43,4),(41,2,23,56,4),(42,3,23,58,4),(43,4,23,49,4),(44,5,23,42,4),(45,6,23,54,4),(46,7,23,55,4),(47,8,23,46,4),(48,9,23,45,4),(49,10,23,53,4),(50,11,23,40,4),(51,12,23,52,4),(52,13,23,41,4),(53,14,23,51,4),(54,15,23,48,4),(55,16,23,57,4),(56,17,23,44,4),(57,18,23,50,4),(58,19,23,59,4),(59,20,23,47,4);
/*!40000 ALTER TABLE `class_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educ_obj`
--

DROP TABLE IF EXISTS `educ_obj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educ_obj` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfc8prigq6vmcxq1cko04xdw3b` (`id`,`school_id`),
  KEY `FKg78gwgbdyqc8tlq3mln925gq9` (`school_id`),
  CONSTRAINT `FKg78gwgbdyqc8tlq3mln925gq9` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educ_obj`
--

LOCK TABLES `educ_obj` WRITE;
/*!40000 ALTER TABLE `educ_obj` DISABLE KEYS */;
INSERT INTO `educ_obj` VALUES (1,'БЕЛ',1),(2,'Литература',1),(3,'Английски език',1),(4,'Немски език',1),(5,'Математика',1),(6,'Компютърно моделиране и информационни технологии',1),(7,'История и цивилизации',1),(8,'География и икономика',1),(9,'Човекът и природата',1),(10,'Музика',1),(11,'Изобразително изкуство',1),(12,'Технологии и предприемачество',1),(13,'ФВС',1),(14,'Биология и здравно образование',1),(15,'Физика и астрономия',1),(16,'Химия и опазване на околната среда',1),(17,'Философия',1),(18,'БЕЛ',2),(19,'Литература',2),(20,'Английски език',2),(21,'Немски език',2),(22,'Математика',2),(23,'Компютърно моделиране и информационни технологии',2),(24,'История и цивилизации',2),(25,'География и икономика',2),(26,'Човекът и природата',2),(27,'Музика',2),(28,'Изобразително изкуство',2),(29,'Технологии и предприемачество',2),(30,'ФВС',2),(31,'Биология и здравно образование',2),(32,'Физика и астрономия',2),(33,'Химия и опазване на околната среда',2),(34,'Философия',2),(35,'Математика СИП',2),(36,'Химия СИП',2),(37,'Информатика СИП',2),(38,'Биология и здравно образование СИП',2);
/*!40000 ALTER TABLE `educ_obj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mark`
--

DROP TABLE IF EXISTS `mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `status` int NOT NULL,
  `student_number_in_class` int NOT NULL,
  `value` double NOT NULL,
  `educ_obj_id` bigint NOT NULL,
  `school_class_id` bigint NOT NULL,
  `study_period_id` bigint NOT NULL,
  `teacher_id` bigint NOT NULL,
  `type_mark_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKef2eb9d4kdra4blyd7dyitwe0` (`id`,`study_period_id`,`school_class_id`,`student_number_in_class`,`educ_obj_id`,`teacher_id`),
  KEY `FK3rwvv6wogb5rh56b7h4hh1lps` (`educ_obj_id`),
  KEY `FKluw9cnsx28a0n4hfteceqxgde` (`school_class_id`),
  KEY `FKan6n0q6ptxgpfwx0jf0fnjsm0` (`study_period_id`),
  KEY `FKqj8tclkd5hinu2sv9xackfjgt` (`teacher_id`),
  KEY `FKcnkxm62e64uv93iowti4six25` (`type_mark_id`),
  CONSTRAINT `FK3rwvv6wogb5rh56b7h4hh1lps` FOREIGN KEY (`educ_obj_id`) REFERENCES `educ_obj` (`id`),
  CONSTRAINT `FKan6n0q6ptxgpfwx0jf0fnjsm0` FOREIGN KEY (`study_period_id`) REFERENCES `study_period` (`id`),
  CONSTRAINT `FKcnkxm62e64uv93iowti4six25` FOREIGN KEY (`type_mark_id`) REFERENCES `type_mark` (`id`),
  CONSTRAINT `FKluw9cnsx28a0n4hfteceqxgde` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`),
  CONSTRAINT `FKqj8tclkd5hinu2sv9xackfjgt` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `mark_chk_1` CHECK ((`value` between 1 and 6))
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES (1,'2024-04-24 12:20:00.000000',0,1,5,1,11,2,1,4),(2,'2024-04-24 12:20:00.000001',0,2,3,1,11,2,1,4),(3,'2024-04-24 12:20:00.000002',0,3,4.5,1,11,2,1,4),(4,'2024-04-24 12:20:00.000003',0,4,6,1,11,2,1,4),(5,'2024-04-24 12:20:00.000004',0,5,5,1,11,2,1,4),(6,'2024-04-24 12:20:00.000005',0,6,5.5,1,11,2,1,4),(7,'2024-04-24 12:20:00.000006',0,7,3.5,1,11,2,1,4),(8,'2024-04-24 12:20:00.000007',0,8,2,1,11,2,1,4),(9,'2024-04-24 12:20:00.000008',0,9,5,1,11,2,1,4),(10,'2024-04-24 12:20:00.000009',0,10,6,1,11,2,1,4),(11,'2024-04-24 12:20:00.000010',0,11,4,1,11,2,1,4),(12,'2024-04-24 12:20:00.000011',0,12,3,1,11,2,1,4),(13,'2024-04-24 12:20:00.000012',0,13,5.5,1,11,2,1,4),(14,'2024-04-22 12:20:00.000000',0,1,5,5,11,2,4,4),(15,'2024-04-22 12:20:00.000001',0,2,3,5,11,2,4,4),(16,'2024-04-22 12:20:00.000002',0,3,4.5,5,11,2,4,4),(17,'2024-04-22 12:20:00.000003',0,4,6,5,11,2,4,4),(18,'2024-04-22 12:20:00.000004',0,5,5,5,11,2,4,4),(19,'2024-04-22 12:20:00.000005',0,6,5.5,5,11,2,4,4),(20,'2024-04-22 12:20:00.000006',0,7,3.5,5,11,2,4,4),(21,'2024-04-22 12:20:00.000007',0,8,2,5,11,2,4,4),(22,'2024-04-22 12:20:00.000008',0,9,5,5,11,2,4,4),(23,'2024-04-22 12:20:00.000009',0,10,6,5,11,2,4,4),(24,'2024-04-22 12:20:00.000010',0,11,4,5,11,2,4,4),(25,'2024-04-22 12:20:00.000011',0,12,3,5,11,2,4,4),(26,'2024-04-22 12:20:00.000012',0,13,5.5,5,11,2,4,4),(27,'2024-04-02 12:20:00.000000',0,1,3,14,11,2,13,1),(28,'2024-04-04 14:20:00.000001',0,2,4.5,15,11,2,14,1),(29,'2024-04-04 12:20:00.000002',0,3,5.5,3,11,2,2,1),(30,'2024-04-06 12:20:00.000003',0,4,6,11,11,2,10,5),(31,'2024-04-08 12:20:00.000004',0,5,6,3,11,2,2,2),(32,'2024-04-14 12:20:00.000005',0,6,5.5,10,11,2,9,7),(33,'2024-04-14 12:20:00.000006',0,7,4.5,6,11,2,5,2),(34,'2024-04-13 12:20:00.000007',0,8,5,3,11,2,2,2),(35,'2024-04-10 12:20:00.000008',0,9,3,10,11,2,9,5),(36,'2024-04-26 12:20:00.000009',0,10,6,13,11,2,12,7),(37,'2024-04-07 12:20:00.000010',0,11,5.5,17,11,2,16,7),(38,'2024-04-17 12:20:00.000011',0,12,4,8,11,2,7,2),(39,'2024-04-13 12:20:00.000012',0,13,6,9,11,2,8,2),(40,'2024-05-12 12:20:00.000000',0,1,5,3,11,2,2,1),(41,'2024-05-14 14:20:00.000001',0,2,5,3,11,2,2,1),(42,'2024-05-04 12:20:00.000002',0,3,5.5,3,11,2,2,1),(43,'2024-05-04 12:20:00.000003',0,4,6,5,11,2,4,8),(44,'2024-05-18 12:20:00.000004',0,5,6,3,11,2,2,8),(45,'2024-05-14 12:20:00.000005',0,6,5.5,7,11,2,6,5),(46,'2024-05-14 12:20:00.000006',0,7,4,6,11,2,5,7),(47,'2024-05-13 12:20:00.000007',0,8,5,2,11,2,1,5),(48,'2024-05-10 12:20:00.000008',0,9,5,10,11,2,9,5),(49,'2024-05-06 12:20:00.000009',0,10,6,13,11,2,12,5),(50,'2024-05-07 12:20:00.000010',0,11,4.5,17,11,2,16,1),(51,'2024-05-12 12:20:00.000011',0,12,4,8,11,2,7,2),(52,'2024-05-14 12:20:00.000012',0,13,5.5,9,11,2,8,2),(53,'2024-05-02 12:20:00.000000',0,1,3,14,11,2,13,1),(54,'2024-05-04 14:20:00.000001',0,2,4.5,15,11,2,14,2),(55,'2024-05-04 12:20:00.000002',0,3,5.5,3,11,2,2,2),(56,'2024-05-06 12:20:00.000003',0,4,6,11,11,2,10,2),(57,'2024-05-08 12:20:00.000004',0,5,6,3,11,2,2,5),(58,'2024-05-14 12:20:00.000005',0,6,5.5,10,11,2,9,5),(59,'2024-05-14 12:20:00.000006',0,7,4,6,11,2,5,5),(60,'2024-05-13 12:20:00.000007',0,8,5,3,11,2,2,2),(61,'2024-05-10 12:20:00.000008',0,9,5,10,11,2,9,1),(62,'2024-05-26 12:20:00.000009',0,10,6,13,11,2,12,1),(63,'2024-05-07 12:20:00.000010',0,11,5,17,11,2,16,1),(64,'2024-05-17 12:20:00.000011',0,12,4,8,11,2,7,2),(65,'2024-05-13 12:20:00.000012',0,13,4,9,11,2,8,2),(66,'2024-04-24 12:20:00.000000',0,1,4.5,1,12,2,1,4),(67,'2024-04-24 12:20:00.000001',0,2,4,1,12,2,1,4),(68,'2024-04-24 12:20:00.000002',0,3,5.5,1,12,2,1,4),(69,'2024-04-24 12:20:00.000003',0,4,5,1,12,2,1,4),(70,'2024-04-24 12:20:00.000004',0,5,6,1,12,2,1,4),(71,'2024-04-24 12:20:00.000005',0,6,4,1,12,2,1,4),(72,'2024-04-24 12:20:00.000006',0,7,3.5,1,12,2,1,4),(73,'2024-04-24 12:20:00.000007',0,8,5.5,1,12,2,1,4),(74,'2024-04-24 12:20:00.000008',0,9,5,1,12,2,1,4),(75,'2024-04-24 12:20:00.000009',0,10,6,1,12,2,1,4),(76,'2024-04-24 12:20:00.000010',0,11,3.5,1,12,2,1,4),(77,'2024-04-24 12:20:00.000011',0,12,2,1,12,2,1,4),(78,'2024-04-24 12:20:00.000012',0,13,6,1,12,2,1,4),(79,'2024-04-22 12:20:00.000000',0,1,5,5,12,2,4,4),(80,'2024-04-22 12:20:00.000001',0,2,3.5,5,12,2,4,4),(81,'2024-04-22 12:20:00.000002',0,3,4.5,5,12,2,4,4),(82,'2024-04-22 12:20:00.000003',0,4,6,5,12,2,4,4),(83,'2024-04-22 12:20:00.000004',0,5,5,5,12,2,4,4),(84,'2024-04-22 12:20:00.000005',0,6,5.5,5,12,2,4,4),(85,'2024-04-22 12:20:00.000006',0,7,3.5,5,12,2,4,4),(86,'2024-04-22 12:20:00.000007',0,8,6,5,12,2,4,4),(87,'2024-04-22 12:20:00.000008',0,9,5,5,12,2,4,4),(88,'2024-04-22 12:20:00.000009',0,10,3,5,12,2,4,4),(89,'2024-04-22 12:20:00.000010',0,11,3.5,5,12,2,4,4),(90,'2024-04-22 12:20:00.000011',0,12,3,5,12,2,4,4),(91,'2024-04-22 12:20:00.000012',0,13,2,5,12,2,4,4),(92,'2024-05-04 12:20:00.000000',0,1,4.5,14,12,2,13,1),(93,'2024-05-06 12:20:00.000001',0,2,4,15,12,2,14,3),(94,'2024-05-14 12:20:00.000002',0,3,5.5,3,12,2,2,3),(95,'2024-05-12 12:20:00.000003',0,4,5,11,12,2,10,3),(96,'2024-05-13 12:20:00.000004',0,5,5,3,12,2,2,7),(97,'2024-05-14 12:20:00.000005',0,6,6,10,12,2,9,7),(98,'2024-05-26 12:20:00.000006',0,7,4,6,12,2,5,8),(99,'2024-05-28 12:20:00.000007',0,8,5.5,3,12,2,2,1),(100,'2024-05-30 12:20:00.000008',0,9,5,10,12,2,9,2),(101,'2024-05-14 12:20:00.000009',0,10,6,13,12,2,12,5),(102,'2024-05-17 12:20:00.000010',0,11,6,17,12,2,16,5),(103,'2024-05-15 12:20:00.000011',0,12,6,8,12,2,7,7),(104,'2024-05-13 12:20:00.000012',0,13,6,9,12,2,8,7),(105,'2024-05-04 12:20:00.000000',0,1,4.5,3,12,2,2,1),(106,'2024-05-06 12:20:00.000001',0,2,4,3,12,2,2,1),(107,'2024-05-14 12:20:00.000002',0,3,5.5,3,12,2,2,1),(108,'2024-05-12 12:20:00.000003',0,4,5,5,12,2,4,3),(109,'2024-05-13 12:20:00.000004',0,5,4,3,12,2,2,1),(110,'2024-05-14 12:20:00.000005',0,6,3.5,7,12,2,6,5),(111,'2024-05-26 12:20:00.000006',0,7,3.5,6,12,2,5,5),(112,'2024-05-28 12:20:00.000007',0,8,5.5,2,12,2,1,7),(113,'2024-05-30 12:20:00.000008',0,9,5,10,12,2,9,8),(114,'2024-05-14 12:20:00.000009',0,10,5.5,13,12,2,12,1),(115,'2024-05-17 12:20:00.000010',0,11,6,17,12,2,16,1),(116,'2024-05-15 12:20:00.000011',0,12,3.5,8,12,2,7,1),(117,'2024-05-13 12:20:00.000012',0,13,6,9,12,2,8,1);
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `day_of_week` tinyint NOT NULL,
  `lesson_order` int NOT NULL,
  `educ_obj_id` bigint DEFAULT NULL,
  `school_class_id` bigint NOT NULL,
  `study_period_id` bigint NOT NULL,
  `teacher_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKneihnc368hk66s2dy6nd9w5j0` (`study_period_id`,`school_class_id`,`day_of_week`,`lesson_order`),
  KEY `FKmjp9oqi0up0s329uugc5a5wak` (`school_class_id`),
  KEY `FKemr6b6nt6h9a4penphu8m8m7s` (`educ_obj_id`),
  KEY `FKpxd2tm4n0n56le1s7374p5av3` (`teacher_id`),
  CONSTRAINT `FKemr6b6nt6h9a4penphu8m8m7s` FOREIGN KEY (`educ_obj_id`) REFERENCES `educ_obj` (`id`),
  CONSTRAINT `FKkaihwdt5iq9obgywonq13shwr` FOREIGN KEY (`study_period_id`) REFERENCES `study_period` (`id`),
  CONSTRAINT `FKmjp9oqi0up0s329uugc5a5wak` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`),
  CONSTRAINT `FKpxd2tm4n0n56le1s7374p5av3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `program_chk_1` CHECK ((`day_of_week` between 0 and 6))
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (1,0,1,1,5,2,1),(2,0,2,2,5,2,1),(3,0,3,7,5,2,6),(4,0,4,8,5,2,7),(5,0,5,9,5,2,8),(6,0,6,5,5,2,4),(7,1,1,3,5,2,2),(8,1,2,3,5,2,2),(9,1,3,6,5,2,6),(10,1,4,6,5,2,6),(11,1,5,13,5,2,12),(12,1,6,13,5,2,12),(13,2,1,10,5,2,9),(14,2,2,11,5,2,10),(15,2,3,5,5,2,4),(16,2,4,1,5,2,1),(17,2,5,7,5,2,6),(18,3,1,12,5,2,11),(19,3,2,13,5,2,12),(20,3,3,9,5,2,8),(21,3,4,6,5,2,5),(22,3,5,1,5,2,1),(23,3,6,5,5,2,4),(24,4,1,7,5,2,6),(25,4,2,3,5,2,2),(26,4,3,1,5,2,1),(27,4,4,8,5,2,7),(28,4,5,6,5,2,5),(29,4,6,6,5,2,5),(30,0,1,3,11,2,2),(31,0,2,3,11,2,2),(32,0,3,6,11,2,6),(33,0,4,6,11,2,6),(34,0,5,13,11,2,12),(35,0,6,13,11,2,12),(36,1,1,10,11,2,9),(37,1,2,11,11,2,10),(38,1,3,5,11,2,4),(39,1,4,1,11,2,1),(40,1,5,7,11,2,6),(41,1,6,14,11,2,13),(42,2,1,12,11,2,11),(43,2,2,13,11,2,12),(44,2,3,9,11,2,8),(45,2,4,15,11,2,14),(46,2,5,1,11,2,1),(47,2,6,5,11,2,4),(48,3,1,1,11,2,1),(49,3,2,2,11,2,1),(50,3,3,7,11,2,6),(51,3,4,8,11,2,7),(52,3,5,9,11,2,8),(53,3,6,5,11,2,4),(54,4,1,16,11,2,15),(55,4,2,3,11,2,2),(56,4,3,1,11,2,1),(57,4,4,8,11,2,7),(58,4,5,6,11,2,5),(59,4,6,17,11,2,16),(60,0,1,12,12,2,11),(61,0,2,13,12,2,12),(62,0,3,9,12,2,8),(63,0,4,15,12,2,14),(64,0,5,1,12,2,1),(65,0,6,5,12,2,4),(66,1,1,3,12,2,2),(67,1,2,3,12,2,2),(68,1,3,6,12,2,6),(69,1,4,6,12,2,6),(70,1,5,13,12,2,12),(71,1,6,13,12,2,12),(72,2,1,1,12,2,1),(73,2,2,2,12,2,1),(74,2,3,7,12,2,6),(75,2,4,8,12,2,7),(76,2,5,9,12,2,8),(77,2,6,5,12,2,4),(78,3,1,16,12,2,15),(79,3,2,3,12,2,2),(80,3,3,1,12,2,1),(81,3,4,8,12,2,7),(82,3,5,6,12,2,5),(83,3,6,17,12,2,16),(84,4,1,10,12,2,9),(85,4,2,11,12,2,10),(86,4,3,5,12,2,4),(87,4,4,1,12,2,1),(88,4,5,7,12,2,6),(89,4,6,14,12,2,13);
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `principal` bigint DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cstdv7l1ednkll9uuiqsu89wt` (`principal`),
  CONSTRAINT `FKg58v7mvpq4qa7lnp3niaqxcu9` FOREIGN KEY (`principal`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (1,'1056 Училище',1,NULL),(2,'ПМГ',34,NULL);
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_class`
--

DROP TABLE IF EXISTS `school_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school_class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4cp5tb6wbkx101jjsgli7953c` (`school_id`,`name`),
  CONSTRAINT `FK2br5afl4106t79kv6m2bgwu8b` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_class`
--

LOCK TABLES `school_class` WRITE;
/*!40000 ALTER TABLE `school_class` DISABLE KEYS */;
INSERT INTO `school_class` VALUES (11,'10а',1),(12,'10б',1),(13,'11а',1),(14,'11б',1),(15,'12а',1),(16,'12б',1),(1,'5а',1),(2,'5б',1),(3,'6а',1),(4,'6б',1),(5,'7а',1),(6,'7б',1),(7,'8а',1),(8,'8б',1),(9,'9а',1),(10,'9б',1),(23,'10а',2),(24,'10б',2),(25,'11а',2),(26,'11б',2),(27,'12а',2),(28,'12б',2),(17,'7а',2),(18,'7б',2),(19,'8а',2),(20,'8б',2),(21,'9а',2),(22,'9б',2);
/*!40000 ALTER TABLE `school_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_class_educ_obj`
--

DROP TABLE IF EXISTS `school_class_educ_obj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school_class_educ_obj` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `educ_obj_id` bigint NOT NULL,
  `school_class_id` bigint NOT NULL,
  `study_period_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpaysomymyj7dofaifx5b0nty` (`study_period_id`,`school_class_id`,`educ_obj_id`),
  KEY `FK7awbfrox1icj097smax73jc48` (`educ_obj_id`),
  KEY `FK6remja57705angynp8f9mpw4s` (`school_class_id`),
  CONSTRAINT `FK6remja57705angynp8f9mpw4s` FOREIGN KEY (`school_class_id`) REFERENCES `school_class` (`id`),
  CONSTRAINT `FK7awbfrox1icj097smax73jc48` FOREIGN KEY (`educ_obj_id`) REFERENCES `educ_obj` (`id`),
  CONSTRAINT `FK7dwg0d61l7b6cu8o40td6jdar` FOREIGN KEY (`study_period_id`) REFERENCES `study_period` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_class_educ_obj`
--

LOCK TABLES `school_class_educ_obj` WRITE;
/*!40000 ALTER TABLE `school_class_educ_obj` DISABLE KEYS */;
INSERT INTO `school_class_educ_obj` VALUES (1,1,5,2),(2,2,5,2),(3,3,5,2),(4,4,5,2),(5,5,5,2),(6,6,5,2),(7,7,5,2),(8,8,5,2),(9,9,5,2),(10,10,5,2),(11,11,5,2),(12,12,5,2),(13,13,5,2),(14,1,11,2),(15,2,11,2),(16,3,11,2),(17,4,11,2),(18,5,11,2),(19,6,11,2),(20,7,11,2),(21,8,11,2),(22,9,11,2),(23,10,11,2),(24,11,11,2),(25,12,11,2),(26,13,11,2),(27,14,11,2),(28,15,11,2),(29,16,11,2),(30,17,11,2),(31,1,12,2),(32,2,12,2),(33,3,12,2),(34,4,12,2),(35,5,12,2),(36,6,12,2),(37,7,12,2),(38,8,12,2),(39,9,12,2),(40,10,12,2),(41,11,12,2),(42,12,12,2),(43,13,12,2),(44,14,12,2),(45,15,12,2),(46,16,12,2),(47,17,12,2);
/*!40000 ALTER TABLE `school_class_educ_obj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `egn` varchar(10) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `parent_egn` varchar(10) NOT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_student_egn_per_school` (`egn`,`school_id`),
  KEY `FK1vm0oqhk9viil6eocn49rj1l9` (`school_id`),
  CONSTRAINT `FK1vm0oqhk9viil6eocn49rj1l9` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'0401010001','Иван','Георгиев','Иванов','7912310001','Георги Иванов',1,1),(2,'0401020002','Петя','Николова','Петрова','8012310002','Николай Петров',1,1),(3,'0401030003','Мария','Димитрова','Георгиева','8112310003','Димитър Георгиев',1,1),(4,'0401040004','Георги','Симеонов','Симеонов','7912310004','Симеон Симеонов',1,1),(5,'0401050005','Ива','Христова','Иванова','7912310005','Христо Иванов',1,1),(6,'0401060006','Васил','Тодоров','Тодоров','7512310006','Тодор Тодоров',1,1),(7,'0401070007','Анна','Иванова','Петрова','7912310007','Иван Петров',1,1),(8,'0401080008','Даниел','Георгиев','Димитров','7412310008','Георги Димитров',1,1),(9,'0401090009','Елена','Христова','Георгиева','7912310009','Христо Георгиев',1,1),(10,'0401100010','Кристина','Петрова','Иванова','7312310010','Петър Иванов',1,1),(11,'0401110011','Мартин','Стоянов','Симеонов','7912310011','Стоян Симеонов',1,1),(12,'0401120012','Любомир','Георгиев','Димитров','7912310012','Георги Димитров',1,1),(13,'0401130013','Борис','Тодоров','Христов','7812310013','Тодор Христов',1,1),(14,'0401140014','Марияна','Иванова','Георгиева','7912310014','Иван Георгиев',1,1),(15,'0401150015','Алек','Димитров','Петров','7512310015','Димитър Петров',1,1),(16,'0401160016','Виктория','Стоянова','Иванова','7912310016','Стоян Иванов',1,1),(17,'0401170017','Михаил','Георгиев','Тодоров','7712310017','Георги Тодоров',1,1),(18,'0401180018','Нина','Иванова','Димитрова','7512310018','Иван Димитров',1,1),(19,'0401190019','Никола','Петров','Георгиев','7412310019','Петър Георгиев',1,1),(20,'0401200020','Яна','Симеонова','Христова','7412310020','Симеон Христов',1,1),(21,'0401210021','Стефан','Иванов','Георгиев','7712310021','Иван Георгиев',1,1),(22,'0401220022','Дарина','Тодорова','Иванова','7912310022','Тодор Иванов',1,1),(23,'0401230023','Калоян','Георгиев','Симеонов','7812310023','Георги Симеонов',1,1),(24,'0401240024','Лилия','Христова','Петрова','7912310024','Христо Петров',1,1),(25,'0401250025','Христо','Иванов','Димитров','7812310025','Иван Димитров',1,1),(26,'0401260026','Павел','Георгиев','Петров','7912310026','Георги Петров',1,1),(27,'0401270027','Симона','Димитрова','Иванова','7912310027','Димитър Иванов',1,1),(28,'0401280028','Андрей','Стоянов','Георгиев','7912310028','Стоян Георгиев',1,1),(29,'0401290029','Гергана','Иванова','Петрова','7612310029','Иван Петров',1,1),(30,'0401300030','Ани','Тодорова','Димитрова','7412310030','Тодор Димитров',1,1),(31,'0401310031','Владислав','Георгиев','Иванов','7912310031','Георги Иванов',1,1),(32,'0401320032','Ралица','Христова','Симеонова','7512310032','Христо Симеонов',1,1),(33,'0401330033','Деница','Иванова','Георгиева','7912310033','Иван Георгиев',1,1),(34,'0401340034','Константин','Петров','Тодоров','7912310034','Петър Тодоров',1,1),(35,'0401350035','Ася','Симеонова','Иванова','7912310035','Симеон Иванов',1,1),(36,'0401360036','Мая','Георгиева','Димитрова','7212310036','Георги Димитров',1,1),(37,'0401370037','Румен','Тодоров','Петров','7312310037','Тодор Петров',1,1),(38,'0401380038','Емилия','Иванова','Христова','7512310038','Иван Христов',1,1),(39,'0401390039','Марио','Георгиев','Симеонов','7612310039','Георги Симеонов',1,1),(40,'401012345','Иван','Иванов','Петров','7912123456','Иван Петров',1,2),(41,'401023456','Мария','Петрова','Иванова','7811123456','Мария Иванова',1,2),(42,'401034567','Георги','Георгиев','Стоянов','7709123456','Георги Стоянов',1,2),(43,'401045678','Ана','Димитрова','Костова','7912123456','Димитър Костов',1,2),(44,'401056789','Петър','Николов','Иванов','7808123456','Николай Иванов',1,2),(45,'401067890','Елена','Иванова','Петрова','7907123456','Елена Петрова',1,2),(46,'401078901','Даниел','Стоянов','Георгиев','7712123456','Стоян Георгиев',1,2),(47,'401089012','София','Петрова','Иванова','7810123456','Петър Иванов',1,2),(48,'401090123','Николай','Димитров','Стоянов','7909123456','Димитър Стоянов',1,2),(49,'401101234','Виктория','Георгиева','Петрова','7807123456','Георги Петров',1,2),(50,'401112345','Симеон','Иванов','Николов','7911123456','Иван Николов',1,2),(51,'401123456','Мария','Стоянова','Георгиева','7708123456','Стоян Георгиев',1,2),(52,'401134567','Иван','Петров','Димитров','7912123456','Петър Димитров',1,2),(53,'401145678','Елена','Николова','Иванова','7811123456','Николай Иванов',1,2),(54,'401156789','Георги','Георгиев','Стоянов','7709123456','Георги Стоянов',1,2),(55,'401167890','Даниел','Петров','Николов','7910123456','Петър Николов',1,2),(56,'401178901','Ана','Иванова','Стоянова','7808123456','Иван Стоянов',1,2),(57,'401189012','Петър','Димитров','Петров','7911123456','Димитър Петров',1,2),(58,'401190123','Виктория','Георгиева','Иванова','7807123456','Георги Иванов',1,2),(59,'401201234','Симеон','Стоянов','Георгиев','7912123456','Стоян Георгиев',1,2);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_period`
--

DROP TABLE IF EXISTS `study_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_period` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `current_semester` int NOT NULL,
  `current_year` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK88h8hp9gvhyfw5i7hsqfii9f6` (`school_id`,`current_year`,`current_semester`),
  CONSTRAINT `FKdvb9ab4w8swqflo0v1bnjh80p` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_period`
--

LOCK TABLES `study_period` WRITE;
/*!40000 ALTER TABLE `study_period` DISABLE KEYS */;
INSERT INTO `study_period` VALUES (1,1,2024,'Есенен срок',0,1),(2,2,2024,'Пролетен срок',1,1),(3,1,2024,'Срок ЕСЕН',0,2),(4,2,2024,'Срок ПРОЛЕТ',1,2);
/*!40000 ALTER TABLE `study_period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teach_educ`
--

DROP TABLE IF EXISTS `teach_educ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teach_educ` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `educ_obj_id` bigint NOT NULL,
  `school_id` bigint NOT NULL,
  `teacher_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKajyb2hhpcdwd5sru8aqiwgq91` (`school_id`,`teacher_id`,`educ_obj_id`),
  KEY `FK9kt5osr1flt0olyhoajn9hogi` (`educ_obj_id`),
  KEY `FK6fpg4mi3p1nbj1dmk36tqavf3` (`teacher_id`),
  CONSTRAINT `FK6fpg4mi3p1nbj1dmk36tqavf3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FK9kt5osr1flt0olyhoajn9hogi` FOREIGN KEY (`educ_obj_id`) REFERENCES `educ_obj` (`id`),
  CONSTRAINT `FKhxk3icieg6l6u5y5s6d8nc1yl` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teach_educ`
--

LOCK TABLES `teach_educ` WRITE;
/*!40000 ALTER TABLE `teach_educ` DISABLE KEYS */;
INSERT INTO `teach_educ` VALUES (39,1,1,1),(40,2,1,1),(3,3,1,2),(4,4,1,3),(5,5,1,4),(6,6,1,5),(7,7,1,6),(8,8,1,7),(9,9,1,8),(10,10,1,9),(11,11,1,10),(12,12,1,11),(13,13,1,12),(14,14,1,13),(15,15,1,14),(16,16,1,15),(17,17,1,16),(18,18,2,17),(19,19,2,17),(20,20,2,18),(21,21,2,19),(22,22,2,20),(23,23,2,21),(24,24,2,22),(25,25,2,23),(26,26,2,24),(27,27,2,25),(28,28,2,26),(29,29,2,27),(30,30,2,28),(31,31,2,29),(32,32,2,30),(33,33,2,31),(34,34,2,32),(35,35,2,33),(43,33,2,34),(44,36,2,34),(37,37,2,35),(38,38,2,36);
/*!40000 ALTER TABLE `teach_educ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `egn` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKebpi6hk1v1bvg3ipyojlngrby` (`id`,`school_id`),
  KEY `FKrg46bnmgbcccayv14naymqg3r` (`school_id`),
  CONSTRAINT `FKrg46bnmgbcccayv14naymqg3r` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'7907116848','Пенка Василева',1),(2,'5910121997','Биянка Тодорова',1),(3,'8304272286','Александър Тотев',1),(4,'6212179482','Николай Великов',1),(5,'7407228935','Дейвид Георгиев',1),(6,'8208061392','Габриела Талева',1),(7,'8004159830','Веска Ангарева',1),(8,'6208248804','Катя Игнатова',1),(9,'5910013430','Снежана Дженкова',1),(10,'5712171793','Славена Белева',1),(11,'6911138478','Нели Иванова',1),(12,'5001129135','Костадин Пламенов',1),(13,'5005138313','Емануела Александрова',1),(14,'5112028107','Катерина Костова',1),(15,'7506078191','Габриела Грозданова',1),(16,'5911204786','Моника Боянова',1),(17,'7602204563','Веселина Герова',2),(18,'7408123569','Лидия Владева',2),(19,'7304257892','Пламена Пеева',2),(20,'7203098761','Ива Иванова',2),(21,'7107234568','Павлин Цонев',2),(22,'7005162345','Габриела Талева',2),(23,'6908305674','Мария Колева',2),(24,'6802241234','Сирвия Кутлева',2),(25,'6704176789','Станимира Иванова',2),(26,'6603012345','Теодора Цончева',2),(27,'6509145678','Дейвид Георгиев',2),(28,'6407057890','Ивайло Димитров',2),(29,'6305294567','Сирвия Кутлева',2),(30,'6203236789','Дияна Йотова',2),(31,'6102162345','Надежда Филипова',2),(32,'6001098765','Мария Асенова',2),(33,'5912023456','Ива Иванова',2),(34,'5803255678','Надежда Филипова',2),(35,'5704177890','Павлин Цонев',2),(36,'5602081234','Сирвия Кутлева',2);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_mark`
--

DROP TABLE IF EXISTS `type_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_mark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `school_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi4kyww5952ltst0bqe0qsqsf7` (`school_id`,`name`),
  CONSTRAINT `FKdwmx9s8m035q1qc3mq3iasfk8` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_mark`
--

LOCK TABLES `type_mark` WRITE;
/*!40000 ALTER TABLE `type_mark` DISABLE KEYS */;
INSERT INTO `type_mark` VALUES (3,' Контролна работа',1),(2,'Активно участие',1),(9,'Входно ниво',1),(8,'Домашна работа',1),(10,'Изходно ниво',1),(11,'Класна работа',1),(6,'Практическо изпитване',1),(7,'Проект',1),(5,'Самостоятелна работа',1),(4,'Тест',1),(1,'Устно изпитване',1),(14,' Контролна работа',2),(13,'Активно участие',2),(18,'Входно ниво',2),(17,'Домашна работа',2),(19,'Изходно ниво',2),(20,'Класна работа',2),(16,'Самостоятелна работа',2),(15,'Тест',2),(12,'Устно изпитване',2);
/*!40000 ALTER TABLE `type_mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','DIRECTOR','TEACHER','STUDENT','PARENT') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `personal_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'{bcrypt}$2a$10$uRbhHQR62orEv4fv67aejuYp3d5dMJWYody3Rry4K/B0kBgUlvKnO','ADMIN','admin',NULL),(8,'{bcrypt}$2a$10$OfF8dqhiOyw7ex1vkHdu4e7IojPM8UOMbZP4kwxcFQh45g5teO/Cm','DIRECTOR','director',NULL),(9,'{bcrypt}$2a$10$ih1YNP7/qPvB1Z.x.d66G.dTTZzabL7xtWjJgfhdufJP5ZWHXd4ni','TEACHER','teacher',NULL),(10,'{bcrypt}$2a$10$wd1DZaMN8velo3idK62n/O32daZ/tCAKGgk.PyUGHf/7nPrpebt1S','STUDENT','student',NULL),(11,'{bcrypt}$2a$10$7jJdDBsJAU3FHXJBVen16OwG.XZ8mmI2D2QRh9seXHeMQQqhUuvNK','PARENT','parent',NULL),(95,'{bcrypt}$2a$10$o1f2cdS.tB1BAACjD6xfG.QS5G2ehkWvDxPmGda4wlIJEMZlftnt6','ADMIN','pesho',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-01 23:13:08
