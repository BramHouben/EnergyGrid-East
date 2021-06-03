FROM openjdk:11-jdk-slim
ADD ./target/websocketservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/websocketservice.jar"]


EXPOSE 8443