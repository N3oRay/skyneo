

** Build with docker compose:

sudo docker-compose up

** test BDD MYSQL

sudo docker exec -it skyneo_mysql_1 bash
$ mysql -u root -p

or

$ mysql -u dashuser -p

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

SHOW FULL TABLES FROM DashboardAdmin;

*** For build on ligne:

sudo docker build -t skyrim-hello . && sudo docker run -p 8080:8080 skyrim-hello

*** or
*** For build local offline:

export DOCKER_BUILDKIT=1

sudo docker build -t skyrim-hello . && sudo docker run -v ~/.m2:/root/.m2 -p 8080:8080 skyrim-hello

*** run simple ***
sudo docker run -p 8080:8080 skyrim-hello

*** Notice Docker commande ********************************************************

sudo docker exec CONTAINER_ID pwd

Lorsqu'on supprime un conteneur penser à utiliser l'option -v qui permet de supprimer les volumes associés à un conteneur.

Pour supprimer tous les conteneurs qui ne tournent pas on peut utiliser la commande suivante:

docker rm -v $(docker ps -aqf status=exited)

Recréé un conteneur est assez rapide du moment que son image est disponible. Ce qui nous amène vers le nettoyage des images inutiles.
Supprimer les images inutiles

J'appelle image "inutile" une image "intermédiaire" qui sert dans la construction d'une image "finale" et qui n'est donc jamais utilisé pour créer un conteneur.

On peut supprimer ces images avec la commande suivante:

docker rmi $(docker images -qf dangling=true)

Souvent indispensable après un docker pull.
Supprimer les volumes orphelins

Un volume orphelin est un volume pour lequel son conteneur associé a été supprimé sans l'option -v. Pour supprimer ces volumes on a la commande suivante:

docker volume rm $(docker volume ls -qf dangling=true)

*** Notice Git *********************************************************************

echo "# skyneo" >> README.md

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin https://github.com/N3oRay/skyneo.git

git push -u origin main
