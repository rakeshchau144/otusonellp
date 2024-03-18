FROM ubuntu:latest
LABEL authors="Rakesh chaudhary"


ENTRYPOINT ["top", "-b"]
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY otusone_backend/target/*.jar Backend.jar
ENTRYPOINT ["java","-jar","/app.jar"]
