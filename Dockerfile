FROM amazoncorretto:17-alpine3.20

LABEL authors="amikhalachkin"
ARG JAR_FILE=momomo-worker-app/build/libs/momomo-worker-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
