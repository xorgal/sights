FROM tomcat:10.1.31-jdk17-temurin-jammy

RUN apt-get update && apt-get install -y postgresql-client

COPY ./target/sights.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
