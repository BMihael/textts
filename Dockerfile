FROM openjdk:11
WORKDIR /app
COPY target/textts-1.0-SNAPSHOT.jar /app/textts-1.0-SNAPSHOT.jar
CMD ["java", "-jar", "textts-1.0-SNAPSHOT.jar"]