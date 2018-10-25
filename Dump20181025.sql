CREATE DATABASE  IF NOT EXISTS `auction` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auction`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: auction
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `start_time` datetime NOT NULL COMMENT 'Время начала аукциона.',
  `finish_time` datetime NOT NULL COMMENT 'Время окончания проведения аукциона.',
  `description` varchar(200) COLLATE utf8_bin NOT NULL COMMENT 'Описание аукциона.',
  `type` tinyint(1) NOT NULL COMMENT 'Тип аукциона:\n0 - прямой\n1 - обратный',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Аукционы, проводимые в определенное время и включающие определенные лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (1,'2018-07-26 12:00:00','2018-10-26 16:00:00','Truck cars.',0),(2,'2018-07-27 11:00:00','2018-10-27 14:00:00','French art.',0),(3,'2018-07-29 13:00:00','2018-10-29 18:00:00','Ancient weapon.',0),(4,'2018-07-30 11:00:00','2018-10-30 17:00:00','Belgian art.',0),(5,'2018-08-01 14:00:00','2018-10-01 19:00:00','Chineese art.',0),(6,'2018-08-02 12:00:00','2018-10-02 21:00:00','Old chineese weapon.',0),(7,'2018-08-06 13:00:00','2018-10-06 18:00:00','Coupe cars.',0),(8,'2018-08-08 15:00:00','2018-10-08 17:00:00','Convertible cars.',0),(9,'2018-08-11 14:00:00','2018-10-11 18:00:00','Sedan cars.',0),(26,'2018-10-26 11:11:00','2018-10-31 11:11:00','desghadjhdjsgahdjdfjshaklfd',0),(27,'2018-10-26 11:11:00','2018-10-31 12:43:00','Something',0);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `price` decimal(20,2) unsigned NOT NULL COMMENT 'Цена ставки.',
  `user_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - пользователь, сделавший ставку.',
  `lot_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - лот, на который сделана данная ставка.',
  PRIMARY KEY (`id`),
  KEY `fk_bid_user1_idx` (`user_id`),
  KEY `fk_bid_lot1_idx` (`lot_id`),
  CONSTRAINT `fk_bid_lot1` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`),
  CONSTRAINT `fk_bid_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Ставки пользователей на лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (1,8000.00,4,4),(2,8000.00,4,5),(3,8500.00,10,10),(4,9000.00,19,10),(5,9500.00,19,5),(6,2000.00,1,1),(7,2100.00,3,3),(8,2000.00,2,3),(9,1800.00,2,7),(11,3800.00,9,1),(12,2200.00,20,3),(13,2400.00,14,3),(14,1600.00,16,8),(15,1700.00,20,8),(16,1200.00,7,18),(17,1000.00,7,19),(18,1900.00,20,19),(19,10000.00,16,6),(20,6000.00,12,11),(21,6000.00,12,12),(22,6500.00,19,11),(23,7000.00,20,11),(24,1500.00,18,16),(25,2000.00,9,16),(26,8000.00,11,17),(27,5000.00,8,13),(28,7000.00,8,14),(29,8000.00,20,13),(30,3400.00,15,20),(31,1700.00,13,21),(32,1900.00,20,21),(33,100.00,17,9),(34,120.00,7,15),(35,4000.00,5,22),(36,123.23,39,21),(37,123.23,39,22),(38,123.23,39,22),(39,123.12,39,21),(40,123.26,39,4),(41,1.20,39,4),(42,9400.00,39,5),(43,9600.00,39,5),(44,9800.00,39,5),(45,2800.00,39,3),(46,12000.00,39,6),(47,12000.06,39,6),(48,9200.00,39,10),(49,7200.00,39,14),(50,1800.00,39,8),(51,1900.00,39,8),(52,7080.00,39,11),(53,2000.00,39,8),(54,4200.00,39,22),(55,1000.00,39,22),(56,4100.00,39,22),(57,11000.00,39,5),(58,3500.00,39,20),(59,3800.00,39,20),(60,7000.00,39,12),(61,8000.91,39,12),(62,8100.91,39,12),(63,7300.00,39,14),(64,7800.00,39,14),(65,125.00,39,15),(66,1900.00,39,7),(67,8200.00,39,4),(68,2100.00,39,8),(69,7900.00,39,14),(70,2000.00,39,21),(71,2100.00,39,21),(72,8200.00,39,13),(73,123.00,39,22),(74,8300.00,39,4),(75,8300.00,39,13),(76,2200.00,39,8),(77,8200.00,39,17),(78,9000.00,39,14),(79,10000.00,39,14),(80,8200.01,39,17),(81,2200.01,39,8),(82,126.00,39,15),(83,8300.04,39,4),(84,2200.00,48,21),(85,2200.02,48,21),(86,127.00,60,15),(87,2200.03,39,21),(88,11100.00,39,5),(89,2200.04,39,21),(90,11140.00,85,5),(91,2200.05,86,21),(92,3801.00,86,20),(93,9200.01,86,10),(94,8300.05,86,4),(95,0.57,89,35),(96,0.57,89,36),(97,0.57,89,37),(98,0.25,90,38);
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL COMMENT 'Первичный ключ.',
  `name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'Название категории.',
  `parent_id` int(11) DEFAULT NULL COMMENT 'Значение идентификатора родительской категории(позволяет создать иерархию вложенности категорий лотов). У самого корневого элемента иерархии в этом поле будет null.',
  PRIMARY KEY (`id`),
  KEY `fk_category_category1_idx` (`parent_id`),
  CONSTRAINT `fk_category_category1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Таблица с категориями с обеспечением иерархии категорий. ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Home',NULL),(2,'Art',1),(3,'Paintings',2),(4,'Statues',2),(5,'Cars',1),(6,'Truck',5),(7,'Coupe',5),(8,'Hatchback',5),(9,'Minivan',5),(10,'Sedan',5),(11,'Station wagon',5),(12,'Convertible',5),(13,'Wine',1),(14,'Riesling',13),(15,'Gah-vurtz-tra-meener',13),(16,'Chardonnay',13),(17,'Sauvignon blanc',13),(18,'Syrah',13),(19,'Merlot',13),(20,'Cabernet sauvignon',13),(21,'Pinot noir',13),(22,'Antiques',1),(23,'Coins',22),(24,'Weapon',22),(25,'Options',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit`
