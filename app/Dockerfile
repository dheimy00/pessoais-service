## Stage 1: Build the app
#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#
## Copia todo o código para dentro da imagem
#COPY . .
#
## Faz o build do projeto sem rodar os testes
#RUN mvn clean package -DskipTests
#
## Stage 2: Runtime image
#FROM eclipse-temurin:17-jre
#WORKDIR /app
#
## Copia o JAR compilado da etapa anterior
#COPY --from=build /app/target/*.jar app.jar
#
## Expondo a porta da aplicação
#EXPOSE 8080
#
## Comando para iniciar a aplicação
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]


FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
