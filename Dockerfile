FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=./target/*.jar

COPY ${JAR_FILE} /app/demo-spring-boot3.jar

EXPOSE 8080

CMD ["java", "-jar", "demo-spring-boot3.jar"]