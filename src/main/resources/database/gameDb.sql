-- MySQL dump 10.13  Distrib 9.5.0, for Win64 (x86_64)
--
-- Host: localhost    Database: game_db_test
-- ------------------------------------------------------
-- Server version	9.5.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '8b10d06a-aeeb-11f0-a9de-9c6b008b4e8c:1-4703';

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `number` int NOT NULL,
  `scene_id` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `status` enum('ARCHIVED','BETA','DRAFT','HIDDEN','PUBLISHED') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (1,'Здесь начинается сюжет. В \"Кафе Мечтателей\" ты можешь стать героем дня, выполняя поручения, или же попробовать на вкус запретный плод — бесплатный заказ.','cafeImage.jpg',1,'1','cafe','Кафешка любви и интересных встреч','PUBLISHED'),(2,'Хранилище тайн и заказов. В зависимости от твоей роли, ты либо обеспечиваешь порядок, либо ищешь, что можно \"случайно\" не найти. Мир WB ждет своего героя или злодея.','zooImage.jpg',2,'1','zoo','Зоопарк большого и малого счастья','PUBLISHED'),(3,'Среди диких зверей и любопытных посетителей скрываются задания, которые проверят твою смекалку и преданность выбранному пути. Будь осторожен, здесь даже животные могут быть не теми, кем кажутся.','wbImage.jpg',3,'1','vilainy','Логово злодейств и коварных планов','PUBLISHED'),(4,'Глава 4 дает возможность игрокам посетить закрытую локацию, работать в секретной огранизации и конечно познакомиться с новым гидом, который поможет им во всем! Статус: В разработке','noname.svg',4,'1','draftChapter4','Глава под номером 4','BETA');
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `effect_key` varchar(255) DEFAULT NULL,
  `effect_value` int DEFAULT NULL,
  `required_key` varchar(255) DEFAULT NULL,
  `required_min_value` int DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `scene_from_id` int DEFAULT NULL,
  `scene_to_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiq9kv0spqxbtvc22upjvu8xgd` (`scene_from_id`),
  KEY `FKsgs0o319jop6b0n82x15n5orx` (`scene_to_id`),
  CONSTRAINT `FKiq9kv0spqxbtvc22upjvu8xgd` FOREIGN KEY (`scene_from_id`) REFERENCES `scene` (`id`),
  CONSTRAINT `FKsgs0o319jop6b0n82x15n5orx` FOREIGN KEY (`scene_to_id`) REFERENCES `scene` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES (1,NULL,NULL,NULL,NULL,'Продолжить прохождение',1,2),(2,'relation_paimon',2,NULL,NULL,'Сделать комплимент',2,3),(3,'relation_paimon',1,NULL,NULL,'Пошутить',2,4),(4,NULL,NULL,NULL,NULL,'Извиниться за опоздание',2,5),(5,NULL,NULL,NULL,NULL,'Продолжить прохождение',3,6),(6,NULL,NULL,NULL,NULL,'Продолжить прохождение',4,6),(7,NULL,NULL,NULL,NULL,'Продолжить прохождение',5,6),(8,'relation_paimon',2,NULL,NULL,'Да, отличная идея!',6,7),(9,'relation_paimon',2,NULL,NULL,'Кушать много тоже вредно, особенно сладкого)',6,8),(10,'relation_paimon',-1,NULL,NULL,'Мы только пришли, давай хотя бы нагуляем аппетит?',6,9),(11,NULL,NULL,NULL,NULL,'Сделать заказ',8,7),(12,NULL,NULL,'relation_paimon',2,'Пошутить',9,12),(13,NULL,NULL,NULL,NULL,'Предложить пообщаться',9,14),(14,'relation_paimon',1,'relation_paimon',5,'Пошутить',10,12),(15,'relation_paimon',1,NULL,NULL,'Пожелать приятного аппетита!',10,13),(16,NULL,NULL,NULL,NULL,'Предложить пообщаться',10,14),(17,NULL,NULL,NULL,NULL,'Согласиться',11,14),(18,NULL,NULL,NULL,NULL,'Отказаться. Предложить заказать в баре 2 лимонада',11,15),(19,NULL,NULL,NULL,NULL,'Согласиться',12,14),(20,NULL,NULL,NULL,NULL,'Отказаться. Предложить заказать в баре 2 лимонада',12,15),(21,NULL,NULL,NULL,NULL,'Продолжить прохождение',13,14),(22,NULL,NULL,NULL,NULL,'Подойти к бару, чтобы заказать 2 лимонада',14,15),(23,NULL,NULL,NULL,NULL,'Продолжить прохождение',15,16),(24,NULL,NULL,NULL,NULL,'Да, конечно',16,17),(25,NULL,NULL,NULL,NULL,'Извини, это встреча только между мной и Паймин',16,18),(26,NULL,NULL,NULL,NULL,'Я тоже за',17,19),(27,NULL,NULL,NULL,NULL,'Без меня',17,20),(28,NULL,NULL,NULL,NULL,'Продолжить прохождение',19,21),(29,NULL,NULL,NULL,NULL,'Продолжить прохождение',20,22);
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dialog`
--

DROP TABLE IF EXISTS `dialog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dialog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `character_id` int DEFAULT NULL,
  `scene_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm8u474vvfaepuwk4i8km2idbu` (`character_id`),
  KEY `FK9ik627psrlh56mn2tmcm27egu` (`scene_id`),
  CONSTRAINT `FK9ik627psrlh56mn2tmcm27egu` FOREIGN KEY (`scene_id`) REFERENCES `scene` (`id`),
  CONSTRAINT `FKm8u474vvfaepuwk4i8km2idbu` FOREIGN KEY (`character_id`) REFERENCES `game_character` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dialog`
--

LOCK TABLES `dialog` WRITE;
/*!40000 ALTER TABLE `dialog` DISABLE KEYS */;
INSERT INTO `dialog` VALUES (1,'Добро пожаловать в 1 главу!',1,1),(2,'Вы идете в кафе на запланированную встречу с кое кем)',1,1),(3,'Помните! От вашего выбора зависит, как будут развиваться дальнейшие события и отношения к вам',1,1),(4,'Иногда даже маленький выбор может привести к большим последствиям',1,1),(5,'Удачи!',1,1),(6,'Вы заходите в небольшое уютное кафе.',1,2),(7,'В помещении играет тихая музыка.',1,2),(8,'У окна за столиком сидит Паймин. Перед ней стоит чашка кофе.',1,2),(9,'О, вот ты где, Паймин. Привет!',2,2),(10,'Привет!',3,2),(11,'Я уже начала думать, что ты не придешь',3,2),(12,'Ты так красива, что я просто не могу отвести взгляд',2,3),(13,'Ой… спасибо',3,3),(14,'Ты сегодня какой-то особенно милый',3,3),(15,'Я не мог пропустить встречу с человеком, который ест за двоих',2,4),(16,'Эй!',3,4),(17,'Я просто люблю вкусно покушать',3,4),(18,'Ты как всегда шутишь',3,4),(19,'Извини, кто-то поставил у меня перед входной дверью тумбу юмбу',2,5),(20,'Пришлось звать соседа, чтобы выбраться',2,5),(21,'Ничего страшного',3,5),(22,'Главное, что ты всё-таки пришёл',3,5),(23,'В кафе продолжает играть спокойная музыка.',1,6),(24,'За соседним столиком кто-то оживлённо обсуждает новости',1,6),(25,'Ты слышал? Вчера опять кто-то камеры закрыл',4,6),(26,'Да. Город словно с ума сошел',5,6),(27,'Ситуация в городе становится всё хуже',2,6),(28,'Да, я тоже это заметила',3,6),(29,'Вчера мне вообще окна заклеили газетами',2,6),(30,'Хотя я живу на седьмом этаже',2,6),(31,'Серьёзно?',3,6),(32,'У тебя есть догадки, кто это может быть?',3,6),(33,'Нет',2,6),(34,'Либо дети совсем распоясались… либо появилось какое-то новое движение',2,6),(35,'И, возможно, это даже не одно и то же',2,6),(36,'Мда…',3,6),(37,'Слишком много мыслей',3,6),(38,'Давай лучше что-нибудь закажем?',3,6),(39,'Вредный ты',3,8),(40,'Есть в кого',2,8),(41,'Кстати…',3,8),(42,'Я бы хотела сырники',3,8),(43,'Закажешь?',3,8),(44,'Эх…',3,9),(45,'Я вообще-то весь день ничего не ела.',3,9),(46,'Что предлагаешь делать?',3,9),(47,'Вот твои сырники',2,10),(48,'Ооо!',3,10),(49,'Ты заказал сырники!',3,10),(50,'Спасибо большое!',3,10),(51,'Пожалуйста',2,10),(52,'Без сырников',2,11),(53,'Эх…',3,11),(54,'Сегодня без сырников',3,11),(55,'Ну ладно',3,11),(56,'Давай тогда просто пообщаемся?',3,11),(57,'Твой взгляд на десертную карту — самая искренняя любовь, которую я видел',2,12),(58,'Ревнуешь?',3,12),(59,'Не переживай',3,12),(60,'Чизкейк — это просто интрижка',3,12),(61,'Моё сердце всё ещё принадлежит… основному блюду',3,12),(62,'Хочешь ещё пообщаться',3,12),(63,'Приятного аппетита, Паймин!',2,13),(64,'Спасибо, и тебе приятного!',3,13),(65,'Как проходят дни?',2,14),(66,'Хорошо, у тебя как?',3,14),(67,'Ну.., в общем и целом пойдет',2,14),(68,'Так. Что случилось?',3,14),(69,'Да бесконечный хаос в городе уже начинает действовать очень сильно не нервы',2,14),(70,'Хоть и стараюсь игнорировать и не вспоминать об этом, но это становится почти невозможно',2,14),(71,'Понимаю, но что еще поделать можно? У полиции нет зацепок или свидетелей, чтобы поймать нарушителя порядка',3,14),(72,'Так что надо ждать чудо',3,14),(73,'Судя по всему именно так и есть',2,14),(74,'Паймин, пошли за лимонадами?',2,15),(75,'Да, давай',3,15),(76,'Вы подошли к бару',1,15),(77,'Здравствуйте. Нам 2 лимонада, пожалуйста',2,15),(78,'Здравствуйте. Один момент',8,15),(79,'Держите ваши лимонады',8,15),(80,'Спасибо',2,15),(81,'Это ваша коробка?',8,15),(82,'Какая?',2,15),(83,'Наверное речь про эту коробку, та, что слева на краю стола',3,15),(84,'Нет, не наша',2,15),(85,'Хм, это уже 5 раз за последние 2 дня, когда в кафе появляются вещи, которые никому не принадлежат',8,15),(86,'Я уже честно говоря устал от этого',8,15),(87,'Витя, а что на камерах?',3,15),(88,'Бармен, мне 3 ягодных мохито, пожалуйста',9,15),(89,'Извините, что прервал вашу беседу',9,15),(90,'Ничего страшного',2,15),(91,'Вот ваши 3 ягодных мохито',8,15),(92,'Благодарю',9,15),(93,'В том то и дело, что на камерах пусто. Кто-то закрывает камеры, поэтому найти виновника не получается',8,15),(94,'Давайте посмотри, что в коробке?',2,15),(95,'Да, думаю это неплохое предложение',3,15),(96,'Я говорю тебе, это какой-то флешмоб',10,15),(97,'Флешмоб? Люди воруют корм из зоопарка',11,15),(98,'Окей, давайте посмотрим',8,15),(99,'В коробке находится лист',1,15),(100,'На листе написано:',1,15),(101,'Фаза «Слепой орёл» — завершена',1,15),(102,'Начать фазу 2',1,15),(103,'Что это значит?',3,15),(104,'Не знаю, но не думаю, что это что-то хорошее',8,15),(105,'Согласен',2,15),(106,'Паймин, пошли за наш столик',2,15),(107,'Мы наверное пойдем. До свидания',2,16),(108,'До свидания',8,16),(109,'Вы вернулись за столик',1,16),(110,'В кафе приходит Марк',1,16),(111,'Оо, Марк, привет!',3,16),(112,'Ого, привет, Паймин',12,16),(113,'Вова, ты задолбал, доделай миграции в проекте',6,16),(114,'Нету ручки - нет конфетки',7,16),(115,'Привет игрок',12,16),(116,'Привет, Марк',2,16),(117,'Можно я к вам присяду?',12,16),(118,'Да, конечно. Возьми стул, а то тут только 2 места',2,17),(119,'Момент',12,17),(120,'Марк нашел стул и сел вместе с вами',1,17),(121,'Как у тебя дела?',3,17),(122,'Если не считать работу, то в целом хорошо',12,17),(123,'А что случилось на работе?',3,17),(124,'Какая-то анархия творится. Сейчас же в городе черт ногу сломит, соответственно и у нас такая же проблема',12,17),(125,'Вчера таблички животных поменяли местами и поставили еще даже с описанием несуществующих животных',12,17),(126,'Ужас',2,17),(127,'Согласна, бардак какой-то',3,17),(128,'Сегодня так вообще корм украли, пришлось экстренно искать замену, чтобы животные не голодали',12,17),(129,'Нет слов, совсем офигели',3,17),(130,'Кошмар',2,17),(131,'На камерах я так полагаю, что ничего нет. Я прав?',2,17),(132,'Да, камеры закрывают на время, когда происходит вандализм',12,17),(133,'В кафешке такая же проблема',2,17),(134,'Может с этим можно что-то сделать?',3,17),(135,'Какие у тебя есть идеи?',2,17),(136,'Ну, может самим разобраться в причинах дестабилизации города?',3,17),(137,'Идея интересная, но мы же не супер герои и не знаем, кто стоит по ту сторону',12,17),(138,'Почему бы просто не выяснить, а после передать всю информацию в полицию?',2,17),(139,'Хорошая идея',12,17),(140,'Я согласна',3,17),(141,'Возможно, что завтра также будет у нас проблемы в зоопарке. Можете завтра прийти и последить, поискать причину',12,17),(142,'У меня хорошие отношения с начальством, я могу попробовать выпросить для вас доступ к рациям зоопарка, чтобы разобраться в этом',12,17),(143,'Я только за. Приключения - это хорошо',3,17),(144,'Извини, это встреча только между мной и Паймин',2,18),(145,'Оу, извини. Пока в таком случае',12,18),(146,'Марк заказал булочку и кофе, после покинул кафешку',1,18),(147,'Ты был груб',3,18),(148,'Извини, но я хотел провести время с тобой',2,18),(149,'Марк хороший друг, он мог составить нам компанию',3,18),(150,'А ты…',3,18),(151,'Я пожалуй пойду. Пока',3,18),(152,'Игра завершена! Неверный выбор',1,18),(153,'Я тоже за. Один за всех и все за одного',2,19),(154,'Отлично!',12,19),(155,'Тогда давайте попробуем обговорить план',12,19),(156,'Какие есть предложения?',3,19),(157,'У меня есть пока такой план',12,19),(158,'Вы завтра наряжаетесь как обычные посетители, не выделяетесь из общего потока',12,19),(159,'Я вам дам рации и будем держать связь все вместе. Возможно, что нам поможет мой друг, он работает в охране зоопарка',12,19),(160,'План такой. Если где-то закрывают камеру, то Олег вас туда отправит, Олег - это мой друг охранник',12,19),(161,'Только надо тихо действовать',12,19),(162,'Хорошо. Тогда до завтра!',2,19),(163,'Да, уже пора расходится. Пока, Марк!',3,19),(164,'Мне тоже пора. Пока!',12,19),(165,'Марк заказывает булочку и кофе, после уходит домой',1,19),(166,'Поздравляем! Вы прошли 1 главу',1,19),(167,'Оу, подождите. Тут оказывается есть финальные титры для 1 главы',1,19),(168,'Знаете, я пожалуй откажусь',2,20),(169,'Не уверен теперь, что это хорошая идея',2,20),(170,'Ты чего?',3,20),(171,'Мы не знаем на что идем. Это может быть опасно для жизни',2,20),(172,'Согласен, но мы же не собираемся их ловить. Просто узнать кто этим занимается и сообщить в полицию',2,20),(173,'Я все равно пожалуй воздержусь. Желаю удачи!',2,20),(174,'Отлично. Первая фаза завершена',13,21),(175,'Можно приступать ко 2 фазе',13,21),(176,'Никто мне не помешает. Хахах',13,21);
/*!40000 ALTER TABLE `dialog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category` enum('FOOD','DRINK','DESSERT') NOT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'Сырники','FOOD',250),(2,'Блинчики со сгущенкой','DESSERT',500),(3,'Цезарь','FOOD',350),(4,'Крабовый салат','FOOD',200),(5,'Анна Павлова','DESSERT',600),(6,'Борщ','FOOD',300),(7,'Катлетосы','FOOD',400),(8,'Мармелад','DESSERT',300),(9,'Кофе','DRINK',1800),(10,'Чай','DRINK',200),(11,'Картошка мятка','FOOD',200),(12,'Картошка фри','FOOD',250),(13,'Рыбный шашлык','FOOD',400),(14,'Мятное желе','DESSERT',399),(15,'Нефритовые мешочки','FOOD',650),(16,'Шашлык с грибами','FOOD',700),(17,'Липтон','DRINK',100),(18,'Coca Cola','DRINK',150),(19,'Грибочки','FOOD',300),(20,'Заварной крем из роз','DESSERT',1200),(21,'Ягодный мидзу мандзю','DESSERT',1000),(22,'Ароматные мясные шарики','FOOD',600),(23,'Суп Де Пуассон','FOOD',800),(24,'Тортик Дебор','DESSERT',5000),(25,'Горы, море, небо','FOOD',550),(26,'Бирьяни','FOOD',500),(27,'Миндальный тофу','DESSERT',400),(28,'Наполеон','DESSERT',850),(29,'Якисоба','FOOD',700);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `role` enum('EMPLOYEE','SECURITY_GUARD','MANAGER','ADMINISTRATOR','POLICE_OFFICER','OWNER','GOD') DEFAULT NULL,
  `salary` int DEFAULT NULL,
  `bonus` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Иван','ivanpetrov','password123','ivanpetrov@company.com','+79161234567','EMPLOYEE',50000,5000),(2,'Мария','mariasidorova','password123','mariasidorova@company.com','+79161234568','EMPLOYEE',52000,5500),(3,'Алексей','alexeykozlov','password123','alexeykozlov@company.com','+79161234569','SECURITY_GUARD',45000,3000),(4,'Ольга','olgaivanova','password123','olgaivanova@company.com','+79161234570','MANAGER',80000,15000),(5,'Дмитрий','dmitrysmirnov','password123','dmitrysmirnov@company.com','+79161234571','ADMINISTRATOR',90000,20000),(6,'Сергей','sergeypopov','password123','sergeypopov@company.com','+79161234572','POLICE_OFFICER',60000,8000),(7,'Анна','annakuznetsova','password123','annakuznetsova@company.com','+79161234573','EMPLOYEE',48000,4000),(8,'Елена','elenavolkova','password123','elenavolkova@company.com','+79161234574','MANAGER',85000,16000),(9,'Павел','pavelmorozov','password123','pavelmorozov@company.com','+79161234575','SECURITY_GUARD',46000,3200),(10,'Наталья','natalyaorlova','password123','natalyaorlova@company.com','+79161234576','EMPLOYEE',51000,5200),(11,'Андрей','andreybelov','password123','andreybelov@company.com','+79161234577','ADMINISTRATOR',95000,22000),(12,'Татьяна','tatyanaegorova','password123','tatyanaegorova@company.com','+79161234578','EMPLOYEE',49000,4500),(13,'Михаил','mikhailkomarov','password123','mikhailkomarov@company.com','+79161234579','POLICE_OFFICER',62000,8500),(14,'Светлана','svetlanasokolova','password123','svetlanasokolova@company.com','+79161234580','MANAGER',82000,14000),(15,'Артем','artemfedorov','password123','artemfedorov@company.com','+79161234581','EMPLOYEE',47000,3800),(16,'Юлия','yuliamikhailova','password123','yuliamikhailova@company.com','+79161234582','SECURITY_GUARD',44000,2800),(17,'Роман','romannovikov','password123','romannovikov@company.com','+79161234583','EMPLOYEE',53000,5800),(18,'Екатерина','ekaterinapavlova','password123','ekaterinapavlova@company.com','+79161234584','ADMINISTRATOR',92000,21000),(19,'Виктор','viktorzaytsev','password123','viktorzaytsev@company.com','+79161234585','POLICE_OFFICER',61000,7800),(20,'Людмила','lyudmilavorobyeva','password123','lyudmilavorobyeva@company.com','+79161234586','EMPLOYEE',54000,6000),(21,'Настенька','nastyaLove781','password123','nastya9754@gmail.com','+798735293','MANAGER',50000,12000),(22,'Дмитрий','Dmitry','password123','Dmitry@gmail.com','78751147579','SECURITY_GUARD',35000,6000),(23,'Александр','aleksandr','password123','aleksandr8@gmail.com','7 938 564 88 13','MANAGER',70000,13000);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_character`
