FROM eclipse-temurin:11-alpine
RUN mkdir /opt/app
COPY build/libs/saga_poc__car_reservation_service-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/saga_poc__car_reservation_service-0.0.1-SNAPSHOT.jar"]