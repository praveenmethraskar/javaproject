FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy full project
COPY . .

# build the project (creates target/*.war)
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# run WAR with embedded Tomcat
CMD ["java","-jar","target/*.war"]
