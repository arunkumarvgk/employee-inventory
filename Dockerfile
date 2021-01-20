FROM openjdk:8u272-jdk-slim
MAINTAINER arunkumarvgk@gmail.com, arunkumars546@gmail.com
WORKDIR /app/work
COPY target/emp-inventory-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "emp-inventory-0.0.1-SNAPSHOT.jar"]
