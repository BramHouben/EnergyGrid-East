FROM openjdk:11-jdk-slim
ADD ./target/userservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/userservice.jar"]

EXPOSE 8130