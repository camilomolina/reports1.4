FROM adoptopenjdk:11-jdk-openj9-bionic AS build

RUN cd && \
    apt update && \
    apt install wget -y && \
    wget -q http://www.us.apache.org/dist/ant/binaries/apache-ant-1.10.7-bin.tar.gz && \
    tar -xzf apache-ant-1.10.7-bin.tar.gz && \
    mv apache-ant-1.10.7 /usr/local/bin/apache-ant && \
    rm apache-ant-1.10.7-bin.tar.gz

COPY . /usr/src/app
RUN /usr/local/bin/apache-ant/bin/ant -f /usr/src/app/build.xml clean war

FROM jetty:9.4-jre11
LABEL maintainer="bennu <contacto@bennu.cl>"
COPY --from=build /usr/src/app/build/reports14.war $JETTY_BASE/webapps/reports14.war


