FROM openjdk:11-jre-slim-sid

EXPOSE 8080 8081

ENV JAVA_OPTS="-server -XX:InitialRAMPercentage=80.0 -XX:MaxRAMPercentage=80.0 -XX:+HeapDumpOnOutOfMemoryError"

COPY target/*.jar donus-code-challenge.jar

ADD entrypoint.sh /
ENTRYPOINT ["/entrypoint.sh"]
