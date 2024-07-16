# Use a Java runtime as base image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/task-server-0.0.1-SNAPSHOT.jar /app/task-server-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "task-server-0.0.1-SNAPSHOT.jar"]