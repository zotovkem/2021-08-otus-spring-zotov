FROM maven:3.8.4-openjdk-11-slim
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn -B install -DskipTests

FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ENV APP_NAME=library-app.jar
COPY --from=0 $PROJECT_DIR/target/$APP_NAME $PROJECT_DIR/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/project/library-app.jar"]
