#dev iets met health check gelezen:  https://blog.couchbase.com/docker-health-check-keeping-containers-healthy/

FROM openjdk:11-jdk-slim

ADD ./target/EurekaServer.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/eurekaserver.jar"]

EXPOSE 8761