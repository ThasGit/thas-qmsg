FROM maven as maven-package

ARG MAVEN_CENTRAL_MIRROR=http://maven.aliyun.com/repository/central/

RUN if [ -n "$MAVEN_CENTRAL_MIRROR" ]; then sed -i  "146 a\ \t<mirror>\n\t\t<id>maven-central-mirror<\/id>\n\t\t<mirrorOf>central<\/mirrorOf>\n\t\t<name>maven-central-mirror<\/name>\n\t\t<url>$MAVEN_CENTRAL_MIRROR<\/url>\n\t</mirror>" /usr/share/maven/conf/settings.xml; fi

RUN git clone -b master https://github.com/ThasGit/thas-qmsg.git

RUN cd /thas-qmsg && mvn package

FROM openjdk:8-jdk-slim

MAINTAINER thas thascc1024@gmail.com

COPY --from=maven-package /thas-qmsg/target/thas-qmsg.jar /app.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["bash", "-c", "java $JAVA_OPTS -jar app.jar"]
