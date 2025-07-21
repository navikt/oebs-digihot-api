# FROM navikt/java:18
FROM gcr.io/distroless/java21

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV TZ="Europe/Oslo"
ENTRYPOINT ["java","-jar","app.jar"]