--

DROP TABLE IF EXISTS `game_character`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_character` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` enum('ARCHIVED','BETA','DRAFT','HIDDEN','PUBLISHED') DEFAULT NULL,
  `type` enum('GUIDE','NPC','PLAYER','SYSTEM') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_character`
--

LOCK TABLES `game_character` WRITE;
/*!40000 ALTER TABLE `game_character` DISABLE KEYS */;
INSERT INTO `game_character` VALUES (1,'Системные уведомления',NULL,'System','PUBLISHED','SYSTEM'),(2,'Главный герой',NULL,'Игрок','PUBLISHED','PLAYER'),(3,'Спутница',NULL,'Паймин','PUBLISHED','GUIDE'),(4,'Прохожий',NULL,'Горожанин 1','PUBLISHED','NPC'),(5,'Прохожий',NULL,'Горожанин 2','PUBLISHED','NPC'),(6,'Странный гость',NULL,'Человек Дыня','PUBLISHED','NPC'),(7,'Разработчик?',NULL,'Вова','PUBLISHED','NPC'),(8,'Работник кафе',NULL,'Бармен Витя','PUBLISHED','NPC'),(9,'Посетитель / Охранник',NULL,'Олег','PUBLISHED','NPC'),(10,'Сплетник',NULL,'Горожанин 3','PUBLISHED','NPC'),(11,'Сплетник',NULL,'Горожанин 4','PUBLISHED','NPC'),(12,'Друг из зоопарка',NULL,'Марк','PUBLISHED','NPC'),(13,'???',NULL,'Таинственный голос','PUBLISHED','NPC');
/*!40000 ALTER TABLE `game_character` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_support`
--

