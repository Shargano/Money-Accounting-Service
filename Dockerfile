FROM ubuntu:18.04

RUN apt-get update && apt-get install -y openjdk-8-jre
RUN java -version

RUN apt-get update && apt-get install -y gnupg2
RUN apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys B97B0AFCAA1A47F044F244A07FCC7D46ACCC4CF8
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ precise-pgdg main" > /etc/apt/sources.list.d/pgdg.list
RUN apt-get update && apt-get install -y software-properties-common postgresql-9.3 postgresql-client-9.3 postgresql-contrib-9.3
USER postgres
RUN    /etc/init.d/postgresql start &&\
    psql --command "CREATE USER docker WITH SUPERUSER PASSWORD 'docker';" &&\
    psql --command "ALTER USER postgres WITH PASSWORD '0440';" &&\
    createdb -O docker docker &&\
    createdb money postgres

COPY build/libs/Money-1.0-SNAPSHOT.jar app.jar

USER root
EXPOSE 8888/tcp
CMD /etc/init.d/postgresql start && java -jar app.jar
