FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /workspace/target/*.jar circlesubi-trustgraph-neo4j.jar
EXPOSE 8889
ENTRYPOINT ["java","-jar","circlesubi-trustgraph-neo4j.jar"]