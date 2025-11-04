# Этап 1: Сборка JAR (с Maven внутри контейнера)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем pom.xml
COPY pom.xml .

# Кэшируем зависимости (ускорит пересборку)
RUN mvn dependency:go-offline

# Копируем исходники
COPY src ./src

# Собираем JAR
RUN mvn clean package -DskipTests

# Этап 2: Запуск
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем готовый JAR
COPY --from=build /app/target/*.jar app.jar

# Порт
EXPOSE 8080

# Запуск
ENTRYPOINT ["java", "-jar", "app.jar"]