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

DROP DATABASE IF EXISTS `auction`;
CREATE SCHEMA `auction`;
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Аукционы, проводимые в определенное время и включающие определенные лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (1,'2018-07-26 12:00:00','2018-10-26 16:00:00','Truck cars.',0),(2,'2018-07-27 11:00:00','2018-10-27 14:00:00','French art.',0),(3,'2018-07-29 13:00:00','2018-10-29 18:00:00','Ancient weapon.',0),(4,'2018-07-30 11:00:00','2018-10-30 17:00:00','Belgian art.',0),(5,'2018-08-01 14:00:00','2018-10-01 19:00:00','Chineese art.',0),(6,'2018-08-02 12:00:00','2018-10-02 21:00:00','Old chineese weapon.',0),(7,'2018-08-06 13:00:00','2018-10-06 18:00:00','Coupe cars.',0),(8,'2018-08-08 15:00:00','2018-10-08 17:00:00','Convertible cars.',0),(9,'2018-08-11 14:00:00','2018-10-11 18:00:00','Sedan cars.',0),(10,'2018-08-16 13:00:00','2018-10-16 19:00:00','Italian wines.',0),(11,'2018-08-20 11:00:00','2018-10-20 16:00:00','Ancient indian weapon.',0),(12,'2018-08-21 12:00:00','2018-10-21 18:00:00','Old turkish weapon.',0),(13,'2018-08-24 13:00:00','2018-10-24 17:00:00','Coins.',0),(14,'2018-08-26 14:00:00','2018-10-26 19:00:00','Wheat options contract.',1),(15,'2018-10-10 16:30:00','2018-12-10 16:20:00','some',0),(16,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(17,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(18,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(19,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(20,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(21,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(22,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(23,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(24,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0),(25,'2018-10-23 01:01:00','2018-10-25 01:01:00','Description',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Ставки пользователей на лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (1,8000.00,4,4),(2,8000.00,4,5),(3,8500.00,10,10),(4,9000.00,19,10),(5,9500.00,19,5),(6,2000.00,1,1),(7,2100.00,3,3),(8,2000.00,2,3),(9,1800.00,2,7),(10,3000.00,6,1),(11,3800.00,9,1),(12,2200.00,20,3),(13,2400.00,14,3),(14,1600.00,16,8),(15,1700.00,20,8),(16,1200.00,7,18),(17,1000.00,7,19),(18,1900.00,20,19),(19,10000.00,16,6),(20,6000.00,12,11),(21,6000.00,12,12),(22,6500.00,19,11),(23,7000.00,20,11),(24,1500.00,18,16),(25,2000.00,9,16),(26,8000.00,11,17),(27,5000.00,8,13),(28,7000.00,8,14),(29,8000.00,20,13),(30,3400.00,15,20),(31,1700.00,13,21),(32,1900.00,20,21),(33,100.00,17,9),(34,120.00,7,15),(35,4000.00,5,22),(36,123.23,39,21),(37,123.23,39,22),(38,123.23,39,22),(39,123.12,39,21),(40,123.26,39,4),(41,1.20,39,4),(42,9400.00,39,5),(43,9600.00,39,5),(44,9800.00,39,5),(45,2800.00,39,3),(46,12000.00,39,6),(47,12000.06,39,6),(48,9200.00,39,10),(49,7200.00,39,14),(50,1800.00,39,8),(51,1900.00,39,8),(52,7080.00,39,11),(53,2000.00,39,8),(54,4200.00,39,22),(55,1000.00,39,22),(56,4100.00,39,22),(57,11000.00,39,5),(58,3500.00,39,20),(59,3800.00,39,20),(60,7000.00,39,12),(61,8000.91,39,12),(62,8100.91,39,12),(63,7300.00,39,14),(64,7800.00,39,14),(65,125.00,39,15),(66,1900.00,39,7),(67,8200.00,39,4),(68,2100.00,39,8),(69,7900.00,39,14),(70,2000.00,39,21),(71,2100.00,39,21),(72,8200.00,39,13),(73,123.00,39,22),(74,8300.00,39,4),(75,8300.00,39,13),(76,2200.00,39,8),(77,8200.00,39,17),(78,9000.00,39,14),(79,10000.00,39,14),(80,8200.01,39,17),(81,2200.01,39,8),(82,126.00,39,15),(83,8300.04,39,4),(84,2200.00,48,21),(85,2200.02,48,21),(86,127.00,60,15),(87,2200.03,39,21),(88,11100.00,39,5),(89,2200.04,39,21),(90,11140.00,85,5),(91,2200.05,86,21),(92,3801.00,86,20);
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Фотографии лота.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'1',1),(2,'2',1),(3,'3',2),(4,'18',3),(5,'5',4),(6,'12',4),(7,'7',4),(8,'8',5),(9,'9',6),(10,'10',7),(11,'11',8),(12,'26',8),(13,'13',8),(14,'14',9),(15,'15',10),(16,'16',11),(17,'17',11),(18,'19',12),(19,'20',13),(20,'21',14),(21,'22',15),(22,'23',16),(23,'24',17),(24,'25',18),(25,'27',19),(26,'28',21),(27,'29',22),(28,'30',22);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Лоты.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lot`
--

LOCK TABLES `lot` WRITE;
/*!40000 ALTER TABLE `lot` DISABLE KEYS */;
INSERT INTO `lot` VALUES (1,'Old french painting.','Author unknown. 16 century.',1,1,3,1,2),(2,'Socks','My old socks.',0,0,4,3,2),(3,'Paul Cezanne. Unknown.','Paul Cezanne\'s masterpice.',1,1,3,2,2),(4,'Ford truck.','F-150',1,0,6,4,1),(5,'Audi truck.','Q7',1,1,6,4,1),(6,'Chineese old sword.','Estimated 6 century.',1,0,24,16,6),(7,'Georges Seurat. Purple sky.','Georges Seurat\'s masterpice',1,0,3,2,2),(8,'Old nordic sword.','Estimated 11 century. Vikings used!',1,1,24,16,3),(9,'Fake ancient coin.','Valid looking.',0,0,22,17,13),(10,'Pickup truck.','Generic.',1,1,6,10,1),(11,'Ferrari coupe 125s.','First ever Ferrari car.',1,1,7,12,7),(12,'Ferrari coupe 375.','Old Ferrari classic.',1,0,7,12,7),(13,'Hoddles Creek 1er wine.','Fine wine.',1,1,16,8,10),(14,'Independent Birmingham Wine','Paul Roberts Wines.',1,0,16,8,10),(15,'Old greek coin','Found on seaside.',1,0,22,7,13),(16,'Convertible car','Generic',1,1,12,18,8),(17,'Sedan car.','Unknown japaneese model.',1,0,10,11,9),(18,'Belgian painting.','Edouard Agneessens 19 century.',1,0,3,7,4),(19,'Tang dynasty tomb figure','7 century.',1,1,4,7,5),(20,'Old indian sword','13 century.',1,0,24,15,11),(21,'Old turkish shield.','10 century.',1,1,24,13,12),(22,'Wheat option.','2020 april.',1,0,25,5,14);
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
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Данные аккаунта пользователя.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hj@ttvb.ll','administrator','9c22a2e5ffc0933e34bc311a1328088e',0,0.00,0,0.00),(2,'cardealer@gmail.com','justCarGuy','74019031f5136b917b955a260c1a02ab',1,0.00,0,0.00),(3,'artfan@gmail.com','bestPainting','d75dc436493cc9fe8763c5ae7ccb8a4c',1,0.00,0,0.00),(4,'hotwheel@gmail.com','hotWheelCarBuyer','2e12262dbdfeb60f33f0081cfd3ee69c',1,0.00,0,0.00),(5,'productsupplyco@gmail.com','wheetDealer','30938519df811f26077965e41f5c5def',1,0.00,0,0.00),(6,'wholefoods@gmail.com','WholeFoodsFoodBuyer','2f055054b509659a493c87986fc07018',1,0.00,0,0.00),(7,'fineArts@gmail.com','fineArts','bb7c12020080ad5a4214846531a9967a',1,0.00,0,0.00),(8,'wineLover@gmail.com','wineLover','81ab922eafb0b011e7d561ce24305755',1,0.00,0,0.00),(9,'oldbottle@gmail.com','oldBottleDealership','23a660bccfcf5c93fd1f364ae31c6306',1,0.00,0,0.00),(10,'goodcarguydealership@gmail.com','goodCarGuy','2d15620a37d19895375073c188c6129f',1,0.00,0,0.00),(11,'fastcarsco@gmail.com','FastCarsCo','21e35a3d73c142208980601fcfbd8a5d',1,0.00,0,0.00),(12,'ferraridealership@gmail.com','FerrariBY','03bf2f41512cfc8678ec65a0cde8dded',1,0.00,0,0.00),(13,'oldsword@gmail.com','OldSword','0233c3eae7fd9d68a4ac0e7cfdf46fd1',1,0.00,0,0.00),(14,'ancientshield@gmail.com','AncientShield','ca64afdf0a306307d408fb8da5c3681b',1,0.00,0,0.00),(15,'historyweapon@gmail.com','HistoryWeapon','8ed1ec194664598452ddc2285b56f3a0',1,0.00,0,0.00),(16,'camelotGuy@gmail.com','camelotGuy','cc7f88911f283a5659d8faf56d6e0220',1,0.00,0,0.00),(17,'antiqueco@gmail.com','AntiqueCo','60cce3f7c6ed0c7eaf83e8ad994f887c',1,0.00,0,0.00),(18,'familycardealership@gmail.com','FamilyCarDealership','b19a3be69e7e13d387ec6d9eb3a90aa8',1,0.00,0,0.00),(19,'businesscarco@gmail.com','BusinessCarCo','f2d677aca0a5af047e0ac3c3dae1dbfd',1,0.00,0,0.00),(20,'trucksupplymasters@gmail.com','TruckSupplyMasters','73205a7cc5db0c7ee846b74880e85e7d',1,0.00,0,0.00),(21,'customer@gmail.com','customer','91ec1f9324753048c0096d036a694f86',1,0.00,0,0.00),(23,'user1@gmail.com','user1','24c9e15e52afc47c225b757e7bee1f9d',1,0.00,0,0.00),(24,'user2@gmail.com','user2','7e58d63b60197ceb55a1c487989a3720',1,0.00,0,0.00),(25,'user3@gmail.com','user3','92877af70a45fd6a2ed7fe81e1236b78',1,0.00,0,0.00),(26,'user4@gmail.com','user4','3f02ebe3d7929b091e3d8ccfde2f3bc6',1,0.00,0,0.00),(27,'user7@gmail.com','user7','02f68ddc37ebb49d112f2c90b4c27557',1,0.00,0,0.00),(28,'user8@gmail.com','user8','7668f673d5669995175ef91b5d171945',1,0.00,0,0.00),(29,'user10@gmail.com','user10','990d67a9f94696b1abe2dccf06900322',1,0.00,0,0.00),(30,'user11@gmail.com','user11','03aa1a0b0375b0461c1b8f35b234e67a',1,0.00,0,0.00),(32,'user20@gmail.com','user20','10880c7f4e4209eeda79711e1ea1723e',1,0.00,0,0.00),(33,'user22@gmail.com','user22','87dc1e131a1369fdf8f1c824a6a62dff',1,0.00,0,0.00),(34,'justCarGuy1@gmail.com','justCarGuy1','2192b082cabcae608753b4719000d5be',1,0.00,0,0.00),(35,'justCarGuy8@gmail.com','justCarGuy8','74019031f5136b917b955a260c1a02ab',1,0.00,0,0.00),(36,'someAcc@gmail.com','someAcc','89c0596da0d539cfdbcf58a0bbec9ae4',1,0.00,0,0.00),(37,'justCarGuy123@gmail.com','justCarGuy123','455fc7a23a01fb94446ce27a301dc39a',1,0.00,0,0.00),(39,'add@eew.cl','user123','5b36dc07c952bd540383494ffac97596',1,0.00,0,0.00),(40,'user1234@gmail.com','user1234','4b4f19147c33ab623f7fefb38b226ee8',1,0.00,0,0.00),(41,'some@mail.com','user12345','874017ffc07bcc47fa31438a84d3c5e7',1,0.00,0,0.00),(42,'user987@gmail.com','user987','d0798313436c94e4d0078d218573b831',1,0.00,0,0.00),(48,'123456user@gmail.com','123456user','18e40c81d76fed14261bc99f6b4b830d',1,0.00,0,0.00),(52,'bjtgr789@gmail.com','yis537A','390df9ace3d0dcdd4809b3d358018d1a',1,0.00,0,0.00),(53,'someone12345','someone12345@gmail.com','4b4028775877b5ddc24b3c311333cbed',1,0.00,0,0.00),(55,'user123','user123@gmail.com','4b9b377b3d733246a3be19f74b3cc618',1,0.00,0,0.00),(60,'some123','some123@gmail.com','a0f8254b9d22911adf387509e25710a1',1,0.00,0,0.00),(61,'User99','user99@ge.co','6df1f6e5400cdea0ca9f01090d0ac5f6',1,0.00,0,0.00),(62,'User111','user111@ge.co','fe552e6f78b91b3036b346c7dda8616a',1,0.00,0,0.00),(65,'User1234','User1234@gmail.com','8432d89975adb4b0c7254d07548631f8',1,0.00,0,0.00),(77,'User010@gg.cc','User010','c220075838b613531a29fa320d728fc1',1,0.00,0,0.00),(78,'User012@gg.cc','User012','d54378d2ebdca1445e0c94badacdd7ea',1,0.00,0,0.00),(80,'SQLdolphin@gmail.com','SQLdolphin','6158d568e03818c9161904413418ed85',1,0.00,0,0.00),(81,'qwerty@gmail.com','qwerty','ec31e27fb022b50d99c36ebe1c32eeb1',1,0.00,0,0.00),(82,'somqwerty@gmail.com','somqwerty','fbbf5aee5223a43de0732f4ec161dec4',1,0.00,0,0.00),(83,'HarryPotter@hogsmith.com','HarryPotter','ba1793c8cbe7d291e3e583e4fc61e3e5',1,0.00,0,0.00),(84,'User1234@some.com','User1234','6edf26f6e0badff12fca32b16db38bf2',1,0.00,0,0.00),(85,'User453@gmail.com','User453','c5192e42fdf26817e86f0576f9f9dea0',1,0.00,0,0.00),(86,'somethingother@comby','Register','37d2906761348e762d0c44d869bb5d75',1,1000000001.84,0,0.00),(87,'MyLittlePony@gmail.com','Magician','2d72ef083e8c626dd84d225b9d694d29',1,0.00,0,0.00),(88,'amazing@gmail.com','amazing','45e590bfc9d2390967b3ed7c011943c8',1,1.03,0,0.00);
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

-- Dump completed on 2018-10-22 23:18:15
