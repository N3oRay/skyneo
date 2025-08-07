# PROJET SOS 
POC complet sous Docker compose avec MySQL 8 pour la base de données, un front avec Angular / BootStrap.
Et un backend sous Spring Boot avec Liquibase.

#** Build with docker compose:

sudo docker-compose up
# ** Verification des dockers en cours d'execution

sudo docker ps -s


#** test BDD MYSQL

sudo docker exec -it mysqldb bash
$ mysql -u root -p

# or

$ mysql -u dashuser -p

#** sample MySQL
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

SHOW FULL TABLES FROM DashboardAdmin;
#** sample MySQL Select
mysql> select * from DashboardAdmin.sos_alimentation;
mysql> select * from DashboardAdmin.sos_laptop;
mysql> select * from DashboardAdmin.sos_pc;
mysql> select * from DashboardAdmin.sos_phone;
mysql> select * from DashboardAdmin.sos_routeur;
mysql> select * from DashboardAdmin.sos_serveur;




#*** For build on ligne:

sudo docker build -t skyrim-hello . && sudo docker run -p 8080:8080 skyrim-hello

#*** or
#*** For build local offline:

export DOCKER_BUILDKIT=1

sudo docker build -t skyrim-hello . && sudo docker run -v ~/.m2:/root/.m2 -p 8080:8080 skyrim-hello

#*** run simple ***
sudo docker run -p 8080:8080 skyrim-hello

#*** Notice Docker commande ********************************************************

sudo docker exec CONTAINER_ID pwd

Lorsqu'on supprime un conteneur penser à utiliser l'option -v qui permet de supprimer les volumes associés à un conteneur.

#Pour supprimer tous les conteneurs qui ne tournent pas on peut utiliser la commande suivante:

docker rm -v $(docker ps -aqf status=exited)

#Recréé un conteneur est assez rapide du moment que son image est disponible. Ce qui nous amène vers le nettoyage des images inutiles.
#Supprimer les images inutiles
#J'appelle image "inutile" une image "intermédiaire" qui sert dans la construction d'une image "finale" et qui n'est donc jamais utilisé pour créer un conteneur.
#On peut supprimer ces images avec la commande suivante:

docker rmi $(docker images -qf dangling=true)

#ou

sudo docker rmi $(sudo docker images -qf dangling=true)

#Souvent indispensable après un docker pull.
#Supprimer les volumes orphelins
#Un volume orphelin est un volume pour lequel son conteneur associé a été supprimé sans l'option -v. Pour supprimer ces volumes on a la commande suivante:

docker volume rm $(docker volume ls -qf dangling=true)

#*** Notice Git *********************************************************************

echo "# skyneo" >> README.md

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin https://github.com/N3oRay/skyneo.git

git push -u origin main

#************* Notice API ******************************************
Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id
http://127.0.0.1:8080/api/sos/laptop?page=1&size=10&sort=id
http://127.0.0.1:8080/api/sos/pc?page=1&size=100&sort=consum
http://127.0.0.1:8080/api/sos/phone?page=1&size=100&sort=consum
http://127.0.0.1:8080/api/sos/routeur?page=1&size=100&sort=name
http://127.0.0.1:8080/api/sos/serveur?page=1&size=100&sort=name

#Notes:
domain: backend/src/main/java/com/sos/obs/decc/domain/SosAlim.java
repository : backend/src/main/java/com/sos/obs/decc/repository/SosAlimRepository.java
rest : backend/src/main/java/com/sos/obs/decc/web/rest/SosAlimResource.java

#************* Notice API Secure ( Admin) ***************************
http://127.0.0.1:8080/
http://127.0.0.1:8080/api/
http://127.0.0.1:8080/api/admin
http://127.0.0.1:8080/api/admin/animation
http://127.0.0.1:8080/management/audits      GET param : fromDate, toDate
http://127.0.0.1:8080/management/audits      GET  /audits/:id
http://127.0.0.1:8080/api/admin/authority
http://127.0.0.1:8080/api/admin/center    /delete/{centerId}                    /add
http://127.0.0.1:8080/api/admin/dashboard             /delete/{dashboardId}
http://127.0.0.1:8080/api/admin/flashInfo
http://127.0.0.1:8080/api/admin/indicator
http://127.0.0.1:8080/api/admin/link
http://127.0.0.1:8080/management
http://127.0.0.1:8080/api/admin/relationship
http://127.0.0.1:8080/api/admin/screen
http://127.0.0.1:8080/api/admin/scrollingMessage
http://127.0.0.1:8080/api/admin/skillGroup
http://127.0.0.1:8080/api/admin/sprite
http://127.0.0.1:8080/api/admin


POST
/authenticate

/api/admin

/api/admin/users

