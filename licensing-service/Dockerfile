# Usin initial image for Docker with 'build' name
FROM openjdk:17-slim AS build
# Settin current working directory for any RUN, ENTRYPOINT, COPY... instructions
WORKDIR application
# Addining additional Metadata
LABEL maintainer="Vadim Volkov <volvadvit@gmail.com>"
# Adding additional arguments for Docker's builder
ARG JAR_FILE=target/*.jar
# Saving licensing-service:0.0.1-SNAPSHOT.jar as app.jar
COPY ${JAR_FILE} application.jar
# Extracting app's layers from .jar file to optimize image's build
RUN java -Djarmode=layertools -jar application.jar extract

# Creating new image to combine only required SpringBoot app's layers
FROM openjdk:17-slim
WORKDIR application
# Copying unpacked app's layers to container layers
ARG DEPENDENCY=/target/dependency
COPY --from=build application/dependencies/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/application/ ./
# Running SpringBoot app
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

# sudo docker build . --tag ostock/licensing-service
#   or  sudo mvn package dockerfile:build
#   or  sudo mvn sprin-boot:build-image

# sudo docker run -it -p8081:8081 ostock/licensing-service:latest