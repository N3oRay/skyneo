

** Build with docker compose:

sudo docker-compose up

** test BDD MYSQL

sudo docker exec -it skyneo_mysql_1 bash
$ mysql -u root -p

** sample MySQL
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| DashboardAdmin     |
| information_schema |
| mydb               |
| mysql              |
| performance_schema |
| sys                |
+--------------------+

SHOW FULL TABLES FROM mydb;

+----------------+------------+
| Tables_in_mydb | Table_type |
+----------------+------------+
| choices        | BASE TABLE |
| polls          | BASE TABLE |
| random_words   | BASE TABLE |
| roles          | BASE TABLE |
| user_roles     | BASE TABLE |
| users          | BASE TABLE |
| votes          | BASE TABLE |
+----------------+------------+
7 rows in set (0.01 sec)

SHOW FULL TABLES FROM DashborardAdmin;

*** For build on ligne:

sudo docker build -t skyrim-hello . && sudo docker run -p 8080:8080 skyrim-hello

*** or
*** For build local offline:

export DOCKER_BUILDKIT=1

sudo docker build -t skyrim-hello . && sudo docker run -v ~/.m2:/root/.m2 -p 8080:8080 skyrim-hello

*** run simple ***
sudo docker run -p 8080:8080 skyrim-hello

*** Notice Docker commande ***

sudo docker exec CONTAINER_ID pwd

*** Notice Git

echo "# skyneo" >> README.md

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin https://github.com/N3oRay/skyneo.git

git push -u origin main
