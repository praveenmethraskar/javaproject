FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy full project
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build WAR
RUN ./mvnw clean package -DskipTests

# Copy WAR to a fixed name (NO wildcards at runtime)
RUN cp target/*.war app.war

EXPOSE 8080

# Run using fixed filename
CMD ["java", "-jar", "app.war"]