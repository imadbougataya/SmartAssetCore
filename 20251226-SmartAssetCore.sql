-- MySQL dump 10.13  Distrib 8.4.5, for Win64 (x86_64)
--
-- Host: localhost    Database: SmartAssetCore
-- ------------------------------------------------------
-- Server version	8.4.5

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
  `geofence_policy` varchar(255) NOT NULL,
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
  `production_line_id` bigint NOT NULL,
  `allowed_site_id` bigint DEFAULT NULL,
  `allowed_zone_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_asset__asset_code` (`asset_code`),
  KEY `fk_asset__production_line_id` (`production_line_id`),
  KEY `fk_asset__allowed_site_id` (`allowed_site_id`),
  KEY `fk_asset__allowed_zone_id` (`allowed_zone_id`),
  CONSTRAINT `fk_asset__allowed_site_id` FOREIGN KEY (`allowed_site_id`) REFERENCES `site` (`id`),
  CONSTRAINT `fk_asset__allowed_zone_id` FOREIGN KEY (`allowed_zone_id`) REFERENCES `zone` (`id`),
  CONSTRAINT `fk_asset__production_line_id` FOREIGN KEY (`production_line_id`) REFERENCES `production_line` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,'INDUSTRIAL_ASSET','308-B01-M01','308-B01-M01','MOTEUR ELECTRIQUE 3.65KW 660 YV 1450tr/min B5 SIEMENS','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LA9113-4KA91-Z','E1008_5266390_04_001',3.65,660.00,4.65,0.79,1450,'IP55','F','B5',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-01-10','2025-06-01',1,7,1,1),(2,'INDUSTRIAL_ASSET','308-B01-M02','308-B01-M02','MOTEUR ELECTRIQUE 2.75KW 660 YV 1445tr/min B5 SIEMENS','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LA9107-4KA91-Z','E1008_5266390_05_001',2.75,660.00,3.50,0.76,1445,'IP55','F','B5',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-01-12','2025-05-18',1,7,1,2),(3,'INDUSTRIAL_ASSET','308-B01-M03','308-B01-M03','MOTEUR ELECTRIQUE 2.75KW 660 YV 1445tr/min B35 SIEMENS','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LA9107-4KA96-Z','E1008_5266390_07_001',2.75,660.00,3.50,0.79,1445,'IP55','F','B35',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-01-15','2025-06-10',2,7,1,3),(4,'INDUSTRIAL_ASSET','308-B01-M05','308-B01-M05','MOTEUR ELECTRIQUE 630KW 10000 YV 1493tr/min B3 SIEMENS','ACTIVE','CRITICAL','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LA4450-4AN80-Z','A81296986010001_2010',630.00,10000.00,45.50,0.84,1493,'IP55','F','B3',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',1,'PT100','2023-11-20','2025-04-15',3,7,1,1),(5,'INDUSTRIAL_ASSET','308-C01-M01','308-C01-M01','MOTEUR ELECTRIQUE 355KW 660 YV 1488tr/min B3 SIEMENS','ACTIVE','CRITICAL','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LA8353-4AB60-Z','1303980000000',355.00,660.00,371.00,0.86,1488,'IP55','F','B3',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',1,'PTC','2023-09-01','2025-03-30',2,7,1,2),(6,'INDUSTRIAL_ASSET','308-C03-M01','308-C03-M01','MOTEUR ELECTRIQUE 82.8KW 660 YV 2978tr/min B3 SIEMENS','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LG6283-2AB90-Z','UC1008_073640801',82.80,660.00,87.00,0.88,2978,'IP55','F','B3',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',1,'PTC','2024-02-05','2025-07-01',1,7,1,3),(7,'INDUSTRIAL_ASSET','308-T01-M01','308-T01-M01','MOTEUR ELECTRIQUE 4KW 660 YV 1445tr/min M3 SEW EURODRIVE','ACTIVE','MEDIUM','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SEW_EURODRIVE','FA77_GDRS100LC4_RS_TF','401347900801',4.00,660.00,5.10,0.81,1445,'IP55','F','M3',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-03-12','2025-06-20',1,7,1,1),(8,'INDUSTRIAL_ASSET','308-T13-M01','308-T13-M01','MOTEUR ELECTRIQUE 15KW 660 YV 1460tr/min B3 WEG','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','WEG','160_L_04','1009014709',15.00,660.00,16.90,0.83,1460,'IP55','F','B3',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-01-30','2025-05-22',1,7,1,2),(9,'INDUSTRIAL_ASSET','308-Z21-M01','308-Z21-M01','MOTEUR ELECTRIQUE 20.2KW 660 YV 1475tr/min B5 SIEMENS','ACTIVE','HIGH','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SIEMENS','1LG6186-4AA91-Z','UD1009_1290687_001_000',20.20,660.00,23.50,0.83,1475,'IP55','F','B5',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',1,'PTC','2024-02-18','2025-06-05',2,7,1,3),(10,'INDUSTRIAL_ASSET','308-Z36-M01','308-Z36-M01','MOTEUR ELECTRIQUE 1.5KW 660 YV 1395tr/min M1 SEW EURODRIVE','ACTIVE','MEDIUM','ZONE_ONLY','Chef Atelier Broyage','CC-BROY-308','SEW_EURODRIVE','FA67_GDRS90M4','401351296202',1.50,660.00,2.00,0.82,1395,'IP55','F','M1',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-04-10','2025-07-10',1,7,1,1),(100,'NON_INDUSTRIAL_ASSET','ELEC-ARM-308-01','ELEC-ARM-308-01','Armoire electrique BT Atelier Broyage','ACTIVE','HIGH','ZONE_ONLY','Responsable Maintenance','CC-UTIL-308','SCHNEIDER','Prisma_P','ARM308BT01',NULL,NULL,NULL,NULL,NULL,'IP54','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2023-06-15','2025-05-10',2,10,1,2),(101,'NON_INDUSTRIAL_ASSET','UPS-JLF-01','UPS-JLF-01','Onduleur industriel secours salle controle','ACTIVE','HIGH','SITE_ONLY','Responsable_IT_OT','CC-UTIL-SITE','APC','Galaxy_VS','UPS-JLF-0001',20.00,400.00,NULL,NULL,NULL,'IP20','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2022-11-20','2025-04-02',1,10,1,3),(102,'NON_INDUSTRIAL_ASSET','COMP-AIR-308-01','COMP-AIR-308-01','Compresseur air auxiliaire Broyage','ACTIVE','MEDIUM','ZONE_ONLY','Responsable_Utilites','CC-UTIL-308','ATLAS_COPCO','GA37','AC-GA37-308',37.00,400.00,NULL,NULL,NULL,'IP55','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2023-03-10','2025-06-18',1,10,1,1),(103,'NON_INDUSTRIAL_ASSET','VENT-308-EXH-01','VENT-308-EXH-01','Ventilateur extraction poussieres Atelier 308','ACTIVE','MEDIUM','ZONE_ONLY','Responsable_Securite','CC-UTIL-308','COMEFRI','TLZ_630','VENT30801',15.00,400.00,NULL,NULL,NULL,'IP55','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2023-08-05','2025-05-28',1,10,1,2),(104,'NON_INDUSTRIAL_ASSET','HYD-GRP-308-01','HYD-GRP-308-01','Groupe hydraulique auxiliaire convoyeurs','ACTIVE','HIGH','ZONE_ONLY','Responsable Maintenance','CC-UTIL-308','BOSCH_REXROTH','HYDR-UNIT-90','HYD308A',22.00,400.00,NULL,NULL,NULL,'IP55','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2022-09-18','2025-03-12',2,10,1,3),(105,'NON_INDUSTRIAL_ASSET','CONV-AUX-308-01','CONV-AUX-308-01','Convoyeur secondaire evacuation dechets','ACTIVE','LOW','ZONE_ONLY','Chef Atelier','CC-UTIL-308','RULMECA','CV-AUX-800','CV308AUX',NULL,NULL,NULL,NULL,NULL,'IP54','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-01-25','2025-06-01',0,10,1,1),(106,'NON_INDUSTRIAL_ASSET','PUMP-UTIL-308-01','PUMP-UTIL-308-01','Pompe utilites eau industrielle','ACTIVE','MEDIUM','ZONE_ONLY','Responsable_Utilites','CC-UTIL-308','KSB','ETANORM_50-200','KSB30801',11.00,400.00,NULL,NULL,NULL,'IP55','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2023-05-02','2025-04-22',1,10,1,2),(107,'NON_INDUSTRIAL_ASSET','LUBE-STAT-308-01','LUBE-STAT-308-01','Station de graissage centralisee','ACTIVE','LOW','ZONE_ONLY','Responsable Maintenance','CC-UTIL-308','SKF','Multiline','SKF308LUBE',NULL,NULL,NULL,NULL,NULL,'IP54','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-02-10','2025-07-05',0,10,1,3),(108,'NON_INDUSTRIAL_ASSET','MOBILE-CART-01','MOBILE-CART-01','Chariot mobile maintenance electrique','ACTIVE','LOW','SITE_ONLY','Magasin Technique','CC-LOG-SITE','FACOM','ROLL_CAB','FACOM-CART-01',NULL,NULL,NULL,NULL,NULL,'IP20','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2024-06-01','2025-06-15',0,10,1,1),(109,'NON_INDUSTRIAL_ASSET','CTRL-PANEL-308-01','CTRL-PANEL-308-01','Pupitre commande local convoyeurs','ACTIVE','MEDIUM','ZONE_ONLY','Chef Atelier','CC-UTIL-308','SIEMENS','TP1200_Comfort','HMI30801',NULL,NULL,NULL,NULL,NULL,'IP65','','UNKNOWN',NULL,NULL,NULL,NULL,NULL,NULL,'MANUAL',0,'NONE','2023-10-12','2025-05-30',1,10,1,2);
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
  `esign_status` varchar(255) NOT NULL,
  `esign_last_update` datetime(6),
  `signed_at` datetime(6),
  `executed_at` datetime(6),
  `asset_id` bigint NOT NULL,
  `requested_by_id` bigint NOT NULL,
  `approved_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_asset_movement_request__asset_id` (`asset_id`),
  KEY `fk_asset_movement_request__requested_by_id` (`requested_by_id`),
  KEY `fk_asset_movement_request__approved_by_id` (`approved_by_id`),
  CONSTRAINT `fk_asset_movement_request__approved_by_id` FOREIGN KEY (`approved_by_id`) REFERENCES `jhi_user` (`id`),
  CONSTRAINT `fk_asset_movement_request__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `fk_asset_movement_request__requested_by_id` FOREIGN KEY (`requested_by_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1558 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_movement_request`
--

LOCK TABLES `asset_movement_request` WRITE;
/*!40000 ALTER TABLE `asset_movement_request` DISABLE KEYS */;
INSERT INTO `asset_movement_request` VALUES (1,'REJECTED','2025-12-16 08:42:22.000000','Déplacement non justifié pour l’état actuel de l’équipement','Magasin Central – Jorf Lasfar','Atelier Maintenance – Jorf Lasfar','WF-REQ-001','REJECTED','2025-12-16 09:10:00.000000','2025-12-16 09:15:00.000000',NULL,1,1,2),(2,'SIGNED','2025-12-16 09:27:40.000000','Besoin de mise à disposition sur ligne de production','Magasin Central – Jorf Lasfar','Ligne Production Acide – Jorf Lasfar','WF-REQ-002','SIGNED','2025-12-16 10:00:00.000000','2025-12-16 10:05:00.000000',NULL,2,1,2),(3,'EXECUTED','2025-12-16 06:35:18.000000','Transfert pour maintenance corrective urgente','Ligne Production Engrais – Jorf Lasfar','Atelier Maintenance – Jorf Lasfar','WF-REQ-003','SIGNED','2025-12-16 07:00:00.000000','2025-12-16 07:05:00.000000','2025-12-16 08:30:00.000000',3,1,2),(4,'DRAFT','2025-12-16 07:02:24.000000','Préparation déplacement préventif planifié','Magasin Central – Jorf Lasfar','Zone Stockage Matières Premières – Jorf Lasfar','','NOT_STARTED',NULL,NULL,NULL,4,1,NULL),(5,'SUBMITTED','2025-12-16 11:43:00.000000','Optimisation emplacement équipement avant campagne','Zone Stockage Matières Premières – Jorf Lasfar','Ligne Production Engrais – Jorf Lasfar','WF-REQ-005','PENDING','2025-12-16 12:00:00.000000',NULL,NULL,5,1,NULL),(6,'SIGNING','2025-12-16 13:57:33.000000','Besoin temporaire pour essais techniques','Atelier Maintenance – Jorf Lasfar','Ligne Production Acide – Jorf Lasfar','WF-REQ-006','NOT_STARTED','2025-12-16 14:10:00.000000',NULL,NULL,6,1,NULL),(7,'SIGNING','2025-12-16 15:25:51.000000','Réaffectation équipement suite arrêt ligne','Ligne Production Engrais – Jorf Lasfar','Magasin Central – Jorf Lasfar','WF-REQ-007','NOT_STARTED','2025-12-16 15:40:00.000000',NULL,NULL,7,1,NULL),(8,'REJECTED','2025-12-16 17:00:23.000000','Demande incompatible avec le planning de production','Magasin Central – Jorf Lasfar','Zone Chargement Portuaire – Jorf Lasfar','WF-REQ-008','REJECTED','2025-12-16 17:30:00.000000','2025-12-16 17:35:00.000000',NULL,8,1,2),(9,'EXECUTED','2025-12-16 06:16:43.000000','Acheminement équipement vers zone portuaire','Atelier Maintenance – Jorf Lasfar','Zone Chargement Portuaire – Jorf Lasfar','WF-REQ-009','SIGNED','2025-12-16 06:45:00.000000','2025-12-16 06:50:00.000000','2025-12-16 08:10:00.000000',9,1,2),(10,'REJECTED','2025-12-16 07:14:51.000000','Équipement non disponible à la date demandée','Ligne Production Acide – Jorf Lasfar','Ligne Production Engrais – Jorf Lasfar','WF-REQ-010','REJECTED','2025-12-16 07:40:00.000000','2025-12-16 07:45:00.000000',NULL,10,1,2),(1500,'DRAFT','2025-12-24 23:00:00.000000','remplacement équipement defectueux ','Engrais 307','Broyage',NULL,'NOT_STARTED',NULL,NULL,NULL,2,1,10),(1501,'DRAFT','2025-12-25 13:06:25.811035','maintenance ','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 13:06:27.103468',NULL,NULL,2,1,10),(1502,'DRAFT','2025-12-25 13:08:01.276539','toto','Engrais 307','Broyage',NULL,'FAILED','2025-12-25 13:08:02.296661',NULL,NULL,2,1,10),(1503,'DRAFT','2025-12-25 13:13:49.840186','ugugj','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 13:13:50.838769',NULL,NULL,2,1,10),(1504,'DRAFT','2025-12-25 13:25:44.530446','dsdssd','Broyage','Engrais 307',NULL,'FAILED','2025-12-25 13:25:45.601855',NULL,NULL,3,1,11),(1505,'DRAFT','2025-12-25 13:29:06.396479','wxscvdsfgb','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 13:29:07.445009',NULL,NULL,4,1,11),(1506,'DRAFT','2025-12-25 13:34:10.329363','geggrr','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 13:34:11.487492',NULL,NULL,2,1,11),(1507,'DRAFT','2025-12-25 13:36:42.415572','ssees','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 13:36:43.453595',NULL,NULL,2,1,13),(1508,'DRAFT','2025-12-25 13:38:37.415279','sdfghjk','Broyage','Engrais 307',NULL,'FAILED','2025-12-25 13:38:38.398111',NULL,NULL,3,1,11),(1509,'DRAFT','2025-12-25 13:43:57.215352','sdfghj','Broyage','Engrais 307',NULL,'FAILED','2025-12-25 13:43:57.868232',NULL,NULL,3,1,12),(1510,'DRAFT','2025-12-25 13:46:27.992481','cvbn,;','Engrais 306','Broyage',NULL,'FAILED','2025-12-25 13:46:31.840756',NULL,NULL,1,1,12),(1511,'DRAFT','2025-12-25 19:30:55.020680','frddhtfthfg','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 19:31:06.288848',NULL,NULL,3,1,13),(1512,'DRAFT','2025-12-25 19:38:16.183397','dsffghj','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 19:38:16.982154',NULL,NULL,2,1,11),(1513,'DRAFT','2025-12-25 19:42:43.673519',NULL,'Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 19:42:44.639696',NULL,NULL,2,1,15),(1514,'DRAFT','2025-12-25 20:11:34.227414','gdgr','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 20:11:36.029521',NULL,NULL,1,1,10),(1515,'DRAFT','2025-12-25 20:20:15.290840','sdrtrtt','Broyage','Engrais 307',NULL,'FAILED','2025-12-25 20:20:16.911943',NULL,NULL,3,1,10),(1516,'DRAFT','2025-12-25 20:32:20.030174','gfrdhtfdhtfdt','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 20:32:21.274312',NULL,NULL,3,1,10),(1517,'DRAFT','2025-12-25 20:36:29.519390','dcsgfdsgdrsfgrds','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 20:36:30.534993',NULL,NULL,2,1,10),(1518,'DRAFT','2025-12-25 20:44:40.965281','fgbfdthgtfrhs','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 20:44:42.112552',NULL,NULL,3,1,11),(1519,'DRAFT','2025-12-25 20:51:01.751146','ferzregertg','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 20:51:02.818156',NULL,NULL,3,1,11),(1520,'DRAFT','2025-12-25 21:12:44.961413','ezrty','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 21:12:45.558915',NULL,NULL,1,1,11),(1521,'DRAFT','2025-12-25 21:14:15.840862','t\'rtete\'r','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 21:14:16.466975',NULL,NULL,3,1,11),(1522,'DRAFT','2025-12-25 21:28:27.952752','fegdrfgrfdgfdrgrger','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 21:28:28.632157',NULL,NULL,3,1,11),(1523,'DRAFT','2025-12-25 21:32:57.402301','fcgdwbcgfhdfhgdhgfdgbfdht','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 21:32:58.896141',NULL,NULL,3,1,11),(1524,'DRAFT','2025-12-25 21:36:01.200423','fesqfeefes','Broyage','Engrais 306',NULL,'IN_PROGRESS','2025-12-25 21:36:05.603095',NULL,NULL,3,1,11),(1525,'DRAFT','2025-12-25 21:40:18.219071','ffeeffeefz','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 21:40:19.235426',NULL,NULL,4,1,11),(1526,'DRAFT','2025-12-25 21:44:42.772561','dgtfgtfgtf','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 21:44:43.590562',NULL,NULL,3,1,11),(1527,'DRAFT','2025-12-25 21:49:30.979279','eeeezzs','Engrais 306','Engrais 307',NULL,'IN_PROGRESS','2025-12-25 21:49:34.284520',NULL,NULL,4,1,11),(1528,'DRAFT','2025-12-25 21:58:00.957658','sdgsfgsgserf','Engrais 307','Engrais 306',NULL,'IN_PROGRESS','2025-12-25 21:58:03.822613',NULL,NULL,2,1,11),(1529,'DRAFT','2025-12-25 22:05:52.209373','ezfezze\'ezfr\'efrz','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 22:05:53.185203',NULL,NULL,4,1,11),(1530,'DRAFT','2025-12-25 22:27:13.591916','trehgtrhetrhetreghre(\'y','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 22:27:14.892959',NULL,NULL,2,1,11),(1531,'DRAFT','2025-12-25 22:33:28.118953','t(reytyrhtryhrht','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 22:33:29.347167',NULL,NULL,3,1,11),(1532,'DRAFT','2025-12-25 23:07:47.199002','rfggrfregferer','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 23:07:48.322864',NULL,NULL,1,1,11),(1533,'DRAFT','2025-12-25 23:13:00.747197','fdgrgdrfgrgre','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 23:13:02.913991',NULL,NULL,3,1,11),(1534,'DRAFT','2025-12-25 23:15:44.862725','gtdhggrgre','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 23:15:46.173570',NULL,NULL,3,1,11),(1535,'DRAFT','2025-12-25 23:21:24.006676','fefezefzfezfezr','Broyage','Engrais 306',NULL,'FAILED','2025-12-25 23:21:25.152949',NULL,NULL,6,1,11),(1536,'DRAFT','2025-12-25 23:28:27.824344','sdefqfefse','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 23:28:29.822083',NULL,NULL,4,1,11),(1537,'DRAFT','2025-12-25 23:31:07.226406','bbklbjklbkj','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 23:31:09.220779',NULL,NULL,7,1,11),(1538,'DRAFT','2025-12-25 23:34:31.666207',NULL,'Broyage','Engrais 306',NULL,'FAILED','2025-12-25 23:34:33.518938',NULL,NULL,6,1,11),(1539,'DRAFT','2025-12-25 23:42:41.514585','grrgrgfrg','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 23:42:43.327371',NULL,NULL,5,1,11),(1540,'DRAFT','2025-12-25 23:45:52.208207','dssfdfsdfdsdfsfd','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 23:45:53.945985',NULL,NULL,1,1,11),(1541,'DRAFT','2025-12-25 23:49:03.524188','dhgrffgdgdrf','Engrais 307','Broyage',NULL,'FAILED','2025-12-25 23:49:05.321188',NULL,NULL,5,1,11),(1542,'DRAFT','2025-12-25 23:52:19.560659','egregrergfgrefegregrergz','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-25 23:52:21.336477',NULL,NULL,2,1,11),(1543,'DRAFT','2025-12-25 23:55:04.292160','bhgyuiuiyuyuygyu','Engrais 307','Broyage',NULL,'FAILED','2025-12-25 23:55:06.157551',NULL,NULL,2,1,11),(1544,'DRAFT','2025-12-25 23:58:16.220365','dfbdgdgfdggfrdsgfer','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-25 23:58:18.129760',NULL,NULL,4,1,11),(1545,'DRAFT','2025-12-26 00:02:51.881625','ytryt-ryt-ruytujh','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-26 00:02:53.627686',NULL,NULL,2,1,11),(1546,'DRAFT','2025-12-26 00:04:31.348875','dgrhegrhtreggregeregr','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-26 00:04:33.815879',NULL,NULL,4,1,11),(1547,'DRAFT','2025-12-26 00:07:44.057621','fqfezefzefzefz','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-26 00:07:45.916672',NULL,NULL,4,1,11),(1548,'DRAFT','2025-12-26 00:10:18.429426','reegrtergtgeregrgeregr','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-26 00:10:20.490877',NULL,NULL,2,1,11),(1549,'DRAFT','2025-12-26 00:13:27.725077','tyrheuhytrjhtrej','Broyage','Engrais 306',NULL,'FAILED','2025-12-26 00:13:29.574058',NULL,NULL,3,1,11),(1550,'DRAFT','2025-12-26 00:16:28.040357','sdfghjklm','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-26 00:16:29.923549',NULL,NULL,2,1,11),(1551,'DRAFT','2025-12-26 00:19:12.536766','vvsdvsdvsdfvsdvds','Broyage','Engrais 306',NULL,'FAILED','2025-12-26 00:19:14.261011',NULL,NULL,6,1,11),(1552,'DRAFT','2025-12-26 00:22:10.329699','dfghjn,','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-26 00:22:12.145619',NULL,NULL,4,1,11),(1553,'DRAFT','2025-12-26 00:26:17.071824','fbddgdgdg','Broyage','Engrais 306',NULL,'FAILED','2025-12-26 00:26:19.742183',NULL,NULL,3,1,11),(1554,'DRAFT','2025-12-26 00:28:24.320155','sdsfsfddsfsfdfdsfds','Engrais 306','Engrais 307',NULL,'FAILED','2025-12-26 00:28:27.069735',NULL,NULL,4,1,11),(1555,'DRAFT','2025-12-26 00:34:13.001135','rrregtrgerereg','Engrais 307','Engrais 306',NULL,'FAILED','2025-12-26 00:34:17.145110',NULL,NULL,100,1,11),(1556,'DRAFT','2025-12-26 00:39:39.328980','vgvkhjjykhughkjy','Broyage','Engrais 306','9f14d257-bae9-4c61-8c09-7ab4ce8b3f45','IN_PROGRESS','2025-12-26 00:39:42.450490',NULL,NULL,3,1,11),(1557,'DRAFT','2025-12-26 00:44:05.836541','dgvdfdfdvdf','Broyage','Engrais 306','7b8a2b7d-da00-47cd-9e34-0bdd4fab6eee','IN_PROGRESS','2025-12-26 00:44:08.541145',NULL,NULL,3,1,11);
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
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2025-12-22 23:12:00',1,'EXECUTED','9:4200230a1f0c593d48ab86aafb0c5c94','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002659-1','jhipster','config/liquibase/changelog/20251217002659_added_entity_Site.xml','2025-12-22 23:12:00',2,'EXECUTED','9:8686a7c29b6e4315faf64fca824ad81a','createTable tableName=site','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002659-1-data','jhipster','config/liquibase/changelog/20251217002659_added_entity_Site.xml','2025-12-22 23:12:00',3,'EXECUTED','9:999fb825c8272117a0ee5c7bbf0708a6','loadData tableName=site','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002700-1','jhipster','config/liquibase/changelog/20251217002700_added_entity_ProductionLine.xml','2025-12-22 23:12:00',4,'EXECUTED','9:80023836465455f87173724c573542ce','createTable tableName=production_line','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002700-1-data','jhipster','config/liquibase/changelog/20251217002700_added_entity_ProductionLine.xml','2025-12-22 23:12:00',5,'EXECUTED','9:c96ee5573e40e1d11dc67a5eaabbfa9b','loadData tableName=production_line','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002701-1','jhipster','config/liquibase/changelog/20251217002701_added_entity_Zone.xml','2025-12-22 23:12:00',6,'EXECUTED','9:c28f7fb31daf6dc94edd452972059857','createTable tableName=zone','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002701-1-data','jhipster','config/liquibase/changelog/20251217002701_added_entity_Zone.xml','2025-12-22 23:12:00',7,'EXECUTED','9:8d7171cba0eaef1cb1d3a755fbb18bb5','loadData tableName=zone','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002702-1','jhipster','config/liquibase/changelog/20251217002702_added_entity_Gateway.xml','2025-12-22 23:12:00',8,'EXECUTED','9:9a5c14417d50a7ad6f5d01057b97b254','createTable tableName=gateway; dropDefaultValue columnName=installed_at, tableName=gateway','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002702-1-data','jhipster','config/liquibase/changelog/20251217002702_added_entity_Gateway.xml','2025-12-22 23:12:00',9,'EXECUTED','9:495e952abcb947f362f5fedda2be2205','loadData tableName=gateway','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002703-1','jhipster','config/liquibase/changelog/20251217002703_added_entity_Asset.xml','2025-12-22 23:12:00',10,'EXECUTED','9:cbb011a053560ba3e5ab99271ec82a56','createTable tableName=asset','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002703-1-data','jhipster','config/liquibase/changelog/20251217002703_added_entity_Asset.xml','2025-12-22 23:12:00',11,'EXECUTED','9:3a6c236a18af3831b508ee69f5cac9e2','loadData tableName=asset','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002704-1','jhipster','config/liquibase/changelog/20251217002704_added_entity_Sensor.xml','2025-12-22 23:12:00',12,'EXECUTED','9:be8d72444c8f96c5dc02908928eaf22e','createTable tableName=sensor; dropDefaultValue columnName=installed_at, tableName=sensor','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002704-1-data','jhipster','config/liquibase/changelog/20251217002704_added_entity_Sensor.xml','2025-12-22 23:12:00',13,'EXECUTED','9:a927298c8ee6eef35afcfd3f382a2920','loadData tableName=sensor','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002705-1','jhipster','config/liquibase/changelog/20251217002705_added_entity_SensorMeasurement.xml','2025-12-22 23:12:00',14,'EXECUTED','9:0679ade6ef7f699d1bf10854b0af0b56','createTable tableName=sensor_measurement; dropDefaultValue columnName=measured_at, tableName=sensor_measurement','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002705-1-data','jhipster','config/liquibase/changelog/20251217002705_added_entity_SensorMeasurement.xml','2025-12-22 23:12:00',15,'EXECUTED','9:bd0f3c820f798128ab4044005b496e4f','loadData tableName=sensor_measurement','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002706-1','jhipster','config/liquibase/changelog/20251217002706_added_entity_MaintenanceEvent.xml','2025-12-22 23:12:00',16,'EXECUTED','9:2e823396bcb356b72acdb4a4aa269b92','createTable tableName=maintenance_event; dropDefaultValue columnName=requested_at, tableName=maintenance_event; dropDefaultValue columnName=planned_at, tableName=maintenance_event; dropDefaultValue columnName=started_at, tableName=maintenance_even...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002706-1-data','jhipster','config/liquibase/changelog/20251217002706_added_entity_MaintenanceEvent.xml','2025-12-22 23:12:00',17,'EXECUTED','9:dad8d9fc368e241140d65b4d1a477bf9','loadData tableName=maintenance_event','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002707-1','jhipster','config/liquibase/changelog/20251217002707_added_entity_Document.xml','2025-12-22 23:12:00',18,'EXECUTED','9:cd61ea8a82203a4e1ea3f0b265bbab08','createTable tableName=document; dropDefaultValue columnName=uploaded_at, tableName=document','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002707-1-data','jhipster','config/liquibase/changelog/20251217002707_added_entity_Document.xml','2025-12-22 23:12:01',19,'EXECUTED','9:852ef4e9eeccb78e047c7fe7ac91a6e1','loadData tableName=document','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002708-1','jhipster','config/liquibase/changelog/20251217002708_added_entity_DocumentLink.xml','2025-12-22 23:12:01',20,'EXECUTED','9:76c07954c0fbf88d53625d6fff101c83','createTable tableName=document_link; dropDefaultValue columnName=linked_at, tableName=document_link; dropDefaultValue columnName=created_date, tableName=document_link; dropDefaultValue columnName=last_modified_date, tableName=document_link','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002708-1-data','jhipster','config/liquibase/changelog/20251217002708_added_entity_DocumentLink.xml','2025-12-22 23:12:01',21,'EXECUTED','9:59022a06b52dc3b0474cd4b475d8debf','loadData tableName=document_link','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002709-1','jhipster','config/liquibase/changelog/20251217002709_added_entity_AssetMovementRequest.xml','2025-12-22 23:12:01',22,'EXECUTED','9:ca162faefae1aaa5c9e42fcab86d53d0','createTable tableName=asset_movement_request; dropDefaultValue columnName=requested_at, tableName=asset_movement_request; dropDefaultValue columnName=esign_last_update, tableName=asset_movement_request; dropDefaultValue columnName=signed_at, table...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002709-1-data','jhipster','config/liquibase/changelog/20251217002709_added_entity_AssetMovementRequest.xml','2025-12-22 23:12:01',23,'EXECUTED','9:9a08855b23928ae7b6a63c38df6b2160','loadData tableName=asset_movement_request','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002710-1','jhipster','config/liquibase/changelog/20251217002710_added_entity_LocationEvent.xml','2025-12-22 23:12:01',24,'EXECUTED','9:3e9364dd49def920f875300313c80b1e','createTable tableName=location_event; dropDefaultValue columnName=observed_at, tableName=location_event','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002710-1-data','jhipster','config/liquibase/changelog/20251217002710_added_entity_LocationEvent.xml','2025-12-22 23:12:01',25,'EXECUTED','9:cdfb1ca900aeb3c3c281710e031651f7','loadData tableName=location_event','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002711-1','jhipster','config/liquibase/changelog/20251217002711_added_entity_SystemEvent.xml','2025-12-22 23:12:01',26,'EXECUTED','9:25f5d7c3ba1577e2582d14547e6647be','createTable tableName=system_event; dropDefaultValue columnName=created_at, tableName=system_event','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002711-1-data','jhipster','config/liquibase/changelog/20251217002711_added_entity_SystemEvent.xml','2025-12-22 23:12:01',27,'EXECUTED','9:ebce2ae5dd7b11fcd73ee10c7a4a098d','loadData tableName=system_event','',NULL,'4.29.2','faker',NULL,'6441519551'),('20251217002700-2','jhipster','config/liquibase/changelog/20251217002700_added_entity_constraints_ProductionLine.xml','2025-12-22 23:12:01',28,'EXECUTED','9:201867c6e2b70894f419e8e7ef7ce72d','addForeignKeyConstraint baseTableName=production_line, constraintName=fk_production_line__zone_id, referencedTableName=zone','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002701-2','jhipster','config/liquibase/changelog/20251217002701_added_entity_constraints_Zone.xml','2025-12-22 23:12:01',29,'EXECUTED','9:385fdd52013bd9d2b82990d28b328e4c','addForeignKeyConstraint baseTableName=zone, constraintName=fk_zone__site_id, referencedTableName=site','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002702-2','jhipster','config/liquibase/changelog/20251217002702_added_entity_constraints_Gateway.xml','2025-12-22 23:12:01',30,'EXECUTED','9:e08de2278de9a6cc93dc232433c7a9bc','addForeignKeyConstraint baseTableName=gateway, constraintName=fk_gateway__site_id, referencedTableName=site; addForeignKeyConstraint baseTableName=gateway, constraintName=fk_gateway__zone_id, referencedTableName=zone','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002703-2','jhipster','config/liquibase/changelog/20251217002703_added_entity_constraints_Asset.xml','2025-12-22 23:12:02',31,'EXECUTED','9:bfb72aa93006f0a10e78fa1992918dc2','addForeignKeyConstraint baseTableName=asset, constraintName=fk_asset__production_line_id, referencedTableName=production_line; addForeignKeyConstraint baseTableName=asset, constraintName=fk_asset__allowed_site_id, referencedTableName=site; addFore...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002704-2','jhipster','config/liquibase/changelog/20251217002704_added_entity_constraints_Sensor.xml','2025-12-22 23:12:02',32,'EXECUTED','9:fb06bae60840cb3e7ba2e5f7c63b527d','addForeignKeyConstraint baseTableName=sensor, constraintName=fk_sensor__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002705-2','jhipster','config/liquibase/changelog/20251217002705_added_entity_constraints_SensorMeasurement.xml','2025-12-22 23:12:02',33,'EXECUTED','9:82fc1aa4b9cec75889bb1d8b8c955528','addForeignKeyConstraint baseTableName=sensor_measurement, constraintName=fk_sensor_measurement__sensor_id, referencedTableName=sensor','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002706-2','jhipster','config/liquibase/changelog/20251217002706_added_entity_constraints_MaintenanceEvent.xml','2025-12-22 23:12:02',34,'EXECUTED','9:8ed1256cc7d79f9679e414300c661c5b','addForeignKeyConstraint baseTableName=maintenance_event, constraintName=fk_maintenance_event__asset_id, referencedTableName=asset','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002708-2','jhipster','config/liquibase/changelog/20251217002708_added_entity_constraints_DocumentLink.xml','2025-12-22 23:12:02',35,'EXECUTED','9:a62c10a7196d1518ce533ca91305517c','addForeignKeyConstraint baseTableName=document_link, constraintName=fk_document_link__document_id, referencedTableName=document','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002709-2','jhipster','config/liquibase/changelog/20251217002709_added_entity_constraints_AssetMovementRequest.xml','2025-12-22 23:12:02',36,'EXECUTED','9:8267db3c14314005f8bda3d98cc6ece5','addForeignKeyConstraint baseTableName=asset_movement_request, constraintName=fk_asset_movement_request__asset_id, referencedTableName=asset; addForeignKeyConstraint baseTableName=asset_movement_request, constraintName=fk_asset_movement_request__re...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217002710-2','jhipster','config/liquibase/changelog/20251217002710_added_entity_constraints_LocationEvent.xml','2025-12-22 23:12:03',37,'EXECUTED','9:c069764724600bbcc9b72b99290c77a8','addForeignKeyConstraint baseTableName=location_event, constraintName=fk_location_event__asset_id, referencedTableName=asset; addForeignKeyConstraint baseTableName=location_event, constraintName=fk_location_event__sensor_id, referencedTableName=sen...','',NULL,'4.29.2',NULL,NULL,'6441519551'),('20251217004256','jhipster','config/liquibase/changelog/20251217004256_added_entity_EntityAuditEvent.xml','2025-12-22 23:12:03',38,'EXECUTED','9:20a5090c3960cbb5753c420a1747a2a0','createTable tableName=jhi_entity_audit_event; createIndex indexName=idx_entity_audit_event_entity_id, tableName=jhi_entity_audit_event; createIndex indexName=idx_entity_audit_event_entity_type, tableName=jhi_entity_audit_event; dropDefaultValue co...','',NULL,'4.29.2',NULL,NULL,'6441519551');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` VALUES (1,'Fiche_technique_Moteur_ENG306.pdf','application/pdf',245678,'/vault/assets/ENG306/fiche_technique.pdf','8f1a2c9e4b7d3a1c9f0eab45d21c7f9e','2025-12-16 09:05:00.000000','youssef.hassani'),(2,'Plan_implantation_Pompe_ENG307.dwg','application/acad',1345678,'/vault/assets/ENG307/plan_implantation.dwg','2a9e4f8c1d7b3e5a6c9f0b2d8e4a7f1c','2025-12-16 13:00:00.000000','imad.bougataya'),(3,'Certificat_conformite_ATEX_BROYAGE.pdf','application/pdf',356789,'/vault/assets/BROYAGE/atex_certificat.pdf','9c7a1b4e2d8f6a0c3e5b7d1f9a4c2e8b','2025-12-16 03:40:00.000000','hse.ocp'),(4,'Rapport_inspection_ENG306_2025-12-16.pdf','application/pdf',189234,'/vault/assets/ENG306/inspection_20251216.pdf','4e2b8c7d1a9f0e5c3b6a2f4d8e7c9a1b','2025-12-16 15:45:00.000000','maintenance.team'),(5,'Photo_installation_capteur_BLE.jpg','image/jpeg',845612,'/vault/assets/BROYAGE/photos/capteur_ble.jpg','6f3a9b8d1e2c7f0a4d5b9c8e7a6f2b1d','2025-12-16 16:22:00.000000','youssef.hassani'),(6,'Rapport_intervention_corrective.pdf','application/pdf',412345,'/vault/assets/ENG307/interventions/corrective_20251216.pdf','1c7e9a4b5f8d2a0e6c3b7d9f1a4e8b2c','2025-12-16 15:57:00.000000','maintenance.team'),(7,'Historique_maintenance_moteur.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',98234,'/vault/assets/ENG306/historique_maintenance.xlsx','5b9e2d8a7c1f3e4a0b6d9f2c8e7a1d4','2025-12-16 01:30:00.000000','planner.ocp'),(8,'Notice_fabricant_capteur_GNSS.pdf','application/pdf',276543,'/vault/assets/COMMON/capteurs/notice_gnss.pdf','8a7d1c2e9b5f4a6c3e0b7d8f9a1c4e2b','2025-12-16 13:15:00.000000','imad.bougataya'),(9,'Rapport_audit_securite_asset.pdf','application/pdf',512678,'/vault/assets/AUDIT/rapport_securite.pdf','3d7b1f9a5e2c8a0e4c6b9d8f1a2e7b4c','2025-12-16 21:15:00.000000','hse.ocp'),(10,'Schema_electrique_moteur_BROYAGE.pdf','application/pdf',768945,'/vault/assets/BROYAGE/schema_electrique.pdf','9f1c4e2a7b8d3e6a0c5b7f9d8a1e2c4b','2025-12-16 12:45:00.000000','engineering.ocp');
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
  `label` varchar(200) DEFAULT NULL,
  `linked_at` datetime(6) NOT NULL,
  `created_by` varchar(120) DEFAULT NULL,
  `created_date` datetime(6),
  `last_modified_by` varchar(120) DEFAULT NULL,
  `last_modified_date` datetime(6),
  `document_id` bigint NOT NULL,
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
INSERT INTO `document_link` VALUES (1,'MOVEMENT_REQUEST',1,'Ordre de déplacement signé','2025-12-16 19:25:00.000000','imad.bougataya','2025-12-16 19:25:00.000000','imad.bougataya','2025-12-16 19:30:00.000000',1),(2,'ASSET',1,'Fiche technique équipement','2025-12-16 16:30:00.000000','youssef.hassani','2025-12-16 16:30:00.000000','youssef.hassani','2025-12-16 16:30:00.000000',2),(3,'MAINTENANCE_EVENT',3,'Rapport d’intervention préventive','2025-12-16 08:10:00.000000','maintenance.team','2025-12-16 08:10:00.000000','maintenance.team','2025-12-16 08:20:00.000000',3),(4,'ASSET',4,'Certificat de conformité ATEX','2025-12-16 23:05:00.000000','hse.ocp','2025-12-16 23:05:00.000000','hse.ocp','2025-12-16 23:05:00.000000',4),(5,'MOVEMENT_REQUEST',2,'Demande de déplacement interne','2025-12-16 10:45:00.000000','planner.ocp','2025-12-16 10:45:00.000000','planner.ocp','2025-12-16 11:00:00.000000',5),(6,'MOVEMENT_REQUEST',6,'Autorisation de déplacement en cours','2025-12-16 03:30:00.000000','planner.ocp','2025-12-16 03:30:00.000000','planner.ocp','2025-12-16 09:00:00.000000',6),(7,'ASSET',7,'Historique de maintenance','2025-12-16 09:25:00.000000','maintenance.team','2025-12-16 09:25:00.000000','maintenance.team','2025-12-16 18:30:00.000000',7),(8,'ASSET',8,'Notice fabricant capteur GNSS','2025-12-16 12:05:00.000000','engineering.ocp','2025-12-16 12:05:00.000000','engineering.ocp','2025-12-16 12:05:00.000000',8),(9,'MOVEMENT_REQUEST',9,'PV de déplacement exécuté','2025-12-16 04:50:00.000000','imad.bougataya','2025-12-16 04:50:00.000000','imad.bougataya','2025-12-16 05:45:00.000000',9),(10,'MAINTENANCE_EVENT',10,'Compte rendu d’intervention corrective','2025-12-16 22:00:00.000000','maintenance.team','2025-12-16 22:00:00.000000','maintenance.team','2025-12-16 22:15:00.000000',10);
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
INSERT INTO `gateway` VALUES (1,'GW-JL-ENG-306-A','Gateway Engrais 306 – Atelier A','Kerlink','Wirnet iStation','A4:CF:12:9B:30:01','10.50.10.11','2025-10-12 00:00:00.000000',1,1,1),(2,'GW-JL-ENG-306-B','Gateway Engrais 306 – Atelier B','Kerlink','Wirnet iStation','A4:CF:12:9B:30:02','10.50.10.12','2025-10-12 00:00:00.000000',1,NULL,NULL),(3,'GW-JL-ENG-307-A','Gateway Engrais 307 – Atelier A','Kerlink','Wirnet iStation','A4:CF:12:9B:30:03','10.50.11.11','2025-10-15 00:00:00.000000',1,NULL,NULL),(4,'GW-JL-ENG-307-B','Gateway Engrais 307 – Atelier B','Kerlink','Wirnet iStation','A4:CF:12:9B:30:04','10.50.11.12','2025-10-15 00:00:00.000000',1,NULL,NULL),(5,'GW-JL-BRY-308-A','Gateway Broyage 308 – Primaire','Milesight','UG67','58:BF:25:8A:44:01','10.50.20.21','2025-10-20 00:00:00.000000',1,NULL,NULL),(6,'GW-JL-BRY-308-B','Gateway Broyage 308 – Secondaire','Milesight','UG67','58:BF:25:8A:44:02','10.50.20.22','2025-10-20 00:00:00.000000',1,NULL,NULL),(7,'GW-JL-BRY-308-C','Gateway Broyage 308 – Tertiaire','Milesight','UG67','58:BF:25:8A:44:03','10.50.20.23','2025-10-20 00:00:00.000000',1,NULL,NULL),(8,'GW-JL-UTIL-01','Gateway Utilités – Poste Central','Cisco','IR1101','3C:6A:A7:11:90:01','10.50.1.10','2025-09-28 00:00:00.000000',1,NULL,NULL),(9,'GW-JL-MAINT-01','Gateway Maintenance Mobile','Teltonika','RUTX11','60:02:92:AA:10:01','10.50.30.50','2025-11-02 00:00:00.000000',0,NULL,NULL),(10,'GW-JL-BACKUP','Gateway Secours Site Jorf Lasfar','Kerlink','Wirnet iStation','A4:CF:12:9B:30:FF','10.50.1.99','2025-09-01 00:00:00.000000',1,NULL,NULL);
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
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_ALERT_ACKNOWLEDGE'),('ROLE_ALERT_CLOSE'),('ROLE_ALERT_VIEW'),('ROLE_ASSET_ASSIGN_PRODUCTIONLINE'),('ROLE_ASSET_CREATE'),('ROLE_ASSET_DECOMMISSION'),('ROLE_ASSET_GEOFENCE_DEFINE'),('ROLE_ASSET_GEOFENCE_OVERRIDE'),('ROLE_ASSET_UPDATE'),('ROLE_ASSET_VIEW'),('ROLE_DOCUMENT_DELETE'),('ROLE_DOCUMENT_UPLOAD'),('ROLE_DOCUMENT_VIEW'),('ROLE_GATEWAY_CONFIGURE'),('ROLE_GATEWAY_VIEW'),('ROLE_LOCATION_VIEW_HISTORY'),('ROLE_LOCATION_VIEW_LIVE'),('ROLE_MAINTENANCE_ASSIGN'),('ROLE_MAINTENANCE_CANCEL'),('ROLE_MAINTENANCE_CLOSE'),('ROLE_MAINTENANCE_EXECUTE'),('ROLE_MAINTENANCE_PLAN'),('ROLE_MAINTENANCE_REQUEST_CREATE'),('ROLE_MAINTENANCE_VIEW'),('ROLE_MOVEMENT_REQUEST_CANCEL'),('ROLE_MOVEMENT_REQUEST_CREATE'),('ROLE_MOVEMENT_REQUEST_EXECUTE'),('ROLE_MOVEMENT_REQUEST_SIGN'),('ROLE_MOVEMENT_REQUEST_SUBMIT'),('ROLE_MOVEMENT_REQUEST_VALIDATE'),('ROLE_MOVEMENT_REQUEST_VIEW'),('ROLE_SENSOR_CONFIGURE'),('ROLE_SENSOR_VIEW'),('ROLE_SITE_CONFIGURE'),('ROLE_SITE_VIEW'),('ROLE_SYSTEM_EVENT_EXPORT'),('ROLE_SYSTEM_EVENT_VIEW'),('ROLE_USER'),('ROLE_USER_ASSIGN_AUTHORITIES'),('ROLE_USER_VIEW'),('ROLE_ZONE_CREATE'),('ROLE_ZONE_DELETE'),('ROLE_ZONE_UPDATE');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_entity_audit_event`
--