DROP TABLE IF EXISTS `message_support`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_support` (
  `id` int NOT NULL AUTO_INCREMENT,
  `answer` text,
  `date` date NOT NULL,
  `message` text NOT NULL,
  `status` enum('CLOSED','IN_PROGRESS','NEW','REJECTED') NOT NULL,
  `administrator_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh3d8yjwh0oinljfub1s7r2og8` (`administrator_id`),
  KEY `FKjlgh48bvjqsgg3p8pu7urxdoq` (`user_id`),
  CONSTRAINT `FKh3d8yjwh0oinljfub1s7r2og8` FOREIGN KEY (`administrator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjlgh48bvjqsgg3p8pu7urxdoq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_support`
--

LOCK TABLES `message_support` WRITE;
/*!40000 ALTER TABLE `message_support` DISABLE KEYS */;
INSERT INTO `message_support` VALUES (1,'Test','2026-02-14','Test 1. Support message','CLOSED',2,16),(2,'Test 2','2026-02-14','Test 2. Support message','IN_PROGRESS',2,16),(3,'Test 3','2026-02-14','Test 3. Support message','CLOSED',16,16),(4,NULL,'2026-02-26','Проверка работы системы! \nНе отвечать на обращение!','NEW',NULL,2),(5,'Администрация активно ведёт работу по разработке игрового контента, ожидается, что сюжет станет доступен уже на этой или следующей недели. \nМодерация добавлена, чтобы Администрация не отвлекалась на ответы пользователям в поддержке. \n\nС уважением команда Модерации проекта RoleMaster! ❤','2026-02-26','Когда будет реализованы главы игры? Слухи есть, Модерация есть, а игры нет. ','CLOSED',16,3),(6,'Без ответа!','2026-03-11','Проверка работы системы!','REJECTED',2,16);
/*!40000 ALTER TABLE `message_support` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `dish_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `dish_id` (`dish_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `user_id` int NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'6b2e7d5b-f253-4eaf-bdb1-4a5b1e2628ed',1,7250.00),(3,'0ccb3d61-ab8a-4cb4-b2f8-7ddcab87e030',3,350.00),(4,'a3368763-4e95-4020-93e5-332636ee8733',2,0.00);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `addressee` varchar(50) NOT NULL,
  `user_id` int NOT NULL,
  `uuid` varchar(50) NOT NULL,
  `price` int NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `package_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `package_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (12,'Иван',3,'a1b2c3d4-e5f6-7890-abcd-ef1234567890',28990,11),(14,'Алексей',4,'c3d4e5f6-g7h8-9012-cdef-345678901234',3290,14),(15,'Ольга',5,'d4e5f6g7-h8i9-0123-defg-456789012345',18990,16),(16,'Дмитрий',6,'e5f6g7h8-i9j0-1234-efgh-567890123456',4590,18),(17,'Анна',7,'f6g7h8i9-j0k1-2345-fghi-678901234567',890,21),(18,'Сергей',8,'g7h8i9j0-k1l2-3456-ghij-789012345678',450,22),(19,'Елена',9,'h8i9j0k1-l2m3-4567-hijk-890123456789',190,24),(20,'Павел',10,'i9j0k1l2-m3n4-5678-ijkl-901234567890',690,26),(21,'Наталья',11,'j0k1l2m3-n4o5-6789-jklm-012345678901',90,28),(22,'Андрей',12,'k1l2m3n4-o5p6-7890-klmn-123456789012',590,30),(23,'Татьяна',13,'l2m3n4o5-p6q7-8901-lmno-234567890123',750,32);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category` enum('COSMETICS','TECHNIQUE','FOOD','OTHERS') NOT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Тональный крем L\'Oreal premium','COSMETICS',850),(2,'Помада Maybelline SuperStay','COSMETICS',650),(3,'Тушь для ресниц Volume Express','COSMETICS',560),(4,'Пудра компактная Pupa','COSMETICS',730),(5,'Крем для лица Nivea','COSMETICS',460),(6,'Шампунь Head & Shoulders','COSMETICS',390),(7,'Гель для душа Dove','COSMETICS',300),(8,'Дезодорант Rexona','COSMETICS',250),(9,'Крем для рук Neutrogena','COSMETICS',420),(10,'Лак для ногтей Essie','COSMETICS',380),(11,'Смартфон Samsung Galaxy A54','TECHNIQUE',28990),(12,'Наушники Apple AirPods Pro','TECHNIQUE',22000),(13,'Ноутбук ASUS VivoBook','TECHNIQUE',54990),(14,'Умные часы Xiaomi Mi Band','TECHNIQUE',3290),(15,'Фитнес-браслет Huawei Band','TECHNIQUE',4590),(16,'Планшет Lenovo Tab','TECHNIQUE',18990),(17,'Электронная книга PocketBook','TECHNIQUE',8990),(18,'Игровая мышь Logitech','TECHNIQUE',4590),(19,'Клавиатура механическая','TECHNIQUE',6290),(20,'Внешний жесткий диск Seagate','TECHNIQUE',7890),(21,'Кофе в зернах Jacobs','FOOD',890),(22,'Чай зеленый Ahmad','FOOD',450),(23,'Шоколад молочный Alpen Gold','FOOD',130),(24,'Печенье Oreo','FOOD',190),(25,'Макароны Barilla','FOOD',240),(26,'Оливковое масло Borges','FOOD',690),(27,'Сыр российский','FOOD',460),(28,'Йогурт питьевой Danone','FOOD',90),(29,'Сок яблочный J7','FOOD',150),(30,'Колбаса докторская','FOOD',590),(31,'Набор кухонных полотенец','OTHERS',890),(32,'Цветочный горшок керамический','OTHERS',750),(33,'Зонт автоматический','OTHERS',1290),(34,'Фоторамка деревянная','OTHERS',450),(35,'Ежедневник кожаный','OTHERS',1200),(36,'Набор отверток','OTHERS',890),(37,'Скакалка спортивная','OTHERS',300),(38,'Свечи ароматические','OTHERS',590),(39,'Держатель для зубных щеток','OTHERS',350),(40,'Подставка для ножей','OTHERS',1290),(41,'Губная помада','COSMETICS',210),(42,'Мармеладки','FOOD',400),(43,'Принтер','TECHNIQUE',8600),(44,'Смартфон Samsung S26 Ultra','TECHNIQUE',7900);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `expired_at` datetime(6) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(512) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKghpmfn23vmxfu3spu3lfg4r2d` (`token`),
  KEY `FK1lih5y2npsf8u5o3vhdb9y0os` (`user_id`),
  CONSTRAINT `FK1lih5y2npsf8u5o3vhdb9y0os` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
INSERT INTO `refresh_tokens` VALUES (2,'2026-03-12 00:47:19.546242','2026-04-11 00:47:19.546242',_binary '','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYW5pbCIsImlhdCI6MTc3MzI2NTYzOSwiZXhwIjoxNzc1ODU3NjM5fQ.H5jxzEbt955aR0glEnYEzEubM-903zkLei-AL3fYa1A',2),(3,'2026-03-12 00:53:22.202139','2026-04-11 00:53:22.202139',_binary '','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYW5pbCIsImlhdCI6MTc3MzI2NjAwMiwiZXhwIjoxNzc1ODU4MDAyfQ.AhiN36lBkT4-FzhcJO021wTzxIgWpJU0jdmNKymuMSM',2),(4,'2026-03-12 00:55:09.403521','2026-04-11 00:55:09.403521',_binary '','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYW5pbCIsImlhdCI6MTc3MzI2NjEwOSwiZXhwIjoxNzc1ODU4MTA5fQ.ULOQ36X9FC6Ry8NEUXnuV-0Zx_J08K_AdSiudGCFdmU',2),(5,'2026-03-12 00:55:26.962661','2026-04-11 00:55:26.962661',_binary '','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZXIyIiwiaWF0IjoxNzczMjY2MTI2LCJleHAiOjE3NzU4NTgxMjZ9.BxoVdOYdje2hIoWE2FCgeKV7w4DbURW3xaSstAqmCak',17),(6,'2026-03-12 00:55:33.968308','2026-04-11 00:55:33.968308',_binary '','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYW5pbCIsImlhdCI6MTc3MzI2NjEzMywiZXhwIjoxNzc1ODU4MTMzfQ.dnQwp50An61-0htHFrsRJAP1jHaB-Jn0RCXlnDkL_Jo',2),(7,'2026-03-12 16:24:13.778728','2026-04-11 16:24:13.778728',_binary '\0','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbG93ZTE3IiwiaWF0IjoxNzczMzIxODUzLCJleHAiOjE3NzU5MTM4NTN9.j997nkozWIs4KbO6m8kILssMSCpSp9uHrEn_gQKqpw8',16),(8,'2026-03-15 13:31:37.006180','2026-04-14 13:31:37.006180',_binary '\0','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbG93ZTE3IiwiaWF0IjoxNzczNTcwNjk3LCJleHAiOjE3NzYxNjI2OTd9.7R9DiSCV_MH57o664CkIGZo0ElrhOwPqnVKal1g8zmA',16);
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scene`
--

