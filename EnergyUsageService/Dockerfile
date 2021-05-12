FROM openjdk:11-jdk-slim

ADD ./target/energyusageservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/energyusageservice.jar"]
EXPOSE 8170