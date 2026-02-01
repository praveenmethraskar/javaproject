FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# 🔑 FIX: give execute permission
RUN chmod +x mvnw

# build application
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/*.war"]
