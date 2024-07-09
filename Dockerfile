# Stage 1: Build the application
FROM ubuntu:latest AS build

# Install dependencies (OpenJDK 21 and Maven)
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the JAR file and necessary folders
COPY ./target /app/target

# Stage 2: Create the final image
FROM adoptopenjdk/openjdk21:alpine-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file and necessary folders from the build stage
COPY --from=build /app/target /app/target

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/target/mavenproject1-1.0-SNAPSHOT.jar"]
