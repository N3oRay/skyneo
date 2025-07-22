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
