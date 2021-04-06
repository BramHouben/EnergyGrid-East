FROM openjdk:11-jdk-slim
ADD ./target/solarparkservice.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/solarparkservice.jar"]


EXPOSE 8090