LOCK TABLES `jhi_entity_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_entity_audit_event` DISABLE KEYS */;
INSERT INTO `jhi_entity_audit_event` VALUES (1,'1','com.ailab.smartasset.domain.User','UPDATE','{\r\n  \"createdBy\" : \"system\",\r\n  \"createdDate\" : null,\r\n  \"lastModifiedBy\" : \"admin\",\r\n  \"lastModifiedDate\" : \"2025-12-25T20:05:42.349434900Z\",\r\n  \"id\" : 1,\r\n  \"login\" : \"admin\",\r\n  \"firstName\" : \"Administrator\",\r\n  \"lastName\" : \"Administrator\",\r\n  \"email\" : \"imad@ailab.ma\",\r\n  \"activated\" : true,\r\n  \"langKey\" : \"fr\",\r\n  \"imageUrl\" : \"\",\r\n  \"resetDate\" : null\r\n}',1,'admin','2025-12-25 19:05:42'),(2,'13','com.ailab.smartasset.domain.User','UPDATE','{\r\n  \"createdBy\" : \"system\",\r\n  \"createdDate\" : null,\r\n  \"lastModifiedBy\" : \"admin\",\r\n  \"lastModifiedDate\" : \"2025-12-25T20:08:06.546760400Z\",\r\n  \"id\" : 13,\r\n  \"login\" : \"operations.supervisor\",\r\n  \"firstName\" : \"Salma\",\r\n  \"lastName\" : \"Operations_Supervisor\",\r\n  \"email\" : \"y.hassani@ocpgroup.ma\",\r\n  \"activated\" : true,\r\n  \"langKey\" : \"fr\",\r\n  \"imageUrl\" : \"\",\r\n  \"resetDate\" : null\r\n}',1,'admin','2025-12-25 19:08:07'),(3,'10','com.ailab.smartasset.domain.User','UPDATE','{\r\n  \"createdBy\" : \"system\",\r\n  \"createdDate\" : null,\r\n  \"lastModifiedBy\" : \"admin\",\r\n  \"lastModifiedDate\" : \"2025-12-25T20:08:40.276964500Z\",\r\n  \"id\" : 10,\r\n  \"login\" : \"maintenance.tech\",\r\n  \"firstName\" : \"Nezha\",\r\n  \"lastName\" : \"Maintenance_Technicien\",\r\n  \"email\" : \"nrahmani@globalindustrie.ma\",\r\n  \"activated\" : true,\r\n  \"langKey\" : \"fr\",\r\n  \"imageUrl\" : \"\",\r\n  \"resetDate\" : null\r\n}',1,'admin','2025-12-25 19:08:40'),(4,'1','com.ailab.smartasset.domain.User','UPDATE','{\r\n  \"createdBy\" : \"system\",\r\n  \"createdDate\" : null,\r\n  \"lastModifiedBy\" : \"admin\",\r\n  \"lastModifiedDate\" : \"2025-12-25T20:42:37.349075900Z\",\r\n  \"id\" : 1,\r\n  \"login\" : \"admin\",\r\n  \"firstName\" : \"Administrator\",\r\n  \"lastName\" : \"Administrator\",\r\n  \"email\" : \"imad.bougataya@gmail.com\",\r\n  \"activated\" : true,\r\n  \"langKey\" : \"fr\",\r\n  \"imageUrl\" : \"\",\r\n  \"resetDate\" : null\r\n}',2,'admin','2025-12-25 19:42:37'),(5,'11','com.ailab.smartasset.domain.User','UPDATE','{\r\n  \"createdBy\" : \"system\",\r\n  \"createdDate\" : null,\r\n  \"lastModifiedBy\" : \"admin\",\r\n  \"lastModifiedDate\" : \"2025-12-25T20:43:05.652824500Z\",\r\n  \"id\" : 11,\r\n  \"login\" : \"maintenance.manager\",\r\n  \"firstName\" : \"Imad\",\r\n  \"lastName\" : \"Maintenance_Manager\",\r\n  \"email\" : \"imad@ailab.ma\",\r\n  \"activated\" : true,\r\n  \"langKey\" : \"fr\",\r\n  \"imageUrl\" : \"\",\r\n  \"resetDate\" : null\r\n}',1,'admin','2025-12-25 19:43:06');
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
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','imad.bougataya@gmail.com','',1,'fr',NULL,NULL,'system',NULL,NULL,'admin','2025-12-25 19:42:37'),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(10,'maintenance.tech','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Nezha','Maintenance_Technicien','nrahmani@globalindustrie.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'admin','2025-12-25 19:08:40'),(11,'maintenance.manager','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Imad','Maintenance_Manager','imad@ailab.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'admin','2025-12-25 19:43:06'),(12,'operations.operator','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Hamza','Operations_Operator','hamza.ops@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(13,'operations.supervisor','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Salma','Operations_Supervisor','y.hassani@ocpgroup.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'admin','2025-12-25 19:08:07'),(14,'security.officer','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Rachid','Security_Officer','rachid.security@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(15,'site.manager','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Nadia','Site_Manager','nadia.site@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(16,'asset.manager','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Omar','Asset_Manager','omar.asset@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(17,'logistics.agent','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Imane','Logistics_Agent','imane.logistics@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(18,'viewer.audit','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Adil','Audit_Viewer','adil.audit@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL),(19,'it.admin','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Soufiane','IT_Admin','soufiane.it@ocp.ma','',1,'fr',NULL,NULL,'system',NULL,NULL,'system',NULL);
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
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(10,'ROLE_ADMIN'),(13,'ROLE_ADMIN'),(1,'ROLE_ALERT_ACKNOWLEDGE'),(14,'ROLE_ALERT_ACKNOWLEDGE'),(15,'ROLE_ALERT_ACKNOWLEDGE'),(19,'ROLE_ALERT_ACKNOWLEDGE'),(1,'ROLE_ALERT_CLOSE'),(1,'ROLE_ALERT_VIEW'),(14,'ROLE_ALERT_VIEW'),(15,'ROLE_ALERT_VIEW'),(19,'ROLE_ALERT_VIEW'),(1,'ROLE_ASSET_ASSIGN_PRODUCTIONLINE'),(16,'ROLE_ASSET_ASSIGN_PRODUCTIONLINE'),(1,'ROLE_ASSET_CREATE'),(16,'ROLE_ASSET_CREATE'),(1,'ROLE_ASSET_DECOMMISSION'),(1,'ROLE_ASSET_GEOFENCE_DEFINE'),(15,'ROLE_ASSET_GEOFENCE_DEFINE'),(16,'ROLE_ASSET_GEOFENCE_DEFINE'),(1,'ROLE_ASSET_GEOFENCE_OVERRIDE'),(1,'ROLE_ASSET_UPDATE'),(16,'ROLE_ASSET_UPDATE'),(1,'ROLE_ASSET_VIEW'),(12,'ROLE_ASSET_VIEW'),(15,'ROLE_ASSET_VIEW'),(16,'ROLE_ASSET_VIEW'),(17,'ROLE_ASSET_VIEW'),(18,'ROLE_ASSET_VIEW'),(1,'ROLE_DOCUMENT_DELETE'),(1,'ROLE_DOCUMENT_UPLOAD'),(10,'ROLE_DOCUMENT_UPLOAD'),(16,'ROLE_DOCUMENT_UPLOAD'),(1,'ROLE_DOCUMENT_VIEW'),(10,'ROLE_DOCUMENT_VIEW'),(16,'ROLE_DOCUMENT_VIEW'),(18,'ROLE_DOCUMENT_VIEW'),(1,'ROLE_GATEWAY_CONFIGURE'),(19,'ROLE_GATEWAY_CONFIGURE'),(1,'ROLE_GATEWAY_VIEW'),(19,'ROLE_GATEWAY_VIEW'),(1,'ROLE_LOCATION_VIEW_HISTORY'),(1,'ROLE_LOCATION_VIEW_LIVE'),(12,'ROLE_LOCATION_VIEW_LIVE'),(14,'ROLE_LOCATION_VIEW_LIVE'),(1,'ROLE_MAINTENANCE_ASSIGN'),(11,'ROLE_MAINTENANCE_ASSIGN'),(1,'ROLE_MAINTENANCE_CANCEL'),(1,'ROLE_MAINTENANCE_CLOSE'),(1,'ROLE_MAINTENANCE_EXECUTE'),(10,'ROLE_MAINTENANCE_EXECUTE'),(13,'ROLE_MAINTENANCE_EXECUTE'),(1,'ROLE_MAINTENANCE_PLAN'),(11,'ROLE_MAINTENANCE_PLAN'),(13,'ROLE_MAINTENANCE_PLAN'),(1,'ROLE_MAINTENANCE_REQUEST_CREATE'),(11,'ROLE_MAINTENANCE_REQUEST_CREATE'),(13,'ROLE_MAINTENANCE_REQUEST_CREATE'),(1,'ROLE_MAINTENANCE_VIEW'),(10,'ROLE_MAINTENANCE_VIEW'),(11,'ROLE_MAINTENANCE_VIEW'),(13,'ROLE_MAINTENANCE_VIEW'),(18,'ROLE_MAINTENANCE_VIEW'),(1,'ROLE_MOVEMENT_REQUEST_CANCEL'),(13,'ROLE_MOVEMENT_REQUEST_CANCEL'),(1,'ROLE_MOVEMENT_REQUEST_CREATE'),(12,'ROLE_MOVEMENT_REQUEST_CREATE'),(13,'ROLE_MOVEMENT_REQUEST_CREATE'),(17,'ROLE_MOVEMENT_REQUEST_CREATE'),(1,'ROLE_MOVEMENT_REQUEST_EXECUTE'),(13,'ROLE_MOVEMENT_REQUEST_EXECUTE'),(1,'ROLE_MOVEMENT_REQUEST_SIGN'),(13,'ROLE_MOVEMENT_REQUEST_SIGN'),(1,'ROLE_MOVEMENT_REQUEST_SUBMIT'),(13,'ROLE_MOVEMENT_REQUEST_SUBMIT'),(1,'ROLE_MOVEMENT_REQUEST_VALIDATE'),(13,'ROLE_MOVEMENT_REQUEST_VALIDATE'),(1,'ROLE_MOVEMENT_REQUEST_VIEW'),(12,'ROLE_MOVEMENT_REQUEST_VIEW'),(13,'ROLE_MOVEMENT_REQUEST_VIEW'),(17,'ROLE_MOVEMENT_REQUEST_VIEW'),(18,'ROLE_MOVEMENT_REQUEST_VIEW'),(1,'ROLE_SENSOR_CONFIGURE'),(1,'ROLE_SENSOR_VIEW'),(1,'ROLE_SITE_CONFIGURE'),(13,'ROLE_SITE_CONFIGURE'),(19,'ROLE_SITE_CONFIGURE'),(1,'ROLE_SITE_VIEW'),(13,'ROLE_SITE_VIEW'),(15,'ROLE_SITE_VIEW'),(19,'ROLE_SITE_VIEW'),(1,'ROLE_SYSTEM_EVENT_EXPORT'),(13,'ROLE_SYSTEM_EVENT_EXPORT'),(1,'ROLE_SYSTEM_EVENT_VIEW'),(11,'ROLE_SYSTEM_EVENT_VIEW'),(15,'ROLE_SYSTEM_EVENT_VIEW'),(18,'ROLE_SYSTEM_EVENT_VIEW'),(19,'ROLE_SYSTEM_EVENT_VIEW'),(1,'ROLE_USER'),(2,'ROLE_USER'),(10,'ROLE_USER'),(11,'ROLE_USER'),(12,'ROLE_USER'),(13,'ROLE_USER'),(14,'ROLE_USER'),(15,'ROLE_USER'),(16,'ROLE_USER'),(17,'ROLE_USER'),(18,'ROLE_USER'),(19,'ROLE_USER'),(1,'ROLE_USER_ASSIGN_AUTHORITIES'),(13,'ROLE_USER_ASSIGN_AUTHORITIES'),(19,'ROLE_USER_ASSIGN_AUTHORITIES'),(1,'ROLE_USER_VIEW'),(19,'ROLE_USER_VIEW'),(1,'ROLE_ZONE_CREATE'),(19,'ROLE_ZONE_CREATE'),(1,'ROLE_ZONE_DELETE'),(19,'ROLE_ZONE_DELETE'),(1,'ROLE_ZONE_UPDATE'),(14,'ROLE_ZONE_UPDATE'),(15,'ROLE_ZONE_UPDATE'),(19,'ROLE_ZONE_UPDATE');
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
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `accuracy_meters` double DEFAULT NULL,
  `speed_kmh` double DEFAULT NULL,
  `gnss_constellation` varchar(50) DEFAULT NULL,
  `raw_payload` varchar(4000) DEFAULT NULL,
  `asset_id` bigint NOT NULL,
  `sensor_id` bigint DEFAULT NULL,
  `matched_site_id` bigint DEFAULT NULL,
  `matched_zone_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_event__asset_id` (`asset_id`),
  KEY `fk_location_event__sensor_id` (`sensor_id`),
  KEY `fk_location_event__matched_site_id` (`matched_site_id`),
  KEY `fk_location_event__matched_zone_id` (`matched_zone_id`),
  CONSTRAINT `fk_location_event__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `fk_location_event__matched_site_id` FOREIGN KEY (`matched_site_id`) REFERENCES `site` (`id`),
  CONSTRAINT `fk_location_event__matched_zone_id` FOREIGN KEY (`matched_zone_id`) REFERENCES `zone` (`id`),
  CONSTRAINT `fk_location_event__sensor_id` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_event`
--

LOCK TABLES `location_event` WRITE;
/*!40000 ALTER TABLE `location_event` DISABLE KEYS */;
INSERT INTO `location_event` VALUES (1,'GNSS','2025-12-16 12:43:00.000000',92,0,0,33.119845,-8.610234,6.5,0,'GPS','{\"tracker\":\"GNSS\",\"fix\":\"3D\",\"sat\":9}',1,NULL,1,1),(2,'GNSS','2025-12-16 21:11:41.000000',88,NULL,NULL,33.12021,-8.608912,7.8,0,'MULTI','{\"tracker\":\"GNSS\",\"fix\":\"3D\",\"sat\":11}',2,NULL,NULL,NULL),(3,'MANUAL','2025-12-16 04:53:13.000000',70,NULL,NULL,33.11845,-8.61287,35,0,'','{\"source\":\"manual\",\"comment\":\"position saisie opérateur\"}',3,NULL,NULL,NULL),(4,'BLE','2025-12-16 05:27:05.000000',85,-62,-8,33.12103,-8.60755,4.2,0,'','{\"beacon\":\"BLE-ZONE-ENGRAIS-306\"}',4,NULL,NULL,NULL),(5,'BLE','2025-12-16 15:48:00.000000',90,-58,-8,33.12241,-8.60598,3.6,0,'','{\"beacon\":\"BLE-ZONE-ENGRAIS-307\"}',5,NULL,NULL,NULL),(6,'BLE','2025-12-16 01:09:17.000000',78,-71,-12,33.11798,-8.61412,6.9,0,'','{\"beacon\":\"BLE-BROYAGE-308\"}',6,NULL,NULL,NULL),(7,'MANUAL','2025-12-16 07:53:43.000000',65,NULL,NULL,33.11675,-8.61543,45,0,'','{\"source\":\"manual\",\"comment\":\"déplacement atelier maintenance\"}',7,NULL,NULL,NULL),(8,'BLE','2025-12-16 11:22:47.000000',87,-60,-8,33.12188,-8.60631,5.1,0,'','{\"beacon\":\"BLE-ZONE-ENGRAIS-307\"}',8,NULL,NULL,NULL),(9,'GNSS','2025-12-16 01:20:22.000000',91,NULL,NULL,33.11912,-8.61154,9.4,2.3,'GALILEO','{\"tracker\":\"GNSS\",\"fix\":\"2D\",\"sat\":7}',9,NULL,NULL,NULL),(10,'MANUAL','2025-12-16 09:16:24.000000',72,NULL,NULL,33.11892,-8.60987,30,0,'','{\"source\":\"manual\",\"comment\":\"position déclarée chef d’équipe\"}',10,NULL,NULL,NULL);
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
  `asset_id` bigint NOT NULL,
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
INSERT INTO `maintenance_event` VALUES (1,'INSPECTION','PLANNED','2025-12-16 09:15:00.000000','2025-12-17 08:00:00.000000',NULL,NULL,'Inspection périodique moteur','Inspection visuelle et contrôle électrique d’un moteur de ligne Engrais 306','Y. El Amrani',0,0.00,'Inspection programmée',1),(2,'INSPECTION','PLANNED','2025-12-16 14:05:00.000000','2025-12-17 13:30:00.000000',NULL,NULL,'Inspection capteur vibration','Contrôle du capteur de vibration sur équipement critique','A. Benjelloun',0,0.00,'Préventif',2),(3,'PREVENTIVE','IN_PROGRESS','2025-12-16 11:45:00.000000','2025-12-16 14:00:00.000000','2025-12-16 14:10:00.000000',NULL,'Maintenance préventive moteur','Graissage, resserrage et contrôle thermique moteur ABB','M. Chafik',45,1250.00,'Intervention en cours',3),(4,'CORRECTIVE','DONE','2025-12-16 06:30:00.000000','2025-12-16 07:00:00.000000','2025-12-16 07:05:00.000000','2025-12-16 08:20:00.000000','Remplacement roulement moteur','Remplacement d’un roulement défectueux suite vibration excessive','H. Berrada',75,4800.00,'Panne corrigée',4),(5,'REVISION','DONE','2025-12-16 03:40:00.000000','2025-12-16 04:00:00.000000','2025-12-16 04:05:00.000000','2025-12-16 07:30:00.000000','Révision complète moteur','Démontage, nettoyage et contrôle général du moteur','S. El Idrissi',205,9200.00,'Révision annuelle',5),(6,'CORRECTIVE','REQUESTED','2025-12-17 00:20:00.000000',NULL,NULL,NULL,'Défaut capteur température','Alerte température élevée détectée par le système','—',0,0.00,'En attente de planification',6),(7,'REVISION','IN_PROGRESS','2025-12-16 20:50:00.000000','2025-12-16 21:00:00.000000','2025-12-16 21:10:00.000000',NULL,'Révision réducteur','Contrôle engrenages et remplacement huile réducteur','R. Ait Lahcen',120,6500.00,'Zone Broyage',7),(8,'REVISION','CANCELLED','2025-12-16 22:00:00.000000','2025-12-17 09:00:00.000000',NULL,NULL,'Révision planifiée annulée','Intervention annulée suite indisponibilité équipement','—',0,0.00,'À reprogrammer',8),(9,'CORRECTIVE','PLANNED','2025-12-16 23:00:00.000000','2025-12-17 01:00:00.000000',NULL,NULL,'Dysfonctionnement moteur convoyeur','Baisse de performance détectée sur convoyeur Engrais 307','K. Amine',60,3500.00,'Intervention de nuit',9),(10,'REVISION','DONE','2025-12-16 07:45:00.000000','2025-12-16 08:00:00.000000','2025-12-16 08:05:00.000000','2025-12-16 09:30:00.000000','Révision moteur auxiliaire','Maintenance d’un moteur auxiliaire hors production','N. Ouali',85,2100.00,'RAS',10);
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
  `zone_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_production_line__code` (`code`),
  KEY `fk_production_line__zone_id` (`zone_id`),
  CONSTRAINT `fk_production_line__zone_id` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production_line`
--

LOCK TABLES `production_line` WRITE;
/*!40000 ALTER TABLE `production_line` DISABLE KEYS */;
INSERT INTO `production_line` VALUES (1,'ENG-306-A','Ligne Engrais 306 – Atelier A','Ligne de production engrais – Atelier A du site OCP Jorf Lasfar',1),(2,'ENG-306-B','Ligne Engrais 306 – Atelier B','Ligne de production engrais – Atelier B du site OCP Jorf Lasfar',1),(3,'ENG-306-C','Ligne Engrais 306 – Atelier C','Ligne de production engrais – Atelier C du site OCP Jorf Lasfar',1),(4,'ENG-307-A','Ligne Engrais 307 – Atelier A','Ligne de production engrais – Atelier A du site OCP Jorf Lasfar',2),(5,'ENG-307-B','Ligne Engrais 307 – Atelier B','Ligne de production engrais – Atelier B du site OCP Jorf Lasfar',2),(6,'ENG-307-C','Ligne Engrais 307 – Atelier C','Ligne de production engrais – Atelier C du site OCP Jorf Lasfar',2),(7,'BRY-308-A','Ligne Broyage 308 – Primaire','Ligne de broyage primaire – Site OCP Jorf Lasfar',3),(8,'BRY-308-B','Ligne Broyage 308 – Secondaire','Ligne de broyage secondaire – Site OCP Jorf Lasfar',3),(9,'BRY-308-C','Ligne Broyage 308 – Tertiaire','Ligne de broyage tertiaire – Site OCP Jorf Lasfar',3),(10,'UTIL-COMMON','Ligne Utilités & Services','Équipements communs, utilités et auxiliaires du site OCP Jorf Lasfar',3);
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
  `external_id` varchar(120) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `unit` varchar(30) DEFAULT NULL,
  `min_threshold` decimal(21,2) DEFAULT NULL,
  `max_threshold` decimal(21,2) DEFAULT NULL,
  `installed_at` datetime(6),
  `active` tinyint NOT NULL,
  `asset_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_sensor__external_id` (`external_id`),
  KEY `fk_sensor__asset_id` (`asset_id`),
  CONSTRAINT `fk_sensor__asset_id` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,'BLE_TAG','PRS-HYD-306-01','Beacon BLE','',0.00,250.00,'2025-12-16 13:25:00.000000',1,1),(2,'GNSS_TRACKER','VIB-MOT-307-02','Tracker GNSS','',0.00,25.00,'2025-12-16 06:21:00.000000',1,2),(3,'HUMIDITY','HUM-AMB-PLANT-01','Capteur humidité ambiante','%RH',10.00,90.00,'2025-12-16 00:28:24.000000',1,10),(4,'TEMPERATURE','TMP-BRG-308-01','Capteur température palier','°C',-20.00,120.00,'2025-12-16 09:00:29.000000',1,3),(5,'LIQUID_LEVEL','LVL-TANK-306-01','Capteur niveau cuve','% ',0.00,100.00,'2025-12-16 13:02:54.000000',0,1),(6,'GNSS_TRACKER','GNSS-MOB-ASF-01','Tracker GNSS équipement mobile','°',0.00,0.00,'2025-12-16 04:59:18.000000',1,5),(7,'BLE_TAG','BLE-ZONE-307-05','Tag BLE localisation indoor','RSSI',-100.00,-20.00,'2025-12-16 05:56:26.000000',1,6),(8,'OIL_LEVEL','OIL-LVL-GEAR-01','Capteur niveau huile','% ',0.00,100.00,'2025-12-16 20:04:05.000000',1,4),(9,'TEMPERATURE','TMP-MOT-306-07','Capteur température moteur','°C',-20.00,180.00,'2025-12-16 18:10:46.000000',1,2),(10,'TEMPERATURE','TMP-AMB-JLF-01','Capteur température ambiante site','°C',-10.00,60.00,'2025-12-16 03:41:33.000000',1,10);
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
  `sensor_id` bigint NOT NULL,
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
INSERT INTO `sensor_measurement` VALUES (1,'2025-12-16 22:10:32.000000',145.60,'GOOD','IOT_GATEWAY',1),(2,'2025-12-16 10:15:48.000000',18.40,'GOOD','EDGE_DEVICE',2),(3,'2025-12-16 12:15:14.000000',6.20,'GOOD','EDGE_DEVICE',3),(4,'2025-12-16 14:12:16.000000',78.90,'GOOD','SCADA',4),(5,'2025-12-16 13:49:29.000000',42.10,'GOOD','IOT_GATEWAY',5),(6,'2025-12-16 20:15:29.000000',3.80,'GOOD','EDGE_DEVICE',6),(7,'2025-12-16 11:48:29.000000',-67.00,'GOOD','BLE_RECEIVER',7),(8,'2025-12-16 23:54:57.000000',91.30,'GOOD','SCADA',8),(9,'2025-12-16 13:01:35.000000',64.50,'WARNING','IOT_GATEWAY',9),(10,'2025-12-16 02:51:42.000000',110.20,'ALARM','EDGE_DEVICE',10);
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
  `center_lat` double DEFAULT NULL,
  `center_lon` double DEFAULT NULL,
  `radius_meters` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_site__code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

LOCK TABLES `site` WRITE;
/*!40000 ALTER TABLE `site` DISABLE KEYS */;
INSERT INTO `site` VALUES (1,'JORF_LASFAR','Jorf Lasfar','Site industriel OCP de Jorf Lasfar',33.1167,-8.6167,5000);
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
  `severity` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(120) DEFAULT NULL,
  `correlation_id` varchar(64) DEFAULT NULL,
  `payload` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_event`
--

LOCK TABLES `system_event` WRITE;
/*!40000 ALTER TABLE `system_event` DISABLE KEYS */;
INSERT INTO `system_event` VALUES (1,'SYSTEM_STARTUP','INFO','SYSTEM','Démarrage du système SmartAssetCore','2025-12-16 00:01:12.000000','system','SYS-BOOT-20251216-0001',''),(2,'API_REQUEST','INFO','API','Requête API traitée avec succès','2025-12-16 02:38:28.000000','api-gateway','API-REQ-20251216-0238',''),(3,'USER_LOGIN','INFO','UI','Connexion utilisateur réussie','2025-12-16 13:55:04.000000','user-interface','UI-LOGIN-20251216-1355',''),(4,'UI_ERROR','ERROR','UI','Erreur inattendue dans l’interface utilisateur','2025-12-16 02:30:06.000000','user-interface','UI-ERR-20251216-0230','component=AssetForm'),(5,'GATEWAY_ERROR','ERROR','GATEWAY','Échec de communication avec un service aval','2025-12-16 14:47:11.000000','api-gateway','GW-ERR-20251216-1447','service=AssetService'),(6,'API_VALIDATION_WARNING','WARNING','API','Données reçues partiellement invalides','2025-12-16 17:30:55.000000','api-gateway','API-WARN-20251216-1730','field=asset_code'),(7,'ESIGN_EVENT','INFO','ESIGN','Document envoyé pour signature électronique','2025-12-16 23:24:42.000000','esign-service','ESIGN-REQ-20251216-2324','documentId=DOC-7845'),(8,'GATEWAY_HEALTHCHECK','INFO','GATEWAY','Vérification de santé du gateway réussie','2025-12-16 06:58:27.000000','api-gateway','GW-HC-20251216-0658',''),(9,'SYSTEM_EXCEPTION','ERROR','SYSTEM','Exception système critique détectée','2025-12-16 01:32:40.000000','system','SYS-EX-20251216-0132','exception=NullPointerException'),(10,'UI_WARNING','WARNING','UI','Temps de réponse élevé détecté sur l’UI','2025-12-16 05:00:00.000000','monitoring','UI-WARN-20251216-0500','responseTimeMs=1800');
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
  `center_lat` double DEFAULT NULL,
  `center_lon` double DEFAULT NULL,
  `radius_meters` int DEFAULT NULL,
  `site_id` bigint NOT NULL,
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
INSERT INTO `zone` VALUES (1,'ENG_306','Engrais 306','Unité de production engrais – Ligne 306',33.1212,-8.6075,400,1),(2,'ENG_307','Engrais 307','Unité de production engrais – Ligne 307',33.1195,-8.6058,400,1),(3,'BROYAGE','Broyage','Zone de broyage matières premières',33.1168,-8.6123,500,1);
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-26  7:25:45
