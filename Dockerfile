# Build frontend (Angular)
FROM node:20-alpine as build-frontend


# Build Cache
## WORKDIR /tmp/
# Copy dependencies
## COPY bootstrap-main.zip /tmp/
## RUN npm cache add bootstrap-main.zip


WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN rm -rf node_modules package-lock.json
RUN npm config set registry https://registry.npmjs.org/
RUN npm cache clean --force
# RUN npm install bootstrap --offline
RUN npm install bootstrap
# RUN npm install chalk
RUN npm install --legacy-peer-deps --offline
RUN npm install @ng-icons/core @ng-icons/heroicons @ng-icons/feather-icons


# RUN npm ci
COPY frontend/ ./
RUN npm run build --prod

# Build backend (Spring Boot)
FROM maven:3.9.6-eclipse-temurin-21-alpine as build-backend
VOLUME ~/.m2
WORKDIR /app
COPY backend/pom.xml backend/
COPY backend/src backend/src
COPY --from=build-frontend /app/frontend/dist/ ./backend/src/main/resources/static/
WORKDIR /app/backend

# Restore dependencies (utilise le cache Docker si pom.xml n'a pas changé)

# Utilise le cache local Maven pour accélérer les builds suivants
RUN mvn dependency:go-offline

# Compile le projet (utilise le cache Maven)
RUN mvn clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build-backend /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
