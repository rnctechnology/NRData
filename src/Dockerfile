FROM openjdk:8-jdk-alpine

EXPOSE 8080

#Set app home folder
ENV APP_HOME /app
#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
ENV JAVA_OPTS=""
#Create base app folder
RUN mkdir $APP_HOME
#Create folder to save configuration files
RUN mkdir $APP_HOME/config
#Create folder with application logs
RUN mkdir $APP_HOME/log

VOLUME $APP_HOME/log
VOLUME $APP_HOME/config
WORKDIR $APP_HOME

COPY src/wait-for-it.sh wait-for-it.sh
COPY target/nrdata-0.0.1-SNAPSHOT.jar nrdata.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar nrdata.jar" ]

#Second option using shell form:
#ENTRYPOINT exec java $JAVA_OPTS -jar nrdata.jar $0 $@
