FROM gradle:5.6-jdk11 as build
WORKDIR /app
COPY ./src ./src
COPY build.gradle .
RUN gradle build
FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN echo "$(ls -l app/build/libs/)"
COPY --from=build app/build/libs/app-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
