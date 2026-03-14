CREATE DATABASE IF NOT EXISTS ecommerce;

use ecommerce;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firebase_uid` varchar(128) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `firebase_uid` (`firebase_uid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB;

INSERT INTO `users` VALUES (1,'PDGUR0VHNFdDn7vf8Uc2N7bLrHK2','admin@test.com','2026-02-21 11:24:20',1),(2,'dEhXqlVnqBXZ0Wcj9O6uYp2KRxO2','sumit@test.com','2026-02-21 13:31:47',1),(4,'ZJGMdSLORNYSl5JUFXCaAU9AAl73','anant@test.com','2026-02-21 14:29:56',1),(5,'Qm1QfZWbUNPFsVdMTryPcvr5Ayr1','geeta@test.com','2026-02-21 14:34:38',1),(6,'IQNcy2SWFHN8WNvJ9vcn704zUZO2','tarun@test.com','2026-02-21 15:15:33',1),(7,'5qVfIzLr77gHwU2OOlsQQC1xwP92','tester@test.com','2026-03-03 15:24:36',1);

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB;

INSERT INTO `roles` VALUES (2,'ROLE_ADMIN'),(1,'ROLE_USER');

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO `user_roles` VALUES (2,1),(4,1),(5,1),(6,1),(7,1),(1,2);

CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `brand` varchar(120) DEFAULT NULL,
  `description` text,
  `category` varchar(120) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `thumbnail_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT INTO `products` VALUES (101,'Nike Air Running T-Shirt','nike','Breathable running tee','tshirts','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1521572163474-6864f9cf17ab'),(102,'Adidas Performance Tee','adidas','Sports performance tshirt','tshirts','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1503342217505-b0a15ec3261c'),(103,'Puma Gym Training Tee','puma','Workout ready tshirt','tshirts','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1489987707025-afc232f7ea0f'),(104,'Levis Classic Denim Jacket','levis','Iconic denim jacket','jackets','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1539533113208-f6df8cc8b543'),(105,'Zara Oversized Hoodie','zara','Street style hoodie','hoodies','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(106,'H&M Cotton Casual Shirt','hm','Comfort cotton shirt','shirts','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1596755094514-f87e34085b2c'),(107,'Nike Sports Joggers','nike','Athletic jogger pants','joggers','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1556306535-38febf6782e7'),(108,'Adidas Track Pants','adidas','Slim fit track pants','joggers','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1562157873-818bc0726f68'),(109,'Puma Active Shorts','puma','Training shorts','shorts','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(110,'Levis Slim Fit Jeans','levis','Slim denim jeans','jeans','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(111,'Zara Casual Summer Dress','zara','Lightweight summer dress','dresses','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1509631179647-0177331693ae'),(112,'H&M Floral Dress','hm','Floral casual dress','dresses','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1490481651871-ab68de25d43d'),(113,'Nike Women Training Tee','nike','Training tshirt','tshirts','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1518611012118-696072aa579a'),(114,'Adidas Yoga Pants','adidas','Comfort yoga pants','joggers','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1518310383802-640c2de311b2'),(115,'Puma Sports Bra','puma','High support sports bra','activewear','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1606902965551-dce093cda6e7'),(116,'Levis Denim Skirt','levis','Blue denim skirt','skirts','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1520975916090-3105956dac38'),(117,'Zara Linen Shirt','zara','Premium linen shirt','shirts','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1485968579580-b6d095142e6e'),(118,'H&M Casual Hoodie','hm','Daily hoodie','hoodies','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1556905055-8f358a7a47b2'),(119,'Nike Running Shorts','nike','Light running shorts','shorts','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(120,'Adidas Graphic Tee','adidas','Logo graphic tshirt','tshirts','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1512436991641-6745cdb1723f'),(121,'Puma Lifestyle Sneakers','puma','Urban sneakers','shoes','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1528701800489-20be9c1b8e7d'),(122,'Nike Air Street Sneakers','nike','Streetwear sneakers','shoes','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77'),(123,'Adidas Ultraboost Sneakers','adidas','Running sneakers','shoes','men','2026-03-08 08:26:33','https://images.unsplash.com/photo-1600185365483-26d7a4cc7519'),(124,'Zara Leather Handbag','zara','Elegant handbag','bags','women','2026-03-08 08:26:33','https://images.unsplash.com/photo-1584917865442-de89df76afd3'),(125,'H&M Canvas Backpack','hm','Everyday backpack','bags','unisex','2026-03-08 08:26:33','https://images.unsplash.com/photo-1509762774605-f07235a08f1f');

CREATE TABLE `product_skus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `sku_code` varchar(120) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sku_code` (`sku_code`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_skus_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB;

INSERT INTO `product_skus` VALUES (1,101,NULL,'S','Black',999.00,15,'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab'),(2,101,NULL,'M','Black',999.00,11,'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab'),(3,101,NULL,'L','White',1099.00,8,'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab'),(4,101,NULL,'XL','White',1099.00,8,'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab'),(5,102,NULL,'S','Blue',1099.00,10,'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c'),(6,102,NULL,'M','Blue',1099.00,9,'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c'),(7,102,NULL,'L','Black',1199.00,10,'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c'),(8,102,NULL,'XL','Black',1199.00,7,'https://images.unsplash.com/photo-1503342217505-b0a15ec3261c'),(9,103,NULL,'S','Grey',999.00,11,'https://images.unsplash.com/photo-1489987707025-afc232f7ea0f'),(10,103,NULL,'M','Grey',999.00,10,'https://images.unsplash.com/photo-1489987707025-afc232f7ea0f'),(11,103,NULL,'L','Red',1099.00,8,'https://images.unsplash.com/photo-1489987707025-afc232f7ea0f'),(12,103,NULL,'XL','Red',1099.00,7,'https://images.unsplash.com/photo-1489987707025-afc232f7ea0f'),(13,104,NULL,'S','Blue',3999.00,8,'https://images.unsplash.com/photo-1539533113208-f6df8cc8b543'),(14,104,NULL,'M','Blue',3999.00,6,'https://images.unsplash.com/photo-1539533113208-f6df8cc8b543'),(15,104,NULL,'L','Black',4199.00,5,'https://images.unsplash.com/photo-1539533113208-f6df8cc8b543'),(16,104,NULL,'XL','Black',4199.00,4,'https://images.unsplash.com/photo-1539533113208-f6df8cc8b543'),(17,105,NULL,'S','Beige',2999.00,10,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(18,105,NULL,'M','Beige',2999.00,9,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(19,105,NULL,'L','Grey',3099.00,8,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(20,105,NULL,'XL','Grey',3099.00,7,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(21,106,NULL,'S','White',1499.00,12,'https://images.unsplash.com/photo-1596755094514-f87e34085b2c'),(22,106,NULL,'M','White',1499.00,10,'https://images.unsplash.com/photo-1596755094514-f87e34085b2c'),(23,106,NULL,'L','Blue',1599.00,8,'https://images.unsplash.com/photo-1596755094514-f87e34085b2c'),(24,106,NULL,'XL','Blue',1599.00,7,'https://images.unsplash.com/photo-1596755094514-f87e34085b2c'),(25,107,NULL,'S','Black',999.00,15,'https://images.unsplash.com/photo-1556306535-38febf6782e7'),(26,107,NULL,'M','Black',999.00,12,'https://images.unsplash.com/photo-1556306535-38febf6782e7'),(27,107,NULL,'L','White',1099.00,10,'https://images.unsplash.com/photo-1556306535-38febf6782e7'),(28,107,NULL,'XL','White',1099.00,8,'https://images.unsplash.com/photo-1556306535-38febf6782e7'),(29,108,NULL,'S','Blue',1099.00,10,'https://images.unsplash.com/photo-1562157873-818bc0726f68'),(30,108,NULL,'M','Blue',1099.00,9,'https://images.unsplash.com/photo-1562157873-818bc0726f68'),(31,108,NULL,'L','Black',1199.00,10,'https://images.unsplash.com/photo-1562157873-818bc0726f68'),(32,108,NULL,'XL','Black',1199.00,7,'https://images.unsplash.com/photo-1562157873-818bc0726f68'),(33,109,NULL,'S','Grey',999.00,11,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(34,109,NULL,'M','Grey',999.00,10,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(35,109,NULL,'L','Red',1099.00,8,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(36,109,NULL,'XL','Red',1099.00,7,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(37,110,NULL,'S','Blue',3999.00,8,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(38,110,NULL,'M','Blue',3999.00,6,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(39,110,NULL,'L','Black',4199.00,5,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(40,110,NULL,'XL','Black',4199.00,4,'https://images.unsplash.com/photo-1541099649105-f69ad21f3246'),(41,111,NULL,'S','Beige',2999.00,10,'https://images.unsplash.com/photo-1509631179647-0177331693ae'),(42,111,NULL,'M','Beige',2999.00,9,'https://images.unsplash.com/photo-1509631179647-0177331693ae'),(43,111,NULL,'L','Grey',3099.00,8,'https://images.unsplash.com/photo-1509631179647-0177331693ae'),(44,111,NULL,'XL','Grey',3099.00,7,'https://images.unsplash.com/photo-1509631179647-0177331693ae'),(45,112,NULL,'S','White',1499.00,12,'https://images.unsplash.com/photo-1490481651871-ab68de25d43d'),(46,112,NULL,'M','White',1499.00,10,'https://images.unsplash.com/photo-1490481651871-ab68de25d43d'),(47,112,NULL,'L','Blue',1599.00,8,'https://images.unsplash.com/photo-1490481651871-ab68de25d43d'),(48,112,NULL,'XL','Blue',1599.00,7,'https://images.unsplash.com/photo-1490481651871-ab68de25d43d'),(49,113,NULL,'S','Black',999.00,15,'https://images.unsplash.com/photo-1518611012118-696072aa579a'),(50,113,NULL,'M','Black',999.00,12,'https://images.unsplash.com/photo-1518611012118-696072aa579a'),(51,113,NULL,'L','White',1099.00,10,'https://images.unsplash.com/photo-1518611012118-696072aa579a'),(52,113,NULL,'XL','White',1099.00,8,'https://images.unsplash.com/photo-1518611012118-696072aa579a'),(53,114,NULL,'S','Blue',1099.00,10,'https://images.unsplash.com/photo-1518310383802-640c2de311b2'),(54,114,NULL,'M','Blue',1099.00,9,'https://images.unsplash.com/photo-1518310383802-640c2de311b2'),(55,114,NULL,'L','Black',1199.00,10,'https://images.unsplash.com/photo-1518310383802-640c2de311b2'),(56,114,NULL,'XL','Black',1199.00,7,'https://images.unsplash.com/photo-1518310383802-640c2de311b2'),(57,115,NULL,'S','Grey',999.00,11,'https://images.unsplash.com/photo-1606902965551-dce093cda6e7'),(58,115,NULL,'M','Grey',999.00,10,'https://images.unsplash.com/photo-1606902965551-dce093cda6e7'),(59,115,NULL,'L','Red',1099.00,8,'https://images.unsplash.com/photo-1606902965551-dce093cda6e7'),(60,115,NULL,'XL','Red',1099.00,7,'https://images.unsplash.com/photo-1606902965551-dce093cda6e7'),(61,116,NULL,'S','Blue',3999.00,8,'https://images.unsplash.com/photo-1520975916090-3105956dac38'),(62,116,NULL,'M','Blue',3999.00,6,'https://images.unsplash.com/photo-1520975916090-3105956dac38'),(63,116,NULL,'L','Black',4199.00,5,'https://images.unsplash.com/photo-1520975916090-3105956dac38'),(64,116,NULL,'XL','Black',4199.00,4,'https://images.unsplash.com/photo-1520975916090-3105956dac38'),(65,117,NULL,'S','Beige',2999.00,10,'https://images.unsplash.com/photo-1485968579580-b6d095142e6e'),(66,117,NULL,'M','Beige',2999.00,9,'https://images.unsplash.com/photo-1485968579580-b6d095142e6e'),(67,117,NULL,'L','Grey',3099.00,8,'https://images.unsplash.com/photo-1485968579580-b6d095142e6e'),(68,117,NULL,'XL','Grey',3099.00,7,'https://images.unsplash.com/photo-1485968579580-b6d095142e6e'),(69,118,NULL,'S','White',1499.00,12,'https://images.unsplash.com/photo-1556905055-8f358a7a47b2'),(70,118,NULL,'M','White',1499.00,9,'https://images.unsplash.com/photo-1556905055-8f358a7a47b2'),(71,118,NULL,'L','Blue',1599.00,8,'https://images.unsplash.com/photo-1556905055-8f358a7a47b2'),(72,118,NULL,'XL','Blue',1599.00,7,'https://images.unsplash.com/photo-1556905055-8f358a7a47b2'),(73,119,NULL,'S','Black',999.00,15,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(74,119,NULL,'M','Black',999.00,12,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(75,119,NULL,'L','White',1099.00,10,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(76,119,NULL,'XL','White',1099.00,8,'https://images.unsplash.com/photo-1503342394128-c104d54dba01'),(77,120,NULL,'S','Blue',1099.00,10,'https://images.unsplash.com/photo-1512436991641-6745cdb1723f'),(78,120,NULL,'M','Blue',1099.00,9,'https://images.unsplash.com/photo-1512436991641-6745cdb1723f'),(79,120,NULL,'L','Black',1199.00,10,'https://images.unsplash.com/photo-1512436991641-6745cdb1723f'),(80,120,NULL,'XL','Black',1199.00,7,'https://images.unsplash.com/photo-1512436991641-6745cdb1723f'),(81,121,NULL,'S','Grey',999.00,11,'https://images.unsplash.com/photo-1528701800489-20be9c1b8e7d'),(82,121,NULL,'M','Grey',999.00,10,'https://images.unsplash.com/photo-1528701800489-20be9c1b8e7d'),(83,121,NULL,'L','Red',1099.00,8,'https://images.unsplash.com/photo-1528701800489-20be9c1b8e7d'),(84,121,NULL,'XL','Red',1099.00,7,'https://images.unsplash.com/photo-1528701800489-20be9c1b8e7d'),(85,122,NULL,'S','Blue',3999.00,8,'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77'),(86,122,NULL,'M','Blue',3999.00,6,'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77'),(87,122,NULL,'L','Black',4199.00,5,'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77'),(88,122,NULL,'XL','Black',4199.00,4,'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77'),(89,123,NULL,'S','Beige',2999.00,10,'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519'),(90,123,NULL,'M','Beige',2999.00,9,'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519'),(91,123,NULL,'L','Grey',3099.00,8,'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519'),(92,123,NULL,'XL','Grey',3099.00,7,'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519'),(93,124,NULL,'S','White',1499.00,12,'https://images.unsplash.com/photo-1584917865442-de89df76afd3'),(94,124,NULL,'M','White',1499.00,10,'https://images.unsplash.com/photo-1584917865442-de89df76afd3'),(95,124,NULL,'L','Blue',1599.00,8,'https://images.unsplash.com/photo-1584917865442-de89df76afd3'),(96,124,NULL,'XL','Blue',1599.00,7,'https://images.unsplash.com/photo-1584917865442-de89df76afd3'),(97,125,NULL,'S','Black',999.00,15,'https://images.unsplash.com/photo-1509762774605-f07235a08f1f'),(98,125,NULL,'M','Black',999.00,12,'https://images.unsplash.com/photo-1509762774605-f07235a08f1f'),(99,125,NULL,'L','White',1099.00,10,'https://images.unsplash.com/photo-1509762774605-f07235a08f1f'),(100,125,NULL,'XL','White',1099.00,8,'https://images.unsplash.com/photo-1509762774605-f07235a08f1f');

CREATE TABLE `product_attributes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `attribute_key` varchar(120) DEFAULT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_attributes_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB;

