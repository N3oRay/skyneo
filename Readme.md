sudo docker build -t skyrim-hello . && sudo docker run -p 8080:8080 skyrim-hello


echo "# skyneo" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/N3oRay/skyneo.git
git push -u origin main
