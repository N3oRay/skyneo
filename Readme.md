

** Build with docker compose:

sudo docker-compose up

** test BDD MYSQL

sudo docker exec -it test_skyneo_mysql_1 bash
$ mysql -u root -p

*** For build on ligne:

sudo docker build -t skyrim-hello . && sudo docker run -p 8080:8080 skyrim-hello

*** or
*** For build local offline:

export DOCKER_BUILDKIT=1

sudo docker build -t skyrim-hello . && sudo docker run -v ~/.m2:/root/.m2 -p 8080:8080 skyrim-hello

*** run simple ***
sudo docker run -p 8080:8080 skyrim-hello

*** Notice Git

echo "# skyneo" >> README.md

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin https://github.com/N3oRay/skyneo.git

git push -u origin main
