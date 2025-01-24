FROM amazoncorretto:21-alpine3.20

LABEL authors="amikhalachkin"
WORKDIR /momomo
ARG JAR_FILE=momomo-worker-app/build/libs/momomo-worker-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/momomo/app.jar"]
