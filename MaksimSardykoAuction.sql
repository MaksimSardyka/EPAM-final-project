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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Аукционы, проводимые в определенное время и включающие определенные лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Ставки пользователей на лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Фотографии лота.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lot`
--

LOCK TABLES `lot` WRITE;
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Данные аккаунта пользователя.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hj@ttvb.ll','administrator','9c22a2e5ffc0933e34bc311a1328088e',0,0.00,0,0.00);
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

-- Dump completed on 2018-10-25 23:35:30
