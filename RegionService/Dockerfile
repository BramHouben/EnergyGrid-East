FROM openjdk:11-jdk-slim
ADD ./target/regionservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/regionservice.jar"]

EXPOSE 8150