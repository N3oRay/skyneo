CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE USER 'readuser'@'%' IDENTIFIED BY 'passwordforreaduser';
GRANT SELECT ON mydb.* TO 'readuser'@'%';

CREATE USER 'writeuser'@'%' IDENTIFIED BY 'passwordforwriteuser';
GRANT INSERT, UPDATE, SELECT, DELETE ON mydb.* TO 'writeuser'@'%';

CREATE USER 'admin'@'%' IDENTIFIED BY 'admin@123';
GRANT ALL PRIVILEGES ON pyapplicationdb.* TO 'admin'@'%';
FLUSH PRIVILEGES;

-- Create a sample table and insert data
CREATE TABLE IF NOT EXISTS random_words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    word VARCHAR(255) NOT NULL
);

INSERT INTO random_words (word) VALUES ('example');



CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_role_id` (`role_id`),
  CONSTRAINT `fk_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `polls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(140) NOT NULL,
  `expiration_date_time` datetime NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `choices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(40) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_choices_poll_id` (`poll_id`),
  CONSTRAINT `fk_choices_poll_id` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `votes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  `choice_id` bigint(20) NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_votes_user_id` (`user_id`),
  KEY `fk_votes_poll_id` (`poll_id`),
  KEY `fk_votes_choice_id` (`choice_id`),
  CONSTRAINT `fk_votes_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_votes_poll_id` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`id`),
  CONSTRAINT `fk_votes_choice_id` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP DATABASE IF EXISTS DashboardAdmin;
CREATE DATABASE IF NOT EXISTS DashboardAdmin;

USE DashboardAdmin;

-- CREATE USER 'dashuser'@'%' IDENTIFIED BY 'dashuser';
GRANT CREATE, INSERT, UPDATE, SELECT, DELETE ON DashboardAdmin.* TO 'dashuser'@'%';
GRANT CREATE, INSERT, UPDATE, SELECT, DELETE ON *.* TO 'dashuser'@'%';
GRANT ALL ON DashboardAdmin.* TO 'dashuser'@'%' WITH GRANT OPTION;
-- GRANT ALL ON DashboardAdmin.* TO 'dashuser'@'localhost' WITH GRANT OPTION;
-- ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'R00t+';
-- ALTER USER 'dashuser'@'localhost' IDENTIFIED WITH mysql_native_password BY 'dashuser';
FLUSH PRIVILEGES;
