FROM openjdk:21
COPY target/*.jar order-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]
EXPOSE 8081