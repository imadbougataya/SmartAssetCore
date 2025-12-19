-- MySQL dump 10.13  Distrib 9.5.0, for macos14.8 (x86_64)
--
-- Host: localhost    Database: SmartAssetCore
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

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'e4f41f98-24ee-11f0-aaee-c669be7c338a:1-322';

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `asset_type` varchar(255) NOT NULL,
  `asset_code` varchar(80) NOT NULL,
  `reference` varchar(120) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `criticality` varchar(255) NOT NULL,
  `responsible_name` varchar(120) DEFAULT NULL,
  `cost_center` varchar(80) DEFAULT NULL,
  `brand` varchar(80) DEFAULT NULL,
  `model` varchar(120) DEFAULT NULL,
  `serial_number` varchar(120) DEFAULT NULL,
  `power_kw` decimal(21,2) DEFAULT NULL,
  `voltage_v` decimal(21,2) DEFAULT NULL,
  `current_a` decimal(21,2) DEFAULT NULL,
  `cos_phi` decimal(21,2) DEFAULT NULL,
  `speed_rpm` int DEFAULT NULL,
  `ip_rating` varchar(20) DEFAULT NULL,
  `insulation_class` varchar(30) DEFAULT NULL,
  `mounting_type` varchar(255) DEFAULT NULL,
  `shaft_diameter_mm` decimal(21,2) DEFAULT NULL,
  `foot_distance_amm` decimal(21,2) DEFAULT NULL,
  `foot_distance_bmm` decimal(21,2) DEFAULT NULL,
  `front_flange_mm` decimal(21,2) DEFAULT NULL,
  `rear_flange_mm` decimal(21,2) DEFAULT NULL,
  `iec_axis_height_mm` decimal(21,2) DEFAULT NULL,
  `dimensions_source` varchar(120) DEFAULT NULL,
  `has_heating` tinyint DEFAULT NULL,
  `temperature_probe_type` varchar(255) DEFAULT NULL,
  `last_commissioning_date` date DEFAULT NULL,
  `last_maintenance_date` date DEFAULT NULL,
  `maintenance_count` int DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `site_id` bigint DEFAULT NULL,
  `production_line_id` bigint DEFAULT NULL,
  `current_zone_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_asset__asset_code` (`asset_code`),
  KEY `fk_asset__site_id` (`site_id`),
  KEY `fk_asset__production_line_id` (`production_line_id`),
  KEY `fk_asset__current_zone_id` (`current_zone_id`),
  CONSTRAINT `fk_asset__current_zone_id` FOREIGN KEY (`current_zone_id`) REFERENCES `zone` (`id`),
  CONSTRAINT `fk_asset__production_line_id` FOREIGN KEY (`production_line_id`) REFERENCES `production_line` (`id`),
  CONSTRAINT `fk_asset__site_id` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,'NON_INDUSTRIAL_ASSET','apprivoiser','préciser échapper','ronfler adhérer à l\'entour de','LOST','CRITICAL','conseil d’administration','ha ha ouin également','au-dehors déjà','actionnaire','dans la mesure où turquoise fonctionnaire',10547.08,5857.46,29585.63,10083.25,29275,'ramener','pouvoir associer','B14',22972.66,29529.41,25737.73,12941.28,22550.02,10365.72,'puisque commissionnaire propre',0,'PTC','2025-12-16','2025-12-16',30607,'crac hormis','2025-12-16 11:15:35.000000','près','2025-12-16 07:46:31.000000',NULL,NULL,NULL),(2,'INDUSTRIAL_ASSET','mal à la faveur de circulaire','toujours avant que','là-haut ouah','INACTIVE','CRITICAL','loin','quoique via','admirablement','atchoum sage','vouloir de manière à ce que tant que',749.14,19929.45,24145.18,1308.57,9037,'suffisamment assez','pin-pon','V1',21070.48,29932.89,11555.08,1047.15,28995.89,4112.84,'rédaction retrouver',0,'NONE','2025-12-16','2025-12-16',11554,'troubler carrément avare','2025-12-16 08:35:01.000000','rédaction franco','2025-12-16 14:04:21.000000',NULL,NULL,NULL),(3,'NON_INDUSTRIAL_ASSET','lorsque','ouch de façon à ce que touriste','aux alentours de rose ouch','SCRAPPED','MEDIUM','personnel parce que aujourd\'hui','en faveur de plaisanter de façon à ce que','totalement rose du fait que','sur retourner croâ','triathlète dorénavant adversaire',30597.05,4456.27,23764.37,6190.52,990,'sentir','smack tsoin-tsoin','B5',18561.93,233.29,27027.31,15250.52,2500.50,18128.97,'ouille',1,'PT1000','2025-12-16','2025-12-16',15387,'en face de','2025-12-16 14:37:41.000000','subito rectorat dedans','2025-12-16 05:39:58.000000',NULL,NULL,NULL),(4,'INDUSTRIAL_ASSET','vlan secouriste coupable','patientèle','vaste communauté étudiante','OUT_OF_SERVICE','LOW','placide débrouiller pendant que','toutefois adversaire prout','vétuste afin que pis','quoique à condition que','psitt',750.27,27821.37,16719.28,4013.48,155,'bien que snif','au-delà atchoum hi','B5',32569.26,11777.99,12078.98,15104.78,22657.55,19841.06,'aussitôt que dessous oups',0,'NONE','2025-12-16','2025-12-16',18537,'hôte dès que','2025-12-16 20:12:39.000000','en guise de soudain','2025-12-16 12:16:10.000000',NULL,NULL,NULL),(5,'INDUSTRIAL_ASSET','en plus de meuh','rattacher','à condition que','INACTIVE','HIGH','exister badaboum','après que broum tellement','ensuite ferme','arrière super au point que','mélanger',15974.19,13173.43,20049.81,23085.22,20116,'vouh','comme aïe que','B3',5425.87,2320.56,31746.62,10182.51,12917.46,10255.33,'pendant piquer neutre',1,'PT1000','2025-12-16','2025-12-16',11008,'lécher longtemps aux alentours de','2025-12-16 19:04:05.000000','au-dessous de étrangler','2025-12-16 00:28:12.000000',NULL,NULL,NULL),(6,'INDUSTRIAL_ASSET','clientèle de peur que de façon à ce que','déclencher','renaître vlan tchou tchouu','SCRAPPED','HIGH','gratter solitaire','administration prestataire de services','entre commissionnaire','bzzz convenir après que','vu que snif',15591.63,29884.04,2633.22,95.18,17816,'tant que derechef','lectorat comme','B3',4345.74,11801.03,6312.94,4509.34,3970.33,14262.52,'fendre intervenir pourvu que',0,'NONE','2025-12-16','2025-12-16',565,'bang spécialiste coac coac','2025-12-16 20:00:29.000000','adepte parce que','2025-12-16 23:21:57.000000',NULL,NULL,NULL),(7,'INDUSTRIAL_ASSET','ouah mal insipide','bzzz','comme souvent auprès de','SCRAPPED','LOW','communauté étudiante','oh','dring quand','terne arrière considérable','drelin avex vlan',10575.79,17088.76,4457.99,10964.57,16375,'très','pour que','B35',30493.77,131.72,19278.70,9836.32,7937.23,13234.81,'consentir cadre',1,'NONE','2025-12-16','2025-12-16',29450,'d\'après sitôt','2025-12-16 03:10:17.000000','de crainte que sale après','2025-12-16 12:47:54.000000',NULL,NULL,NULL),(8,'INDUSTRIAL_ASSET','désigner cocorico sur','également membre titulaire','consommer','OUT_OF_SERVICE','CRITICAL','présidence peut-être','a à partir de','fabriquer quoique','derrière clac','certes toutefois',30805.74,19370.91,4147.63,25949.44,8362,'rectorat','hé pff guide','B35',21151.79,31848.87,16881.86,383.14,27568.64,17023.24,'croâ',0,'PT1000','2025-12-16','2025-12-16',24643,'ensuite','2025-12-16 19:54:55.000000','partenaire apparemment','2025-12-16 11:49:39.000000',NULL,NULL,NULL),(9,'INDUSTRIAL_ASSET','pour que','tsoin-tsoin','euh de façon que empêcher','SCRAPPED','HIGH','vers','triste','du côté de','auparavant ressortir','énergique',19137.23,28363.05,13374.81,26867.73,1598,'commissionnaire','brusque','V1',25688.53,28607.65,32573.08,22849.40,5892.87,28313.41,'rallier',1,'PT100','2025-12-16','2025-12-16',22178,'soudain après','2025-12-16 03:06:48.000000','en decà de bientôt','2025-12-16 06:09:58.000000',NULL,NULL,NULL),(10,'NON_INDUSTRIAL_ASSET','clac clientèle','magenta','gai rapide pallier','LOST','LOW','jeune','pourpre','cyan','dans la mesure où gonfler','sombre rudement',28368.49,27980.16,31119.63,29991.33,11956,'gestionnaire tic-tac','équipe de recherche patientèle','B5',13265.73,8058.99,6878.55,23882.71,12892.07,18101.94,'méconnaître plouf relever',0,'NONE','2025-12-16','2025-12-16',17823,'si bien que autour de soucier','2025-12-16 20:25:42.000000','ha ha brusque afin que','2025-12-16 23:33:44.000000',NULL,NULL,NULL);
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_movement_request`
--

DROP TABLE IF EXISTS `asset_movement_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_movement_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  `requested_at` datetime(6) NOT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `from_location_label` varchar(200) DEFAULT NULL,
  `to_location_label` varchar(200) DEFAULT NULL,
  `esign_workflow_id` varchar(120) DEFAULT NULL,
  `esign_status` varchar(80) DEFAULT NULL,
  `esign_last_update` datetime(6),
  `signed_at` datetime(6),
  `executed_at` datetime(6),
  `requested_by` varchar(120) DEFAULT NULL,
  `approved_by` varchar(120) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `asset_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_asset_movement_request__asset_id` (`asset_id`),
  CONSTRAINT `fk_asset_movement_request__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_movement_request`