--

DROP TABLE IF EXISTS `credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `debt` decimal(20,2) unsigned NOT NULL COMMENT 'Сумма выданная в кредит.',
  `percent` decimal(5,2) unsigned NOT NULL COMMENT 'Процент по кредиту.',
  `issued` datetime NOT NULL COMMENT 'Дата выдачи кредита.',
  `paid` datetime DEFAULT NULL COMMENT 'Дата оплаты взятого кредита.',
  `user_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - клиент, взявший кредит.',
  PRIMARY KEY (`id`),
  KEY `fk_credit_user1_idx` (`user_id`),
  CONSTRAINT `fk_credit_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Кредиты, выдаваемые клиентам.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit`
--

LOCK TABLES `credit` WRITE;
/*!40000 ALTER TABLE `credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `picture_name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'Название изображения в папке файловой системы, сгенерованное при сохранении этого изображения, и имеющее длинну до 40 знаков',
  `lot_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - лот, к которому принадлежит данная фотография.',
  PRIMARY KEY (`id`),
  KEY `fk_image_lot1_idx` (`lot_id`),
  CONSTRAINT `fk_image_lot1` FOREIGN KEY (`lot_id`) REFERENCES `lot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Фотографии лота.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (29,'ed10be66-b1d1-4f8a-89a2-b54d46857067jpg',27),(30,'73796529-a00b-4694-846f-234db29006a6png',27),(31,'f839aae6-51e3-45c1-8903-80f8f3fc718epng',27),(32,'93337fd5-1af3-4ca5-b11c-42d07b5e9f18jpg',28),(33,'c34e1816-e9ee-49e0-9b23-acff79f265bbpng',28),(34,'a9b6b060-a949-4763-90a6-0d30d948936cpng',28),(35,'d51359e4-0db8-44a9-bab0-0d1dba1233a1png',29),(36,'28d7e1f3-b2c1-4a4b-a960-9a5dded434dapng',29),(37,'1571beda-78d3-4c1a-bb39-de5265b3f19cjpg',29),(38,'0ad7da49-71ec-40da-8de8-07620a09f54dpng',30),(39,'07d4c17d-7b41-4462-89c1-37d3201e1befpng',30),(40,'0e328d4b-a4f7-4693-9950-00eee9ea6420jpg',30),(41,'eb0e8a5d-fbda-403a-8b33-0a90d72c7c1cpng',31),(42,'6dbc5c88-1716-437a-92a6-c968f56aa294png',31),(43,'ee59ed05-1d50-4890-b065-c5b8422af6c2jpg',31),(44,'eab7d9c8-507a-4143-8525-25a9ccf8fdfcpng',32),(45,'894c5ac0-5b67-48d3-b83f-0453948c1c56png',32),(46,'df205072-0082-4225-9a6e-22f1be6678c1jpg',32),(47,'67e158ad-e722-426e-a9ee-6bb4d9e71d59png',33),(48,'23a4013d-269c-4654-9221-a3d7b2f6b210png',33),(49,'d6655f9d-ab11-4b59-b367-c03aa8ca982fjpg',33),(50,'b038afa1-9850-498b-ab72-c293d458a7d4png',34),(51,'091c1d42-1490-4512-bc7d-9db97a2c0644png',34),(52,'e6cda4e3-480c-442e-983e-96d6ca78b83ajpg',34),(53,'c8eefa71-43ad-4e55-98e2-80ea7f26c3bbpng',35),(54,'ad0d9528-3e59-46d4-a2c8-aed04d50ca05png',35),(55,'972eb83f-e750-4804-8181-0922862f19c0jpg',35),(56,'6bdcc35b-75e3-4956-9b3e-2520734a6596.png',36),(57,'1442d684-a411-40e0-96b3-1f4218d6bad1.png',36),(58,'e61176fb-787b-4973-9e1b-22c26ef726ad.jpg',36),(59,'2867f107-ff75-49ff-8c41-71d567ee12b3.png',37),(60,'aff3534c-0a0e-4768-9d7f-3dfb9024c0a8.png',37),(61,'1aa025eb-a903-4577-81be-7bdd9bf137ed.jpg',37),(62,'56cf07da-9165-458a-8d50-e6c45fd95b96.png',38),(63,'349d8435-0ba3-4993-84f0-da4a3af8ccbe.png',38),(64,'ce42c6a1-f18a-4424-863b-5e23e54c34df.jpg',38);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lot`
--

DROP TABLE IF EXISTS `lot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'Название лота.',
  `description` varchar(200) COLLATE utf8_bin NOT NULL COMMENT 'Описание лота.',
  `is_approved` tinyint(1) NOT NULL COMMENT 'Администратор может подтверждать или отклонять(блокировать) предложенный лот.\nЛот выставляется на торги только после подтверждения админстратора.\n0 - не утвержден администратором и не выставлен на торги.\n1 - активен и будет выставлен на торги.',
  `is_paid` tinyint(1) NOT NULL COMMENT 'Оплачен ли данный лот?\n0 - нет\n1 - да',
  `category_id` int(11) NOT NULL COMMENT 'Внешний ключ - категория, к которой принадлежит лот.',
  `user_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - клиент, выставивший данный лот.',
  `auction_id` bigint(20) NOT NULL COMMENT 'Внешний ключ - id аукциона, на котоый выставлен лот.',
  PRIMARY KEY (`id`),
  KEY `fk_Items_Categories1_idx` (`category_id`),
  KEY `fk_lot_user1_idx` (`user_id`),
  KEY `fk_lot_auction1_idx` (`auction_id`),
  CONSTRAINT `fk_Items_Categories1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_lot_auction1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`),
  CONSTRAINT `fk_lot_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lot`
--

LOCK TABLES `lot` WRITE;
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` VALUES (23,'Name','desc',0,0,3,89,1),(24,'Name','Desc',0,0,3,89,1),(25,'Name','Desc',0,0,3,89,2),(26,'Name','Desc',0,0,3,89,2),(27,'Something','Other',0,0,3,89,2),(28,'Som','Anothers',0,0,3,89,2),(29,'Ngfhdsjk','gvghskdbjf',0,0,3,89,1),(30,'ghdghdw','gdhkshdfdgh',0,0,3,89,2),(31,'fghjk','tfyiuo;retyiu',0,0,3,89,1),(32,'gjklsghciofh','hukkksjdho;;n;ofi',0,0,3,89,2),(33,'gdsuihgjiog;','uiiiyueqjrowi',0,0,3,89,1),(34,'heghwjdskl','ghkjlsndfjh',0,0,3,89,1),(35,'Name','Desription',1,0,3,89,1),(36,'Name','Desription',1,0,3,89,1),(37,'gfsjfhgdhghsakfhd','ghegdshdghjkgfdhjgds',0,0,3,89,2),(38,'Useless','Unknown',1,0,3,90,27);
/*!40000 ALTER TABLE `lot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ.',
  `email` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'Email пользователя. Длинна в соответствии с RFC 5321.',
  `login` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'Псевдоним пользователя.',
  `password` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'Пароль храниться в захешированном формате из интересов безопасности. Планируется использовать алгоритм md5\n',
  `role` tinyint(1) NOT NULL COMMENT 'Роль. Может быть 2 типов:\n0 - администратор\n1 - пользователь',
  `money` decimal(20,2) unsigned NOT NULL COMMENT 'Деньги на счету пользователя.',
  `is_blocked` tinyint(1) NOT NULL COMMENT 'Заблокирован ли данный пользователь?\n0 - не заблокирован\n1 - заблокирован',
  `frozen_money` decimal(20,2) unsigned NOT NULL COMMENT 'Замороженные средства пользователя. Средства замораживаются в этом поле при выставлении ставки.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Данные аккаунта пользователя.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hj@ttvb.ll','administrator','9c22a2e5ffc0933e34bc311a1328088e',0,0.00,0,0.00),(89,'Register@gmail.com','Register','bffef5b042459d7cdd2c082c28bb171d',1,2000.00,0,0.00),(90,'max@gmail.com','Maksim','b58e9d3fb09e0d2df6965221a8716fd3',1,0.02,0,0.00);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'auction'
--
/*!50003 DROP FUNCTION IF EXISTS `getpath` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getpath`(cat_id INT) RETURNS text CHARSET utf8
    DETERMINISTIC
BEGIN
    DECLARE res TEXT;
    CALL getpath(cat_id, res);
    RETURN res;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deleteLot` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteLot`(
   delete_id BIGINT
)
    COMMENT 'A procedure to delete inactive lot'
BEGIN 
	DELETE FROM auction.lot WHERE auction.id = delete_id;
    DELETE FROM auction.image WHERE image.lot_id = delete_id;
    DELETE FROM auction.bid WHERE bid.lot_id = delete_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getpath` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getpath`(IN cat_id INT, OUT path TEXT)
BEGIN
    DECLARE catname VARCHAR(20);
    DECLARE temppath TEXT;
    DECLARE tempparent INT;
    SET max_sp_recursion_depth = 255;
    SELECT name, parent_id FROM category WHERE id=cat_id INTO catname, tempparent;
    IF tempparent IS NULL
    THEN
        SET path = catname;
    ELSE
        CALL getpath(tempparent, temppath);
        SET path = CONCAT(temppath, ' / ', catname);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(
	OUT p_id BIGINT(20), 
	OUT p_email VARCHAR(255), 
    INOUT p_login VARCHAR(40), 
    INOUT p_password VARCHAR(40), 
    OUT p_role TINYINT(1),
    OUT p_money DECIMAL(20,2),
    OUT p_is_blocked TINYINT(1),
    OUT p_frozen_money DECIMAL(20,2))
BEGIN
	SELECT EXISTS(
		SELECT id, email, role, money, is_blocked, frozen_money
        FROM auction.user 
        WHERE (login = p_login) AND (password = MD5(p_password))
		) INTO p_id, p_email, p_role, p_money, p_is_blocked, p_frozen_money;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-25 10:13:24
