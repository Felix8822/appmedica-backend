# Imagen base con Java 21 (clave)
FROM eclipse-temurin:21-jdk

# Set work directory
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Compilar usando Maven wrapper
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# Ejecutar el JAR
CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