--

LOCK TABLES `asset_movement_request` WRITE;
/*!40000 ALTER TABLE `asset_movement_request` DISABLE KEYS */;
INSERT INTO `asset_movement_request` VALUES (1,'SIGNED','2025-12-16 16:24:15.000000','main-d’œuvre filer hors','secouriste triathlète','au-delà','rompre','minuscule','2025-12-16 15:03:28.000000','2025-12-16 07:29:52.000000','2025-12-16 20:17:41.000000','vu que hormis recourir','communauté étudiante patientèle','prétendre bientôt','2025-12-16 00:55:42.000000','aussi infiniment près de','2025-12-16 08:48:14.000000',NULL),(2,'REJECTED','2025-12-16 07:36:33.000000','de façon que','quand gens chef','secouriste dense','tendre étant donné que près de','extra plouf à l\'égard de','2025-12-16 23:09:23.000000','2025-12-16 05:35:03.000000','2025-12-16 08:13:54.000000','dans cot cot coupable','à la faveur de recommander du moment que','du fait que','2025-12-16 18:20:30.000000','même','2025-12-16 01:59:07.000000',NULL),(3,'REJECTED','2025-12-16 04:48:43.000000','au point que cot cot','de façon que','simple renaître amorphe','pin-pon à condition que innombrable','gravir à peu près broum','2025-12-16 18:07:11.000000','2025-12-16 09:37:50.000000','2025-12-16 22:38:59.000000','pendant que','rapide prout','camarade même si altruiste','2025-12-16 11:10:20.000000','même si vis-à-vie de','2025-12-16 18:51:09.000000',NULL),(4,'SIGNED','2025-12-16 23:42:21.000000','lunatique euh','antagoniste franco','touriste interrompre au prix de','coac coac vouh','groin groin zzzz coller','2025-12-16 07:24:45.000000','2025-12-16 18:39:06.000000','2025-12-16 17:04:01.000000','alors que en dedans de','concernant','sus aussi','2025-12-16 04:07:34.000000','jeune enfant équipe','2025-12-16 06:58:51.000000',NULL),(5,'SIGNING','2025-12-16 11:50:03.000000','depuis','dès que','formuler','tchou tchouu terne apte','dérober altruiste','2025-12-16 17:21:26.000000','2025-12-16 20:55:42.000000','2025-12-16 20:15:01.000000','adorable','chef drelin immense','pourvu que','2025-12-16 05:25:58.000000','toc','2025-12-16 05:11:43.000000',NULL),(6,'EXECUTED','2025-12-16 23:31:49.000000','tchou tchouu','outre barrer','quasi tout à fait encore','revenir','dessous','2025-12-16 00:41:02.000000','2025-12-16 22:36:30.000000','2025-12-16 09:21:57.000000','calme','pourvu que','adorable à l\'encontre de avare','2025-12-16 23:15:12.000000','patientèle coupable','2025-12-16 16:01:11.000000',NULL),(7,'SUBMITTED','2025-12-16 09:54:21.000000','à l\'entour de patientèle trop','près de débile','ding rapide','cuicui veiller après que','dynamique','2025-12-16 17:15:27.000000','2025-12-16 11:19:14.000000','2025-12-16 13:25:42.000000','étant donné que turquoise incognito','tranquille raide','sauf sans que fonctionnaire','2025-12-16 14:03:01.000000','avare','2025-12-16 22:50:16.000000',NULL),(8,'REJECTED','2025-12-16 02:57:20.000000','assurément hé','taper','vu que','durant boire','conseil municipal quoique protester','2025-12-16 23:06:19.000000','2025-12-16 15:55:08.000000','2025-12-16 03:40:23.000000','prévaloir','dès comme quitte à','sous','2025-12-16 23:40:15.000000','derechef miam drôlement','2025-12-16 03:26:34.000000',NULL),(9,'DRAFT','2025-12-16 09:10:43.000000','circulaire développer','secouriste juriste','commis de cuisine','commissionnaire terriblement gens','novice','2025-12-16 03:43:01.000000','2025-12-16 20:38:46.000000','2025-12-16 03:48:13.000000','à raison de coin-coin puisque','proche de','secours hebdomadaire si bien que','2025-12-16 10:34:25.000000','sans doute autrement trop','2025-12-16 21:40:46.000000',NULL),(10,'SUBMITTED','2025-12-16 04:48:10.000000','jamais','aussitôt que vaste dépendre','joliment autoriser avare','magenta entre','mature en outre de sans','2025-12-16 21:59:01.000000','2025-12-16 02:09:05.000000','2025-12-16 14:41:17.000000','autour de atchoum','de façon à ce que','entre au point que entourer','2025-12-16 07:25:44.000000','au-dedans de','2025-12-16 13:02:10.000000',NULL);
/*!40000 ALTER TABLE `asset_movement_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2025-12-17 16:56:47',1,'EXECUTED','9:27550c65ac854b8b465ad94b898a7a20','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002659-1','jhipster','config/liquibase/changelog/20251217002659_added_entity_Site.xml','2025-12-17 16:56:47',2,'EXECUTED','9:4fd21acf5e236df741860a51e248459f','createTable tableName=site; dropDefaultValue columnName=created_date, tableName=site; dropDefaultValue columnName=last_modified_date, tableName=site','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002659-1-data','jhipster','config/liquibase/changelog/20251217002659_added_entity_Site.xml','2025-12-17 16:56:47',3,'EXECUTED','9:70af1a70ae70d2fad47dbf49a1a21dd7','loadData tableName=site','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002700-1','jhipster','config/liquibase/changelog/20251217002700_added_entity_ProductionLine.xml','2025-12-17 16:56:47',4,'EXECUTED','9:525088b9d6758ca82ebadce99bc274b0','createTable tableName=production_line; dropDefaultValue columnName=created_date, tableName=production_line; dropDefaultValue columnName=last_modified_date, tableName=production_line','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002700-1-data','jhipster','config/liquibase/changelog/20251217002700_added_entity_ProductionLine.xml','2025-12-17 16:56:47',5,'EXECUTED','9:603c140701d561ae663c7aceda891bf4','loadData tableName=production_line','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002701-1','jhipster','config/liquibase/changelog/20251217002701_added_entity_Zone.xml','2025-12-17 16:56:47',6,'EXECUTED','9:756e283a1f3bc309861540ac0a74bb3a','createTable tableName=zone; dropDefaultValue columnName=created_date, tableName=zone; dropDefaultValue columnName=last_modified_date, tableName=zone','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002701-1-data','jhipster','config/liquibase/changelog/20251217002701_added_entity_Zone.xml','2025-12-17 16:56:47',7,'EXECUTED','9:984df54053680b78b2fa998f44d19edb','loadData tableName=zone','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002702-1','jhipster','config/liquibase/changelog/20251217002702_added_entity_Gateway.xml','2025-12-17 16:56:47',8,'EXECUTED','9:9f4da9ff2b9fa699112e4bba9b37ee8a','createTable tableName=gateway; dropDefaultValue columnName=installed_at, tableName=gateway; dropDefaultValue columnName=created_date, tableName=gateway; dropDefaultValue columnName=last_modified_date, tableName=gateway','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002702-1-data','jhipster','config/liquibase/changelog/20251217002702_added_entity_Gateway.xml','2025-12-17 16:56:47',9,'EXECUTED','9:a3a9f25f4d4e1b43905b7a10c34dafb5','loadData tableName=gateway','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002703-1','jhipster','config/liquibase/changelog/20251217002703_added_entity_Asset.xml','2025-12-17 16:56:47',10,'EXECUTED','9:3b2a7a2fb6983f326bc1879cbd942f96','createTable tableName=asset; dropDefaultValue columnName=created_date, tableName=asset; dropDefaultValue columnName=last_modified_date, tableName=asset','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002703-1-data','jhipster','config/liquibase/changelog/20251217002703_added_entity_Asset.xml','2025-12-17 16:56:47',11,'EXECUTED','9:c55a8140bb926dd516f4f2b3fe08b20b','loadData tableName=asset','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002704-1','jhipster','config/liquibase/changelog/20251217002704_added_entity_Sensor.xml','2025-12-17 16:56:47',12,'EXECUTED','9:127ba522b7aa62863e334855f48abd8a','createTable tableName=sensor; dropDefaultValue columnName=installed_at, tableName=sensor; dropDefaultValue columnName=created_date, tableName=sensor; dropDefaultValue columnName=last_modified_date, tableName=sensor','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002704-1-data','jhipster','config/liquibase/changelog/20251217002704_added_entity_Sensor.xml','2025-12-17 16:56:47',13,'EXECUTED','9:60944106304be4b502f90c079ca477c0','loadData tableName=sensor','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002705-1','jhipster','config/liquibase/changelog/20251217002705_added_entity_SensorMeasurement.xml','2025-12-17 16:56:47',14,'EXECUTED','9:9878ad04b6683fd6d3ae410d1d00742c','createTable tableName=sensor_measurement; dropDefaultValue columnName=measured_at, tableName=sensor_measurement; dropDefaultValue columnName=created_date, tableName=sensor_measurement; dropDefaultValue columnName=last_modified_date, tableName=sens...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002705-1-data','jhipster','config/liquibase/changelog/20251217002705_added_entity_SensorMeasurement.xml','2025-12-17 16:56:47',15,'EXECUTED','9:e6df2f4683f4f272cdeae2ae1bbb4258','loadData tableName=sensor_measurement','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002706-1','jhipster','config/liquibase/changelog/20251217002706_added_entity_MaintenanceEvent.xml','2025-12-17 16:56:47',16,'EXECUTED','9:60edd78eee09c29488f9600de148aebf','createTable tableName=maintenance_event; dropDefaultValue columnName=requested_at, tableName=maintenance_event; dropDefaultValue columnName=planned_at, tableName=maintenance_event; dropDefaultValue columnName=started_at, tableName=maintenance_even...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002706-1-data','jhipster','config/liquibase/changelog/20251217002706_added_entity_MaintenanceEvent.xml','2025-12-17 16:56:47',17,'EXECUTED','9:e04b889a448b6b4ed1f4253e304f64d6','loadData tableName=maintenance_event','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002707-1','jhipster','config/liquibase/changelog/20251217002707_added_entity_Document.xml','2025-12-17 16:56:47',18,'EXECUTED','9:3e945a72e230e0f8c7aaab75137542d0','createTable tableName=document; dropDefaultValue columnName=uploaded_at, tableName=document; dropDefaultValue columnName=created_date, tableName=document; dropDefaultValue columnName=last_modified_date, tableName=document','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002707-1-data','jhipster','config/liquibase/changelog/20251217002707_added_entity_Document.xml','2025-12-17 16:56:47',19,'EXECUTED','9:2919bcbfa06057e34e4fe6987e5d1f2a','loadData tableName=document','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002708-1','jhipster','config/liquibase/changelog/20251217002708_added_entity_DocumentLink.xml','2025-12-17 16:56:47',20,'EXECUTED','9:75da2695548a541b74efb9088570ebfa','createTable tableName=document_link; dropDefaultValue columnName=linked_at, tableName=document_link; dropDefaultValue columnName=created_date, tableName=document_link; dropDefaultValue columnName=last_modified_date, tableName=document_link','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002708-1-data','jhipster','config/liquibase/changelog/20251217002708_added_entity_DocumentLink.xml','2025-12-17 16:56:47',21,'EXECUTED','9:1528e5cdce88e5c28d9e8c67b341b1ce','loadData tableName=document_link','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002709-1','jhipster','config/liquibase/changelog/20251217002709_added_entity_AssetMovementRequest.xml','2025-12-17 16:56:48',22,'EXECUTED','9:4ccfbbafb71be26b185d949287691f61','createTable tableName=asset_movement_request; dropDefaultValue columnName=requested_at, tableName=asset_movement_request; dropDefaultValue columnName=esign_last_update, tableName=asset_movement_request; dropDefaultValue columnName=signed_at, table...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002709-1-data','jhipster','config/liquibase/changelog/20251217002709_added_entity_AssetMovementRequest.xml','2025-12-17 16:56:48',23,'EXECUTED','9:5fea0c76b319a71b2bfd6915e8d9eddb','loadData tableName=asset_movement_request','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002710-1','jhipster','config/liquibase/changelog/20251217002710_added_entity_LocationEvent.xml','2025-12-17 16:56:48',24,'EXECUTED','9:2f5d23c9b638ab752875f161c4aa4c7b','createTable tableName=location_event; dropDefaultValue columnName=observed_at, tableName=location_event; dropDefaultValue columnName=created_date, tableName=location_event; dropDefaultValue columnName=last_modified_date, tableName=location_event','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002710-1-data','jhipster','config/liquibase/changelog/20251217002710_added_entity_LocationEvent.xml','2025-12-17 16:56:48',25,'EXECUTED','9:3efcd6d5458c3e5ad2b100d43a6f5db3','loadData tableName=location_event','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002711-1','jhipster','config/liquibase/changelog/20251217002711_added_entity_SystemEvent.xml','2025-12-17 16:56:48',26,'EXECUTED','9:13fdd4802af00c7df6a6866569d2848c','createTable tableName=system_event; dropDefaultValue columnName=created_at, tableName=system_event; dropDefaultValue columnName=created_date, tableName=system_event; dropDefaultValue columnName=last_modified_date, tableName=system_event','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002711-1-data','jhipster','config/liquibase/changelog/20251217002711_added_entity_SystemEvent.xml','2025-12-17 16:56:48',27,'EXECUTED','9:8e1093aa043f482d3af2c5b0891ae39f','loadData tableName=system_event','',NULL,'4.29.2','faker',NULL,'5987007002'),('20251217002700-2','jhipster','config/liquibase/changelog/20251217002700_added_entity_constraints_ProductionLine.xml','2025-12-17 16:56:48',28,'EXECUTED','9:46a370cd986da79d4b644efa18596029','addForeignKeyConstraint baseTableName=production_line, constraintName=fk_production_line__site_id, referencedTableName=site','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002701-2','jhipster','config/liquibase/changelog/20251217002701_added_entity_constraints_Zone.xml','2025-12-17 16:56:48',29,'EXECUTED','9:385fdd52013bd9d2b82990d28b328e4c','addForeignKeyConstraint baseTableName=zone, constraintName=fk_zone__site_id, referencedTableName=site','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002702-2','jhipster','config/liquibase/changelog/20251217002702_added_entity_constraints_Gateway.xml','2025-12-17 16:56:48',30,'EXECUTED','9:e08de2278de9a6cc93dc232433c7a9bc','addForeignKeyConstraint baseTableName=gateway, constraintName=fk_gateway__site_id, referencedTableName=site; addForeignKeyConstraint baseTableName=gateway, constraintName=fk_gateway__zone_id, referencedTableName=zone','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002703-2','jhipster','config/liquibase/changelog/20251217002703_added_entity_constraints_Asset.xml','2025-12-17 16:56:48',31,'EXECUTED','9:5758000d9fddf7d8169966f8a9197397','addForeignKeyConstraint baseTableName=asset, constraintName=fk_asset__site_id, referencedTableName=site; addForeignKeyConstraint baseTableName=asset, constraintName=fk_asset__production_line_id, referencedTableName=production_line; addForeignKeyCo...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002704-2','jhipster','config/liquibase/changelog/20251217002704_added_entity_constraints_Sensor.xml','2025-12-17 16:56:48',32,'EXECUTED','9:fb06bae60840cb3e7ba2e5f7c63b527d','addForeignKeyConstraint baseTableName=sensor, constraintName=fk_sensor__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002705-2','jhipster','config/liquibase/changelog/20251217002705_added_entity_constraints_SensorMeasurement.xml','2025-12-17 16:56:48',33,'EXECUTED','9:82fc1aa4b9cec75889bb1d8b8c955528','addForeignKeyConstraint baseTableName=sensor_measurement, constraintName=fk_sensor_measurement__sensor_id, referencedTableName=sensor','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002706-2','jhipster','config/liquibase/changelog/20251217002706_added_entity_constraints_MaintenanceEvent.xml','2025-12-17 16:56:48',34,'EXECUTED','9:8ed1256cc7d79f9679e414300c661c5b','addForeignKeyConstraint baseTableName=maintenance_event, constraintName=fk_maintenance_event__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002708-2','jhipster','config/liquibase/changelog/20251217002708_added_entity_constraints_DocumentLink.xml','2025-12-17 16:56:48',35,'EXECUTED','9:a62c10a7196d1518ce533ca91305517c','addForeignKeyConstraint baseTableName=document_link, constraintName=fk_document_link__document_id, referencedTableName=document','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002709-2','jhipster','config/liquibase/changelog/20251217002709_added_entity_constraints_AssetMovementRequest.xml','2025-12-17 16:56:48',36,'EXECUTED','9:18121b59654178e041d207db6e981af4','addForeignKeyConstraint baseTableName=asset_movement_request, constraintName=fk_asset_movement_request__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002710-2','jhipster','config/liquibase/changelog/20251217002710_added_entity_constraints_LocationEvent.xml','2025-12-17 16:56:48',37,'EXECUTED','9:86f2c2bd577dc6ac45d0c03ad0ebf09b','addForeignKeyConstraint baseTableName=location_event, constraintName=fk_location_event__asset_id, referencedTableName=asset; addForeignKeyConstraint baseTableName=location_event, constraintName=fk_location_event__zone_id, referencedTableName=zone;...','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217002711-2','jhipster','config/liquibase/changelog/20251217002711_added_entity_constraints_SystemEvent.xml','2025-12-17 16:56:48',38,'EXECUTED','9:fadfe96c3f2f39f48e0f2f907809d327','addForeignKeyConstraint baseTableName=system_event, constraintName=fk_system_event__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'5987007002'),('20251217004256','jhipster','config/liquibase/changelog/20251217004256_added_entity_EntityAuditEvent.xml','2025-12-17 16:56:48',39,'EXECUTED','9:20a5090c3960cbb5753c420a1747a2a0','createTable tableName=jhi_entity_audit_event; createIndex indexName=idx_entity_audit_event_entity_id, tableName=jhi_entity_audit_event; createIndex indexName=idx_entity_audit_event_entity_type, tableName=jhi_entity_audit_event; dropDefaultValue co...','',NULL,'4.29.2',NULL,NULL,'5987007002');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `mime_type` varchar(120) NOT NULL,
  `size_bytes` bigint DEFAULT NULL,
  `storage_ref` varchar(500) NOT NULL,
  `checksum_sha_256` varchar(128) DEFAULT NULL,
  `uploaded_at` datetime(6) NOT NULL,
  `uploaded_by` varchar(120) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` VALUES (1,'dire ronron snob','adepte',15859,'quasiment','changer','2025-12-16 01:16:40.000000','sur','de façon à au dépens de probablement','2025-12-16 11:15:23.000000','au défaut de','2025-12-16 09:29:06.000000'),(2,'de façon à ce que','à la merci pendant que',1839,'bof juriste oh','du fait que infime en outre de','2025-12-16 09:09:20.000000','téméraire prestataire de services jeune enfant','porte-parole vlan exprès','2025-12-16 13:54:25.000000','secours brusque pourvu que','2025-12-16 04:17:20.000000'),(3,'afin que ouf','apte',10072,'splendide','loin de tordre','2025-12-16 19:39:26.000000','ah recta','souple','2025-12-16 10:32:18.000000','aussi','2025-12-16 04:37:23.000000'),(4,'par suite de altruiste','énorme dense',20814,'pin-pon insuffisamment','parmi population du Québec à raison de','2025-12-16 20:14:43.000000','de crainte que ouf fort','assez exploser en dépit de','2025-12-16 18:29:42.000000','avant que adepte ha ha','2025-12-16 05:07:15.000000'),(5,'représenter triathlète en dehors de','solitaire bè membre à vie',14728,'contribuer au point que','volontiers rapide conseil municipal','2025-12-16 14:56:27.000000','communiquer communauté étudiante','souvent économiser équiper','2025-12-16 23:49:20.000000','turquoise','2025-12-16 06:52:14.000000'),(6,'fade très','subsister bang considérable',26597,'avant-hier','entre-temps dehors avare','2025-12-16 05:42:55.000000','zzzz quand ?','commis auparavant','2025-12-16 17:57:08.000000','exiger','2025-12-16 07:39:28.000000'),(7,'porte-parole quoique','guide avant que premièrement',18312,'de peur que calme','actionnaire tchou tchouu au point que','2025-12-16 00:37:47.000000','lorsque badaboum','de sorte que','2025-12-16 18:26:21.000000','en decà de minuscule','2025-12-16 13:48:20.000000'),(8,'de peur que si','emporter',31639,'areu areu bzzz','autrement','2025-12-16 01:14:59.000000','circulaire','sage ensuite','2025-12-16 09:29:25.000000','fade enfermer','2025-12-16 15:45:19.000000'),(9,'à travers de crainte que chef','déplacer dès que ouch',9952,'au-dedans de','respecter','2025-12-16 22:26:26.000000','personnel professionnel vlan touriste','au-dessus','2025-12-16 19:23:42.000000','cadre','2025-12-16 10:20:09.000000'),(10,'souffrir','crac',26871,'splendide','partout d\'abord direction','2025-12-16 23:28:44.000000','meuh','sitôt que assassiner prou','2025-12-16 16:42:02.000000','lunatique','2025-12-16 13:24:48.000000');
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_link`
--

DROP TABLE IF EXISTS `document_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entity_type` varchar(255) NOT NULL,
  `entity_id` bigint NOT NULL,
  `label` varchar(150) DEFAULT NULL,
  `linked_at` datetime(6) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `document_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_document_link__document_id` (`document_id`),
  CONSTRAINT `fk_document_link__document_id` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_link`
--

LOCK TABLES `document_link` WRITE;
/*!40000 ALTER TABLE `document_link` DISABLE KEYS */;
INSERT INTO `document_link` VALUES (1,'ASSET',21329,'euh','2025-12-16 02:00:41.000000','brave','2025-12-16 00:49:46.000000','acheter','2025-12-16 03:55:10.000000',NULL),(2,'ASSET',17429,'serviable','2025-12-16 17:04:18.000000','administration autrefois recta','2025-12-16 17:26:20.000000','blablabla','2025-12-16 20:08:24.000000',NULL),(3,'ASSET',18658,'à l\'insu de','2025-12-16 07:43:36.000000','aimable','2025-12-16 10:22:41.000000','certainement alors identifier','2025-12-16 07:35:11.000000',NULL),(4,'MAINTENANCE_EVENT',9336,'administration','2025-12-16 04:17:11.000000','foule grandement','2025-12-16 04:51:00.000000','croâ malgré assez','2025-12-16 15:00:53.000000',NULL),(5,'ASSET',26634,'vouh parfois','2025-12-16 13:22:23.000000','prou','2025-12-16 00:34:09.000000','pousser','2025-12-16 15:07:40.000000',NULL),(6,'MOVEMENT_REQUEST',31872,'afin que','2025-12-16 22:50:51.000000','concurrence diététiste de par','2025-12-16 16:52:54.000000','dorénavant','2025-12-16 15:25:21.000000',NULL),(7,'MOVEMENT_REQUEST',18407,'tôt','2025-12-16 23:10:19.000000','chef de cuisine alors que parce que','2025-12-16 17:48:03.000000','trop déborder population du Québec','2025-12-16 17:30:55.000000',NULL),(8,'ASSET',6381,'présenter','2025-12-16 05:25:21.000000','avant que blême','2025-12-16 01:21:13.000000','cependant quoique','2025-12-16 02:04:01.000000',NULL),(9,'MOVEMENT_REQUEST',25170,'du fait que','2025-12-16 02:05:00.000000','débarrasser après que par rapport à','2025-12-16 08:28:02.000000','répondre','2025-12-16 00:38:15.000000',NULL),(10,'MOVEMENT_REQUEST',25260,'altruiste','2025-12-17 00:15:25.000000','de manière à ce que','2025-12-16 07:30:58.000000','étant donné que','2025-12-16 21:39:13.000000',NULL);
/*!40000 ALTER TABLE `document_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateway`
--

DROP TABLE IF EXISTS `gateway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(80) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `vendor` varchar(80) DEFAULT NULL,
  `model` varchar(80) DEFAULT NULL,
  `mac_address` varchar(32) DEFAULT NULL,
  `ip_address` varchar(64) DEFAULT NULL,
  `installed_at` datetime(6),
  `active` tinyint NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `site_id` bigint DEFAULT NULL,
  `zone_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_gateway__code` (`code`),
  KEY `fk_gateway__site_id` (`site_id`),
  KEY `fk_gateway__zone_id` (`zone_id`),
  CONSTRAINT `fk_gateway__site_id` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`),
  CONSTRAINT `fk_gateway__zone_id` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway`
--

LOCK TABLES `gateway` WRITE;
/*!40000 ALTER TABLE `gateway` DISABLE KEYS */;
INSERT INTO `gateway` VALUES (1,'dynamique triathlète','grrr large','croiser paf','environ lunatique','en dehors de dans la mesure où','amorphe composer','2025-12-16 00:27:28.000000',1,'à l\'entour de tandis que','2025-12-16 23:10:17.000000','direction à force de','2025-12-16 05:51:35.000000',NULL,NULL),(2,'miaou','boum aux environs de ramasser','là-haut pourvu que croâ','protester intrépide personnel','touchant','hôte dès que avant','2025-12-16 23:50:42.000000',1,'gens absolument','2025-12-16 02:56:15.000000','sauf bof','2025-12-16 13:59:06.000000',NULL,NULL),(3,'novice','amorphe sitôt que de façon que','de façon à ce que','moderne','cuicui par puisque','assez abattre','2025-12-16 15:22:49.000000',0,'en plus de pourvu que à travers','2025-12-16 19:20:42.000000','raide moins','2025-12-16 13:20:34.000000',NULL,NULL),(4,'tchou tchouu vétuste à moins de','cadre','au défaut de','de peur de de','ouf pendant que magenta','vaste','2025-12-16 00:50:11.000000',0,'devant dedans','2025-12-16 02:40:25.000000','quitte à','2025-12-16 02:59:04.000000',NULL,NULL),(5,'derrière','quasi','aligner sur','abriter conseil d’administration tic-tac','vis-à-vie de à condition que déc','multiple dès','2025-12-16 08:53:28.000000',1,'tellement tolérer','2025-12-16 22:25:15.000000','concurrence assez','2025-12-16 02:05:36.000000',NULL,NULL),(6,'adresser chut à seule fin de','sauf','super','tant prout agiter','souvent chef','tant deviner','2025-12-16 18:36:53.000000',1,'cyan','2025-12-16 03:07:33.000000','clac main-d’œuvre','2025-12-16 04:52:52.000000',NULL,NULL),(7,'parer commissionnaire premièrement','franco','clac','sympathique biathlète','au-dedans de complètement ding','juriste','2025-12-16 06:52:25.000000',1,'solliciter toc-toc','2025-12-16 09:51:28.000000','tant administration','2025-12-16 10:23:44.000000',NULL,NULL),(8,'mieux de peur que','ainsi entièrement','pendant que miaou','tant','meubler parce que foule','près bè','2025-12-16 06:43:24.000000',0,'amorphe bzzz','2025-12-16 20:14:04.000000','au-dehors terne','2025-12-16 17:00:18.000000',NULL,NULL),(9,'réparer ah','conseiller hier balayer','bof parlementaire hypocrite','céder','à partir de responsable','trop','2025-12-16 00:43:06.000000',0,'beaucoup lectorat','2025-12-16 19:33:11.000000','pin-pon poursuivre en dedans de','2025-12-16 02:08:34.000000',NULL,NULL),(10,'alentour','menacer','moderne séculaire','charitable démarrer antagoniste','personnel ouille','cyan','2025-12-16 07:44:18.000000',0,'personnel','2025-12-16 21:07:02.000000','mal appeler si','2025-12-16 01:02:55.000000',NULL,NULL);
/*!40000 ALTER TABLE `gateway` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_entity_audit_event`
--

DROP TABLE IF EXISTS `jhi_entity_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_entity_audit_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entity_id` varchar(50) NOT NULL,
  `entity_type` varchar(255) NOT NULL,
  `action` varchar(20) NOT NULL,
  `entity_value` longtext,
  `commit_version` int DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_entity_audit_event_entity_id` (`entity_id`),
  KEY `idx_entity_audit_event_entity_type` (`entity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_entity_audit_event`
--

LOCK TABLES `jhi_entity_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_entity_audit_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_entity_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` tinyint NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1050 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_event`
--

DROP TABLE IF EXISTS `location_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `source` varchar(255) NOT NULL,
  `observed_at` datetime(6) NOT NULL,
  `zone_confidence` int DEFAULT NULL,
  `rssi` int DEFAULT NULL,
  `tx_power` int DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `accuracy_meters` double DEFAULT NULL,
  `speed_kmh` double DEFAULT NULL,
  `raw_payload` varchar(4000) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `asset_id` bigint DEFAULT NULL,
  `zone_id` bigint DEFAULT NULL,
  `gateway_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_event__asset_id` (`asset_id`),
  KEY `fk_location_event__zone_id` (`zone_id`),
  KEY `fk_location_event__gateway_id` (`gateway_id`),
  CONSTRAINT `fk_location_event__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `fk_location_event__gateway_id` FOREIGN KEY (`gateway_id`) REFERENCES `gateway` (`id`),
  CONSTRAINT `fk_location_event__zone_id` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_event`
--

LOCK TABLES `location_event` WRITE;
/*!40000 ALTER TABLE `location_event` DISABLE KEYS */;
INSERT INTO `location_event` VALUES (1,'BLE','2025-12-16 07:27:16.000000',5828,27726,21950,28756.72,14255.48,14246.54,29243.76,'vaste sitôt que empêcher','snif','2025-12-16 08:56:24.000000','premièrement après-demain reconstruire','2025-12-16 19:49:54.000000',NULL,NULL,NULL),(2,'BLE','2025-12-16 19:07:35.000000',31385,6812,1002,22980.88,22491.63,30070.28,30029.63,'que cocorico','cot cot prestataire de services minuscule','2025-12-16 08:32:08.000000','infime actionnaire','2025-12-16 09:16:16.000000',NULL,NULL,NULL),(3,'GNSS','2025-12-16 16:21:55.000000',17761,8265,4037,27783.95,16570.13,29175.81,2425.96,'quitte à','délégation','2025-12-16 15:50:15.000000','jusqu’à ce que','2025-12-16 23:45:09.000000',NULL,NULL,NULL),(4,'BLE','2025-12-16 01:32:41.000000',2036,25005,26014,16254.86,9309.9,21535.67,12646.03,'sauvage toc-toc dès','feindre outre','2025-12-16 16:33:44.000000','bè','2025-12-16 02:24:01.000000',NULL,NULL,NULL),(5,'MANUAL','2025-12-16 17:44:51.000000',31136,16309,30185,7277.99,13024.86,4131.26,15626.87,'tendre','afin de sauf','2025-12-16 17:35:54.000000','smack quoique durant','2025-12-16 23:45:12.000000',NULL,NULL,NULL),(6,'MANUAL','2025-12-16 15:25:08.000000',31857,26758,11207,1648.24,838.58,29623.01,17853.82,'hi comprendre impressionner','tsoin-tsoin antagoniste','2025-12-16 13:55:16.000000','si avancer insipide','2025-12-16 06:31:41.000000',NULL,NULL,NULL),(7,'GNSS','2025-12-16 13:19:16.000000',24164,21990,23224,23287.31,7790.95,25895.26,15441.33,'sauter','monter bâtir','2025-12-16 20:01:10.000000','groin groin bang','2025-12-16 02:45:43.000000',NULL,NULL,NULL),(8,'BLE','2025-12-16 01:19:26.000000',19484,12515,5502,9806.92,8369.42,11392.98,3555.91,'énoncer devant maintenant','au-dessus de','2025-12-16 01:01:10.000000','au-dessus de tandis que','2025-12-16 04:29:45.000000',NULL,NULL,NULL),(9,'MANUAL','2025-12-16 04:01:42.000000',12437,14958,22578,4549.74,1830.03,14603.12,13303.26,'présidence','prout','2025-12-16 08:41:41.000000','jusqu’à ce que','2025-12-16 02:15:48.000000',NULL,NULL,NULL),(10,'MANUAL','2025-12-16 21:10:26.000000',26749,30342,24143,17635.55,21377.19,9425.88,18802.07,'via en decà de achever','du moment que différer membre de l’équipe','2025-12-16 01:21:46.000000','ha ha pourvu que pallier','2025-12-16 17:29:45.000000',NULL,NULL,NULL);
/*!40000 ALTER TABLE `location_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance_event`
--

DROP TABLE IF EXISTS `maintenance_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `maintenance_type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `requested_at` datetime(6) NOT NULL,
  `planned_at` datetime(6),
  `started_at` datetime(6),
  `finished_at` datetime(6),
  `title` varchar(180) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `technician` varchar(120) DEFAULT NULL,
  `downtime_minutes` int DEFAULT NULL,
  `cost_amount` decimal(21,2) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `asset_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_maintenance_event__asset_id` (`asset_id`),
  CONSTRAINT `fk_maintenance_event__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance_event`
--

LOCK TABLES `maintenance_event` WRITE;
/*!40000 ALTER TABLE `maintenance_event` DISABLE KEYS */;
INSERT INTO `maintenance_event` VALUES (1,'INSPECTION','IN_PROGRESS','2025-12-16 13:40:10.000000','2025-12-16 19:54:46.000000','2025-12-16 17:18:18.000000','2025-12-16 11:46:51.000000','du fait que rectangulaire','orange exister','grandement repartir',27867,13758.31,'premièrement','énumérer pourvu que','2025-12-16 14:01:45.000000','en dedans de croâ hormis','2025-12-16 17:35:53.000000',NULL),(2,'INSPECTION','REQUESTED','2025-12-16 01:29:03.000000','2025-12-16 06:47:25.000000','2025-12-16 04:48:12.000000','2025-12-16 18:10:14.000000','hi sauvage','presser','jusqu’à ce que',22602,8646.81,'proche','désagréable','2025-12-16 19:01:23.000000','ouf secouriste provoquer','2025-12-16 21:27:25.000000',NULL),(3,'REVISION','IN_PROGRESS','2025-12-16 00:27:42.000000','2025-12-16 04:10:25.000000','2025-12-16 21:58:11.000000','2025-12-16 01:43:17.000000','aussitôt que','interpréter pff','tant étonner réconcilier',20342,20693.71,'porte-parole équipe altruiste','tsoin-tsoin','2025-12-16 10:17:14.000000','vraiment brouiller','2025-12-16 22:01:42.000000',NULL),(4,'INSPECTION','DONE','2025-12-16 16:33:55.000000','2025-12-16 11:56:04.000000','2025-12-16 09:00:57.000000','2025-12-16 12:41:25.000000','badaboum','électorat','multiple',13468,24721.75,'smack lâche habile','marron dans la mesure où au-dedans de','2025-12-16 16:00:13.000000','hé','2025-12-16 02:26:11.000000',NULL),(5,'REVISION','CANCELLED','2025-12-16 06:17:44.000000','2025-12-16 09:01:33.000000','2025-12-16 16:27:48.000000','2025-12-16 08:07:12.000000','pschitt raide','de peur que','triste',11537,13593.90,'regretter avex corps enseignant','de manière à d’autant que','2025-12-17 00:15:09.000000','sauf à de manière à','2025-12-16 05:48:46.000000',NULL),(6,'CORRECTIVE','CANCELLED','2025-12-16 15:09:44.000000','2025-12-16 11:34:09.000000','2025-12-16 17:50:07.000000','2025-12-16 16:44:21.000000','moderne','de par','d\'après',8148,20645.63,'téméraire à côté de réaliser','debout à l\'exception de trop','2025-12-16 16:35:15.000000','lunatique probablement','2025-12-16 11:00:18.000000',NULL),(7,'INSPECTION','CANCELLED','2025-12-16 14:21:31.000000','2025-12-16 09:22:05.000000','2025-12-16 05:52:13.000000','2025-12-16 20:01:02.000000','cuicui','tant triathlète','clac malgré incalculable',1775,16683.59,'tant boum','mairie ensuite','2025-12-16 17:43:11.000000','tranquille lorsque au point que','2025-12-16 08:33:56.000000',NULL),(8,'PREVENTIVE','CANCELLED','2025-12-16 11:55:18.000000','2025-12-16 17:06:43.000000','2025-12-16 16:25:09.000000','2025-12-16 17:36:36.000000','subsister en dedans de personnel','après-demain verger doucement','quoique de la part de clac',22662,19066.69,'pacifique déjà concurrence','réjouir','2025-12-16 14:00:35.000000','bè','2025-12-16 20:45:23.000000',NULL),(9,'INSPECTION','CANCELLED','2025-12-16 01:22:23.000000','2025-12-16 20:19:01.000000','2025-12-16 16:26:04.000000','2025-12-16 18:49:41.000000','recommencer améliorer','areu areu dès que','gens',31102,19837.23,'lors police','collègue différer parce que','2025-12-16 07:48:49.000000','direction','2025-12-16 04:40:58.000000',NULL),(10,'INSPECTION','DONE','2025-12-16 06:45:47.000000','2025-12-17 00:18:15.000000','2025-12-16 11:01:44.000000','2025-12-16 09:18:33.000000','électorat','agiter terriblement','constater police',6740,32535.78,'sincère assurément','suivre ha moins','2025-12-16 19:34:28.000000','à moins de efficace','2025-12-16 22:15:02.000000',NULL);
/*!40000 ALTER TABLE `maintenance_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `production_line`
--

DROP TABLE IF EXISTS `production_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `production_line` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `site_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_production_line__site_id` (`site_id`),
  CONSTRAINT `fk_production_line__site_id` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production_line`
--

LOCK TABLES `production_line` WRITE;
/*!40000 ALTER TABLE `production_line` DISABLE KEYS */;
INSERT INTO `production_line` VALUES (1,'soigner fourbe à l\'égard de','timide apte hors','ha','aux alentours de développer souple','2025-12-16 06:38:04.000000','chut avant que quelquefois','2025-12-16 15:46:19.000000',NULL),(2,'hi sous','par suite de','du côté de','smack','2025-12-16 00:47:57.000000','vaste','2025-12-16 01:17:11.000000',NULL),(3,'au-devant tout','avant de','à l\'entour de','pauvre meuh subsister','2025-12-16 14:56:53.000000','atteindre','2025-12-16 06:44:06.000000',NULL),(4,'areu areu patientèle effacer','détourner','de conseil d’administration','tout à fait à partir de','2025-12-16 11:35:16.000000','dans la mesure où loin de','2025-12-16 03:49:46.000000',NULL),(5,'trop reprocher rectangulaire','sauf à','de sorte que','tsoin-tsoin observer','2025-12-16 19:00:04.000000','après que par rapport à clac','2025-12-16 11:00:05.000000',NULL),(6,'devant capter contre','négliger jusqu’à ce que areu areu','pendant que avant personnel','comme là extrêmement','2025-12-16 02:16:36.000000','commissionnaire','2025-12-16 04:47:17.000000',NULL),(7,'tchou tchouu secouriste','impromptu','lors de','guide apparemment aspirer','2025-12-16 06:41:57.000000','hormis','2025-12-16 10:37:31.000000',NULL),(8,'coac coac à condition que coac coac','sitôt que','auprès de','large chef','2025-12-16 12:39:46.000000','mairie que','2025-12-16 21:27:41.000000',NULL),(9,'sauf aux alentours de autour de','à bas de nonobstant blesser','énorme','après-demain terriblement','2025-12-16 11:00:15.000000','de façon à ce que afin que','2025-12-16 03:51:24.000000',NULL),(10,'aussitôt concevoir','de peur que','rédaction','quoique de façon que','2025-12-16 15:19:04.000000','si bien','2025-12-16 10:22:36.000000',NULL);
/*!40000 ALTER TABLE `production_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sensor_type` varchar(255) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `unit` varchar(30) DEFAULT NULL,
  `min_threshold` decimal(21,2) DEFAULT NULL,
  `max_threshold` decimal(21,2) DEFAULT NULL,
  `installed_at` datetime(6),
  `active` tinyint NOT NULL,
  `external_id` varchar(120) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `asset_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sensor__asset_id` (`asset_id`),
  CONSTRAINT `fk_sensor__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,'HUMIDITY','prou au-dedans de rapporter','lâche',6385.79,7179.85,'2025-12-16 20:28:46.000000',0,'procurer','en dépit de','2025-12-16 09:52:08.000000','du moment que','2025-12-16 16:19:30.000000',NULL),(2,'BLE_TAG','novice coac coac','tant étaler',17505.62,21783.47,'2025-12-16 04:24:42.000000',0,'tant soit','pin-pon tchou tchouu étudier','2025-12-16 01:05:20.000000','auprès de ouf','2025-12-16 20:02:14.000000',NULL),(3,'PRESSURE','totalement vraiment sans que','appeler chez pff',4207.31,12754.46,'2025-12-16 13:36:08.000000',1,'intégrer','encore insolite communauté étudiante','2025-12-16 17:40:56.000000','en decà de sympathique loufoque','2025-12-16 13:54:43.000000',NULL),(4,'LIQUID_LEVEL','vouh ainsi fonctionnaire','tellement',6405.17,24859.86,'2025-12-16 03:50:07.000000',0,'membre du personnel','bè','2025-12-16 18:15:28.000000','moderne attraper','2025-12-16 13:44:33.000000',NULL),(5,'LIQUID_LEVEL','concernant pff','afin que blablabla miam',7077.08,31494.42,'2025-12-17 00:26:22.000000',1,'intrépide','membre à vie','2025-12-16 01:51:49.000000','vouh','2025-12-16 01:50:12.000000',NULL),(6,'BLE_TAG','a chez à cause de','de façon à approximativement c',17488.79,22005.09,'2025-12-16 16:44:23.000000',0,'si au prix de','tchou tchouu','2025-12-16 22:08:30.000000','même','2025-12-16 02:06:32.000000',NULL),(7,'VIBRATION','sincère','décharger ouin mélancolique',991.27,7859.26,'2025-12-16 15:04:11.000000',0,'collègue','quoique','2025-12-17 00:04:11.000000','pas mal de crainte que','2025-12-16 16:39:08.000000',NULL),(8,'VIBRATION','ha mairie y','brusque prout',15520.48,21858.22,'2025-12-16 11:54:16.000000',1,'aligner exploiter lâche','main-d’œuvre smack reposer','2025-12-16 14:25:08.000000','chef à condition que','2025-12-16 15:17:10.000000',NULL),(9,'OIL_LEVEL','lorsque','euh entre-temps',6817.79,23070.84,'2025-12-16 04:44:52.000000',1,'tirer de façon à ce que au-delà','tard boum','2025-12-16 09:26:39.000000','miaou émérite concernant','2025-12-16 01:00:47.000000',NULL),(10,'VIBRATION','dîner soumettre vers','dès que plus quasi',13571.62,30215.30,'2025-12-16 17:06:06.000000',1,'mater','étant donné que','2025-12-16 14:51:28.000000','consoler mince aïe','2025-12-16 06:02:56.000000',NULL);
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_measurement`
--

DROP TABLE IF EXISTS `sensor_measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_measurement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `measured_at` datetime(6) NOT NULL,
  `value` decimal(21,2) NOT NULL,
  `quality` varchar(40) DEFAULT NULL,
  `source` varchar(80) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `sensor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sensor_measurement__sensor_id` (`sensor_id`),
  CONSTRAINT `fk_sensor_measurement__sensor_id` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_measurement`
--

LOCK TABLES `sensor_measurement` WRITE;
/*!40000 ALTER TABLE `sensor_measurement` DISABLE KEYS */;
INSERT INTO `sensor_measurement` VALUES (1,'2025-12-16 08:24:40.000000',4398.68,'minuscule','ouin','encore','2025-12-16 01:22:35.000000','bien que','2025-12-16 14:07:47.000000',NULL),(2,'2025-12-16 09:46:42.000000',10200.97,'hors tant que encore','tsoin-tsoin tant que freiner','broum','2025-12-16 16:09:16.000000','de sorte que en plus de','2025-12-16 13:11:16.000000',NULL),(3,'2025-12-16 01:14:05.000000',31480.77,'couper tellement','dès que','épanouir touriste','2025-12-16 06:03:17.000000','altruiste boum','2025-12-16 06:21:02.000000',NULL),(4,'2025-12-16 02:20:07.000000',10016.02,'commis de cuisine','plouf reproduire','jusque équipe sale','2025-12-16 16:11:04.000000','tsoin-tsoin svelte','2025-12-16 04:08:02.000000',NULL),(5,'2025-12-16 11:48:10.000000',303.20,'tellement bzzz subito','meuh gens électorat','à peu près','2025-12-16 11:17:11.000000','hormis poser ficher','2025-12-16 20:37:54.000000',NULL),(6,'2025-12-16 03:29:19.000000',16237.12,'exagérer vaste au-dedans de','dense jeune','orange','2025-12-16 23:21:26.000000','après que','2025-12-16 12:49:34.000000',NULL),(7,'2025-12-16 09:03:12.000000',12331.95,'surveiller','grrr','timide blablabla euh','2025-12-16 11:05:30.000000','hé inviter','2025-12-16 05:20:44.000000',NULL),(8,'2025-12-16 11:06:05.000000',18503.03,'vlan tant','si bien que','grandement puisque émérite','2025-12-16 03:06:04.000000','serviable','2025-12-16 22:11:02.000000',NULL),(9,'2025-12-16 11:51:25.000000',11550.13,'à l\'encontre de hebdomadaire','tailler hormis','vaste','2025-12-16 15:53:32.000000','diététiste communauté étudiante','2025-12-16 09:35:03.000000',NULL),(10,'2025-12-16 05:37:05.000000',9861.72,'spécialiste avant que','pas mal','cultiver','2025-12-16 07:21:39.000000','miam bè chef','2025-12-16 13:58:45.000000',NULL);
/*!40000 ALTER TABLE `sensor_measurement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `site` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_site__code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

LOCK TABLES `site` WRITE;
/*!40000 ALTER TABLE `site` DISABLE KEYS */;
INSERT INTO `site` VALUES (1,'aussi vlan dresser','assurément','oser','pin-pon foule','2025-12-16 12:48:07.000000','espiègle','2025-12-16 14:25:35.000000'),(2,'proche avant de','de façon que administration alors que','membre du personnel gratis communauté étudiante','un peu sage','2025-12-17 00:19:21.000000','approximativement','2025-12-16 05:55:03.000000'),(3,'selon','certainement ramener','habile autour','loin dès franco','2025-12-16 03:59:49.000000','actionnaire laver','2025-12-16 05:12:51.000000'),(4,'spécialiste terriblement','chez fonctionnaire près','à défaut de de façon à chef de cuisine','énorme de façon à ce que','2025-12-16 08:08:37.000000','d’autant que toc pencher','2025-12-16 01:15:02.000000'),(5,'sur certes','terne','camarade trop','habile serviable','2025-12-16 17:45:38.000000','de manière à à partir de au dépens de','2025-12-16 12:33:24.000000'),(6,'à l\'égard de','plouf ding','à côté de au cas où tandis que','au moyen de entraîner','2025-12-16 04:20:25.000000','rectorat coucher','2025-12-16 22:19:17.000000'),(7,'éclater coin-coin','coupable','attaquer','cadre','2025-12-16 19:43:08.000000','enterrer plouf','2025-12-17 00:21:57.000000'),(8,'au-dehors adepte avant','à la merci autrement énergique','rectangulaire pour','de peur de oh ha','2025-12-16 11:49:26.000000','où contre','2025-12-16 08:19:41.000000'),(9,'peindre','vu que','population du Québec amorcer croâ','au-dessous de juriste en decà de','2025-12-16 13:23:32.000000','parlementaire probablement attaquer','2025-12-16 18:08:54.000000'),(10,'bon','en face de','extatique nonobstant','fourrer broum','2025-12-16 14:34:31.000000','souple également','2025-12-16 07:50:47.000000');
/*!40000 ALTER TABLE `site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_event`
--

DROP TABLE IF EXISTS `system_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_type` varchar(120) NOT NULL,
  `entity_type` varchar(255) NOT NULL,
  `entity_id` bigint DEFAULT NULL,
  `severity` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(120) DEFAULT NULL,
  `correlation_id` varchar(64) DEFAULT NULL,
  `payload` longtext,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `asset_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_system_event__asset_id` (`asset_id`),
  CONSTRAINT `fk_system_event__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_event`
--

LOCK TABLES `system_event` WRITE;
/*!40000 ALTER TABLE `system_event` DISABLE KEYS */;
INSERT INTO `system_event` VALUES (1,'prestataire de services','ASSET',23759,'WARNING','SYSTEM','psitt lors','2025-12-16 16:49:55.000000','de','cot cot absolument','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 22:55:38.000000','partager','2025-12-16 14:24:09.000000',NULL),(2,'affable sous','LOCATION_EVENT',11170,'WARNING','API','infime rencontrer semer','2025-12-16 04:01:48.000000','lunatique lors de énorme','ouin secouriste admirablement','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 23:03:06.000000','cadre vers','2025-12-16 13:57:42.000000',NULL),(3,'a traîner émérite','FORESIGHT',3428,'ERROR','UI','ouin hôte à peine','2025-12-16 22:36:14.000000','épargner antique abîmer','tôt même si commissionnaire','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 21:21:03.000000','d\'abord','2025-12-16 14:59:17.000000',NULL),(4,'sans que conseil municipal','LOCATION_EVENT',8387,'WARNING','UI','quand hystérique de manière à','2025-12-16 08:02:38.000000','comme','rafraîchir malgré cocorico','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 02:33:29.000000','pas mal toc-toc','2025-12-16 11:09:47.000000',NULL),(5,'maintenant déposer joliment','ASSET',9120,'INFO','ESIGN','chef de cuisine hé','2025-12-16 12:18:42.000000','glouglou ronron','redevenir croâ afin de','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 09:16:57.000000','passablement deçà','2025-12-16 14:07:02.000000',NULL),(6,'ouch rose','MOVEMENT_REQUEST',12836,'WARNING','UI','maintenant moyennant triathlète','2025-12-16 20:19:10.000000','de crainte que conseil d’administration en guise de','intrépide parce que tchou tchouu','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 15:54:57.000000','pourvu que','2025-12-16 14:55:05.000000',NULL),(7,'d\'après timide','ASSET',6924,'INFO','ESIGN','cadre dessus demander','2025-12-16 02:26:16.000000','crac rudement','éclater','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 11:38:48.000000','conseil d’administration ouah clientèle','2025-12-16 21:31:42.000000',NULL),(8,'glisser','SENSOR',16549,'WARNING','ESIGN','alors gravir répondre','2025-12-16 10:41:32.000000','au-devant résister','conquérir charger étonner','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 11:53:55.000000','antagoniste discerner sous','2025-12-16 15:43:50.000000',NULL),(9,'immense cependant','MAINTENANCE_EVENT',26569,'ERROR','UI','candide ouille a','2025-12-16 07:17:00.000000','avant que','fidèle couvrir','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 02:26:00.000000','trop','2025-12-16 18:28:18.000000',NULL),(10,'cadre a','MAINTENANCE_EVENT',22234,'WARNING','API','exécuter identifier','2025-12-16 07:43:52.000000','bè quoique','fort à côté de','JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.','2025-12-16 14:07:56.000000','tant','2025-12-16 14:02:09.000000',NULL);
/*!40000 ALTER TABLE `system_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zone`
--

DROP TABLE IF EXISTS `zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zone` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(80) NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `zone_type` varchar(80) DEFAULT NULL,
  `center_lat` double DEFAULT NULL,
  `center_lon` double DEFAULT NULL,
  `radius_meters` double DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_by` varchar(50) NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  `site_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_zone__code` (`code`),
  KEY `fk_zone__site_id` (`site_id`),
  CONSTRAINT `fk_zone__site_id` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zone`
--

LOCK TABLES `zone` WRITE;
/*!40000 ALTER TABLE `zone` DISABLE KEYS */;
INSERT INTO `zone` VALUES (1,'repérer direction','aligner après que par suite de','de crainte que','dévoiler prou patientèle',7498.09,17883.44,22779.38,'ding interdire critiquer','2025-12-16 03:14:13.000000','menacer','2025-12-16 13:06:26.000000',NULL),(2,'autour','areu areu chut badaboum','extrêmement','gigantesque bzzz',27250.85,21105.98,12178.82,'coin-coin adepte quoique','2025-12-16 05:41:09.000000','présenter bien que','2025-12-16 16:30:57.000000',NULL),(3,'avant que','quand ? près de parce que','dans la mesure où','du moment que',27408.54,21286.44,8715.34,'encore pacifique oups','2025-12-16 14:32:06.000000','lors de','2025-12-16 15:39:30.000000',NULL),(4,'incalculable écraser','devant gratis cuicui','cadre','hebdomadaire de manière à ce que',14475.44,2795.05,19675,'toc','2025-12-16 08:34:34.000000','fonctionnaire quoique','2025-12-16 16:23:29.000000',NULL),(5,'de façon à ce que','candide infime cuicui','spécialiste','préparer antique',23840.19,26748.31,30177.84,'en vérité à condition que','2025-12-16 16:16:21.000000','personnel pourpre ouah','2025-12-16 18:20:32.000000',NULL),(6,'ensuite à seule fin de rédaction','administration','pff','neutre triste',25815.34,23178.71,22586.31,'après que jusque','2025-12-16 12:59:21.000000','bof à l\'entour de','2025-12-16 19:31:24.000000',NULL),(7,'personnel miaou au lieu de','ensuite aïe','contre','longtemps',6336.44,7304.94,22626.49,'broum','2025-12-16 02:40:16.000000','sitôt que vaste toc-toc','2025-12-16 08:26:11.000000',NULL),(8,'devant','police durant moquer','peut-être','dans la mesure où',3178.9,4253.51,11286.21,'mélancolique prout comme','2025-12-16 13:41:12.000000','orienter','2025-12-16 16:56:24.000000',NULL),(9,'penser déchiffrer acheter','quoique','direction police aussitôt que','énorme super encourager',31017.58,21359.24,13957.18,'par suite de coin-coin pas mal','2025-12-16 15:24:55.000000','vouh séduire','2025-12-16 06:38:14.000000',NULL),(10,'contre sans doute prout','ah naguère','à la faveur de','guide meuh athlète',10409.47,25585.15,5471.67,'quand','2025-12-16 06:26:33.000000','guide à côté de','2025-12-16 07:32:21.000000',NULL);
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;
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

-- Dump completed on 2025-12-19  8:48:09
