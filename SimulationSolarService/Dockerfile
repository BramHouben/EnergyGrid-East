FROM openjdk:11-jdk-slim

ADD ./target/simulationsolarservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/simulationsolarservice.jar"]

EXPOSE 8120