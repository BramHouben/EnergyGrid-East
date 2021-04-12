FROM openjdk:11-jdk-slim

ADD ./target/weatherservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/weatherservice.jar"]
EXPOSE 8110