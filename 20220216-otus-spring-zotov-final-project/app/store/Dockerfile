FROM maven:3.8.4-openjdk-11-slim as base
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

FROM base as package
ADD ./src/ $PROJECT_DIR/src
RUN mvn -B package -DskipTests

FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ENV APP_NAME=store-app.jar
COPY --from=package $PROJECT_DIR/target/$APP_NAME $PROJECT_DIR/

EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/opt/project/store-app.jar"]
