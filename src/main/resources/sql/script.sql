DROP DATABASE IF EXISTS `chatop`;
CREATE DATABASE `chatop`;
USE `chatop`;

-- Création de la table USERS
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE IF NOT EXISTS `USERS` (
                                       `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       `email` varchar(255) NOT NULL,
                                       `name` varchar(255),
                                       `password` varchar(255),
                                       `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
                                       `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Création de la table RENTALS
DROP TABLE IF EXISTS `RENTALS`;
CREATE TABLE IF NOT EXISTS `RENTALS` (
                                         `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         `name` varchar(255),
                                         `surface` DECIMAL(10, 2),
                                         `price` DECIMAL(10, 2),
                                         `picture` varchar(255),
                                         `description` varchar(2000),
                                         `owner_id` BIGINT NOT NULL,
                                         `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
                                         `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         CONSTRAINT `fk_owner` FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE
);

-- Création de la table MESSAGES
DROP TABLE IF EXISTS `MESSAGES`;
CREATE TABLE IF NOT EXISTS `MESSAGES` (
                                          `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          `rental_id` BIGINT,
                                          `user_id` BIGINT,
                                          `message` varchar(2000),
                                          `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
                                          `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          CONSTRAINT `fk_rental` FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`) ON DELETE CASCADE,
                                          CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE
);

-- Création d'un index unique sur l'email dans la table USERS
CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);
