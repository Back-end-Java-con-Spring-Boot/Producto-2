# Compilación con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos los archivos de configuración y el código fuente
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto saltando los tests para agilizar el proceso
RUN mvn clean package -DskipTests

# Imagen de ejecución (ligera)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/alquila-tus-vehiculos-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto estándar de Spring Boot
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]