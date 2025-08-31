FROM eclipse-temurin:21-jre
WORKDIR /app
COPY backend/target/backend-0.0.1-SNAPSHOT.jar finance-tracker-v1.0.jar
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "finance-tracker-v1.0.jar"]
