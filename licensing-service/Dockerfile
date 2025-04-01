# Perform the extraction in a separate builder container
FROM openjdk:17-slim AS build
# Settin current working directory for any RUN, ENTRYPOINT, COPY... instructions
WORKDIR application
# Addining additional Metadata
LABEL maintainer="Vadim Volkov <volvadvit@gmail.com>"
# This points to the build jar file in the target forlder
# Adjust this to 'build/libs/*.jar' if you're using Gradle
ARG JAR_FILE=target/*.jar
# Copy the jar file to the working directory and rename it to application.jar
COPY ${JAR_FILE} application.jar
# Extract the jar file using an efficient layout
RUN java -Djarmode=layertools -jar application.jar extract

# This is the runtime container
FROM openjdk:17-slim
WORKDIR application
# Copy the extracted jar contents from the builder container into the working directory in the runtime container
# Every copy step creates a new docker layer
# This allows docker to only pull the changes it really needs
ARG DEPENDENCY=/target/dependency
COPY --from=build application/dependencies/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/application/ ./
# Start the application jar - this is not the source jar used by the loader
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and CDS friendly
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]