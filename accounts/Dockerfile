ARG profile
ARG proyecto=accounts

FROM maven:3.8-jdk-8 AS build
ARG profile
ARG proyecto
RUN echo PROFILE=${profile}
RUN echo PROYECTO=${proyecto}
COPY commons /home/commons
RUN mvn -f /home/commons/pom.xml install 
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -P${profile}
FROM openjdk:8-jre-alpine
ARG profile
ARG proyecto
ENV JAVA_OPTS="-XX:+UseSerialGC -Xss512k -XX:MaxRAM=1024M"
RUN echo PROFILE2=${profile}
RUN echo PROYECTO2=${proyecto}
COPY --from=build /home/app/target/${proyecto}.jar ${proyecto}.jar
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/accounts.jar" ]