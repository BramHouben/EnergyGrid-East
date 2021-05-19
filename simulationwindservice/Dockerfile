FROM openjdk:11-jdk-slim

ADD ./target/simulationwindservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/simulationwindservice.jar"]

EXPOSE 8140