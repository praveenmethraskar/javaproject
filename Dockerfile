FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy project
COPY . .

# make mvnw executable
RUN chmod +x mvnw

# build WAR
RUN ./mvnw clean package -DskipTests

# rename WAR to a fixed name
RUN cp target/*.war app.war

EXPOSE 8080

CMD ["java","-jar","app.war"]
