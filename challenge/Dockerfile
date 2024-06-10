FROM gradle:8.8.0-jdk-21-and-22 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon || return 0

COPY src ./src
RUN gradle clean build -x test --no-daemon

FROM openjdk:22-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/my-spring-boot-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/my-spring-boot-app.jar"]