FROM maven:3.9.5-amazoncorretto-17 as build

WORKDIR /app

COPY . .

RUN mvn clean package -X -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build ./app/target/*.jar ./meu-almoxarifado-backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "meu-almoxarifado-backend.jar"]
