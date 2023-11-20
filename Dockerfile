FROM openjdk:17-jdk

WORKDIR /app

LABEL maintainer="damian" \
      version="1.0" \
      description="Docker image for the web-search-service"

COPY target/web-search-service-0.0.1-SNAPSHOT.jar /app/web-search-service.jar

EXPOSE 8086

CMD ["java", "-jar", "/app/web-search-service.jar"]