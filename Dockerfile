# Use the official Gradle image with JDK 17 as the base image
FROM gradle:7.3.3-jdk17 as builder
# Set the working directory
WORKDIR /app
# Copy the Gradle configuration files first for better caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew

# Then copy the rest of the application
COPY src src
# Build the application
RUN ./gradlew clean build -x test
# Use OpenJDK 17 for the runtime stage
FROM openjdk:17-jdk-alpine
# Set the working directory
WORKDIR /app
# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar
# Expose the application on port 8080
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]