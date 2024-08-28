# Docker 镜像构建
# @author <a href="https://blog.csdn.net/guojiaqi_">oldGj</a>
# @from <a href="https://github.com/guoJiaQi-123/TYUT-SmartViewBI-backend">GitHub地址</a>
FROM maven:3.8.1-jdk-8-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
CMD ["java","-jar","/app/target/tyut-bi-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]