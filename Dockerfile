FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy source code
COPY . .

# build jar inside container
RUN ./mvnw clean package -DskipTests

# run jar
CMD ["java","-jar","target/*.jar"]

EXPOSE 8080
