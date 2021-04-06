FROM openjdk:11-jdk-slim

ADD ./target/authenticationservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/authenticationservice.jar"]
EXPOSE 8100