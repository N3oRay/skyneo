CREATE DATABASE mydb;

CREATE USER 'readuser'@'%' IDENTIFIED BY 'passwordforreaduser';
GRANT SELECT ON mydb.* TO 'readuser'@'%';

CREATE USER 'writeuser'@'%' IDENTIFIED BY 'passwordforwriteuser';
GRANT INSERT, UPDATE, SELECT, DELETE ON mydb.* TO 'writeuser'@'%';