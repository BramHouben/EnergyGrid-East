FROM openjdk:11-jdk-slim

ADD ./target/EnergyBalanceService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/energyBalanceService.jar"]
EXPOSE 8160