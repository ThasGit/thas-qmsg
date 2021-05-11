FROM maven as maven-package

MAINTAINER thas thascc1024@gmail.com

RUN git clone -b master https://github.com/ThasGit/thas-qmsg.git

RUN cd /thas-qmsg && mvn package

FROM java:8

COPY --from=maven-package /thas-qmsg/target/thas-qmsg*.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["bash", "-c", "java $JAVA_OPTS -jar app.jar"]
