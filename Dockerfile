# ---- Estágio 1: build ----
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY escala/pom.xml .
RUN mvn -B -q dependency:go-offline
COPY escala/src ./src
# Os testes rodam no GitHub Actions (CI); aqui só empacota.
RUN mvn -B -q -DskipTests package

# ---- Estágio 2: execução ----
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