INSERT INTO `product_attributes` VALUES (1,101,'fabric','polyester'),(2,101,'fit','regular'),(3,102,'fabric','denim'),(4,102,'fit','regular'),(5,103,'fabric','cotton'),(6,103,'style','street'),(7,104,'fabric','denim'),(8,104,'fit','slim'),(9,105,'fabric','linen'),(10,105,'style','casual'),(11,106,'fabric','cotton'),(12,106,'pattern','floral'),(13,107,'fabric','polyester'),(14,107,'support','high'),(15,108,'material','mesh'),(16,108,'sole','rubber'),(17,109,'material','leather'),(18,109,'style','fashion'),(19,110,'material','canvas'),(20,110,'capacity','20L'),(21,111,'fabric','polyester'),(22,111,'fit','regular'),(23,112,'fabric','denim'),(24,112,'fit','regular'),(25,113,'fabric','cotton'),(26,113,'style','street'),(27,114,'fabric','denim'),(28,114,'fit','slim'),(29,115,'fabric','linen'),(30,115,'style','casual'),(31,116,'fabric','cotton'),(32,116,'pattern','floral'),(33,117,'fabric','polyester'),(34,117,'support','high'),(35,118,'material','mesh'),(36,118,'sole','rubber'),(37,119,'material','leather'),(38,119,'style','fashion'),(39,120,'material','canvas'),(40,120,'capacity','20L'),(41,121,'fabric','polyester'),(42,121,'fit','regular'),(43,122,'fabric','denim'),(44,122,'fit','regular'),(45,123,'fabric','cotton'),(46,123,'style','street'),(47,124,'fabric','denim'),(48,124,'fit','slim'),(49,125,'fabric','linen'),(50,125,'style','casual');

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `total_amount` double DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB;

INSERT INTO `orders` VALUES (1,7,1499,'PLACED','2026-03-08 08:50:48'),(2,7,2198,'PLACED','2026-03-10 06:58:38'),(3,7,999,'PLACED','2026-03-12 08:41:25');

CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `sku_id` bigint DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `sku_id` (`sku_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`sku_id`) REFERENCES `product_skus` (`id`)
) ENGINE=InnoDB;

INSERT INTO `order_items` VALUES (1,1,70,'H&M Casual Hoodie','hm','M','White',1499,1),(2,2,3,'Nike Air Running T-Shirt','nike','L','White',1099,2),(3,3,2,'Nike Air Running T-Shirt','nike','M','Black',999,1);


CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cart_id` bigint DEFAULT NULL,
  `sku_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_id` (`cart_id`),
  KEY `sku_id` (`sku_id`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`sku_id`) REFERENCES `product_skus` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `wishlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  KEY `fk_wishlist_product` (`product_id`),
  CONSTRAINT `fk_wishlist_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_wishlist_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO `wishlist` VALUES (3,7,103,'2026-03-08 08:58:17'),(5,7,119,'2026-03-08 13:24:29'),(6,7,102,'2026-03-12 08:40:58');

