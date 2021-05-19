FROM openjdk:11-jdk-slim

ADD ./target/energybalanceservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/energybalanceservice.jar"]
EXPOSE 8160