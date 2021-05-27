FROM openjdk:11-jdk-slim

ADD ./target/simulationnuclearservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/simulationnuclearservice.jar"]
EXPOSE 8180