
FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/patients-0.0.1-SNAPSHOT.jar mediscreen-patients.jar
ENTRYPOINT ["java", "-jar", "mediscreen-patients.jar"]