DROP TABLE IF EXISTS `scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scene` (
  `id` int NOT NULL AUTO_INCREMENT,
  `scene_id` varchar(255) DEFAULT NULL,
  `scene_type` enum('DEFEAT','DIALOGS','EVENT','MENU','NEXTCHAPTER','REWARDS') DEFAULT NULL,
  `chapter_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjen64rvkpgto3w5ewode5myj3` (`chapter_id`),
  CONSTRAINT `FKjen64rvkpgto3w5ewode5myj3` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scene`
--

LOCK TABLES `scene` WRITE;
/*!40000 ALTER TABLE `scene` DISABLE KEYS */;
INSERT INTO `scene` VALUES (1,'ch1_s1_start','DIALOGS',1),(2,'ch1_s2','DIALOGS',1),(3,'ch1_s3','DIALOGS',1),(4,'ch1_s4','DIALOGS',1),(5,'ch1_s5','DIALOGS',1),(6,'ch1_s6','DIALOGS',1),(7,'ch1_s7','MENU',1),(8,'ch1_s8','DIALOGS',1),(9,'ch1_s9','DIALOGS',1),(10,'ch1_s10','DIALOGS',1),(11,'ch1_s11','DIALOGS',1),(12,'ch1_s12','DIALOGS',1),(13,'ch1_s13','DIALOGS',1),(14,'ch1_s14','DIALOGS',1),(15,'ch1_s15','DIALOGS',1),(16,'ch1_s16','DIALOGS',1),(17,'ch1_s17','DIALOGS',1),(18,'ch1_s18','DEFEAT',1),(19,'ch1_s19','DIALOGS',1),(20,'ch1_s20','DIALOGS',1),(21,'ch1_s21','NEXTCHAPTER',1),(22,'ch1_s22','NEXTCHAPTER',1);
/*!40000 ALTER TABLE `scene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_game_attribute`
--

DROP TABLE IF EXISTS `user_game_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_game_attribute` (
  `id` int NOT NULL AUTO_INCREMENT,
  `effect_key` varchar(255) NOT NULL,
  `effect_value` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmad2cj692l1ya2trdeyauorvx` (`user_id`),
  CONSTRAINT `FKmad2cj692l1ya2trdeyauorvx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_game_attribute`
--

LOCK TABLES `user_game_attribute` WRITE;
/*!40000 ALTER TABLE `user_game_attribute` DISABLE KEYS */;
INSERT INTO `user_game_attribute` VALUES (1,'relation_paimon',0,16);
/*!40000 ALTER TABLE `user_game_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_game_state`
--

DROP TABLE IF EXISTS `user_game_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_game_state` (
  `id` int NOT NULL AUTO_INCREMENT,
  `current_scene_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtigmbcqdwv24u43s2b80qxjid` (`user_id`),
  KEY `FKr4g9wum1eosl0fdm6o9qolfmx` (`current_scene_id`),
  CONSTRAINT `FKexe1twtcb9mg8n8v17lpw9t8t` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKr4g9wum1eosl0fdm6o9qolfmx` FOREIGN KEY (`current_scene_id`) REFERENCES `scene` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_game_state`
--

LOCK TABLES `user_game_state` WRITE;
/*!40000 ALTER TABLE `user_game_state` DISABLE KEYS */;
INSERT INTO `user_game_state` VALUES (1,15,16);
/*!40000 ALTER TABLE `user_game_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_progress`
--

DROP TABLE IF EXISTS `user_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_progress` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_passage` datetime(6) NOT NULL,
  `chapter_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo12le3eddsmnvr79aikfq6cas` (`chapter_id`),
  KEY `FKrt37sneeps21829cuqetjm5ye` (`user_id`),
  CONSTRAINT `FKo12le3eddsmnvr79aikfq6cas` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`),
  CONSTRAINT `FKrt37sneeps21829cuqetjm5ye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_progress`
--

LOCK TABLES `user_progress` WRITE;
/*!40000 ALTER TABLE `user_progress` DISABLE KEYS */;
INSERT INTO `user_progress` VALUES (2,'2026-02-04 19:16:00.000000',1,2),(3,'2026-02-04 19:16:00.000000',1,16),(4,'2026-09-02 14:43:00.000000',1,15),(5,'2026-02-09 20:39:37.437086',1,17),(6,'2026-02-14 20:16:00.000000',1,1),(7,'2026-02-10 20:16:00.000000',1,3),(8,'2026-02-12 20:16:00.000000',1,4),(9,'2026-02-11 20:16:00.000000',1,5),(10,'2026-02-11 20:16:00.000000',1,6),(11,'2026-02-13 20:16:00.000000',1,7),(12,'2026-02-14 20:16:00.000000',1,8),(13,'2026-02-09 20:16:00.000000',1,9),(14,'2026-02-10 20:16:00.000000',1,10),(15,'2026-02-10 20:16:00.000000',1,11),(16,'2026-02-14 20:16:00.000000',1,12),(17,'2026-02-14 20:16:00.000000',1,13),(18,'2026-02-13 20:16:00.000000',1,14);
/*!40000 ALTER TABLE `user_progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `role` enum('ADMINISTRATOR','MODERATOR','NARRATIVEDESIGNER','TESTER','PREMIUM_USER','PLAYER') DEFAULT NULL,
  `balance` int NOT NULL DEFAULT '100',
  `progress_level` int DEFAULT '1',
  `progress_xp` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Настя','nastya736','$2a$10$lH6foiLwTnx0FAV9XnHjHeO1ynGRGW1YlVt.i54r78M9lKo89WLcy','nastya9754@gmail.com','+798735293','2003-01-04','PLAYER',3750,1,0),(2,'Данечка<3','Danil','$2a$10$GIb1RXE0VBNjL34tanlqW.un9KGkwx0pkS6BUQ86XHXXlIohBJtYy','danil@gamil.com','+7746783632','2007-10-05','ADMINISTRATOR',-100,1,0),(3,'Test','Test','$2a$10$JYP.7G1Q476kfQCXL9zpOeWAGFoxHfQYFovMLxdmQuNGn4ft5GnXu','test@gmail.com','+777777778','2007-10-05','PLAYER',894800,1,0),(4,'Александр','alexandrivanov','$2a$10$0qBcxIrLZdTQgqCU/JhMre8DOvb6rhSjBjG5iJ2VP4mbk.yZlWDuG','alexandrivanov@mail.com','+79161111111','1990-05-15','PLAYER',1000,1,0),(5,'Екатерина','ekaterinapetrova','$2a$10$fxTGTcCqWxcmm5qVB4VEw.q7zR7r0c0VCw2pah1M9jUvbIrJBEs6.','ekaterinapetrova@mail.com','+79161111112','1988-08-22','PLAYER',2500,1,0),(6,'Максим','maximsidorov','$2a$10$aZkyyS29nAJAdvv73c0oIekG63JiaCMDe1HHb75xokzwCH3lt6LmK','maximsidorov@mail.com','+79161111113','1995-03-10','PLAYER',500,1,0),(7,'Анна','annasmirnova','$2a$10$6HUNn0PthqmoDXhjJzS2Xe2md8zyVoNT6yvHzGTOeDxcFonExdm1.','annasmirnova@mail.com','+79161111114','1992-11-30','PLAYER',2800,1,0),(8,'Денис','deniskuznetsov','$2a$10$Q2Q55jmE72Vc31Ixvqg4KeUYAzVhHCo/xSDkL4IT0liiKqGN80nOS','deniskuznetsov@mail.com','+79161111115','1987-07-18','PLAYER',800,1,0),(9,'Ольга','olgapopova','$2a$10$pRgu80MI65kSdccUKuAggeumV1Gk/7hKAge.R5jvtQ8d9qJynMj92','olgapopova@mail.com','+79161111116','1993-12-05','PLAYER',4200,1,0),(10,'Артем','artemvolkov','$2a$10$BaEXeuvfbHaq0MM2YZ7kG.4Aw3zzNA5nAf3gFA8ailVY5NWaxul4q','artemvolkov@mail.com','+79161111117','1991-09-14','PLAYER',1200,1,0),(11,'Ирина','irinazorina','$2a$10$limz.qiafIjKImHVp82WQOYfyRABROxdHLdl3eeGKVl802cOqaFnm','irinazorina@mail.com','+79161111118','1989-06-25','PLAYER',2900,1,0),(12,'Сергей','sergeyfedorov','$2a$10$69BuzDmwP2wzpAkqRId22uFLAVD.hxtrZek43nTb97cKC/VUGvSNK','sergeyfedorov@mail.com','+79161111119','1994-02-08','PLAYER',600,1,0),(13,'Наталья','natalyamuravyeva','$2a$10$APyR32.B/6yzFBM4hho9duItCemlquXMtaxT0WujAultDGWTaC2M2','natalyamuravyeva@mail.com','+79161111120','1996-04-12','PLAYER',2700,1,0),(14,'Administrator','admin','$2a$10$fx4Gt/pONc94I4Ohh884O.FLSPsihf6h7WhoWx/1YHWIHkozI8hPS','admin@gmail.com','+00000000','2007-10-05','ADMINISTRATOR',100,1,0),(15,'Tester1','tester1','$2a$10$cENLaBMLVu08NoS0twkLUO8rMCSxnPVozwnkuBCnpNElbVV/sjOOC','tester1@gmail.com','8748395','2025-12-02','PLAYER',100,1,0),(16,'Даня','alowe17','$2a$10$5WyvPg/TeVXzKAa.CoJWhO2rWAQjsy4HLyuo8WNCzkEuODMz7qtqK','moderator@gmail.com','75124124167','2007-10-05','MODERATOR',1700,1,0),(17,'test2','tester2','$2a$10$a.ILeOf7RP2YliUuHuKteu0orZmwDUn1./7a3mlGXCqf.Acst3rru','test2@gmail.com','8-998-702-67-55','2026-02-02','PLAYER',100,0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-17 11:05:16
