FROM openjdk:11-jdk

WORKDIR /product-ms

COPY target/*.jar /product-ms/app.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -jar app.jar --server.port=$PORT
