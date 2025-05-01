FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY HotelWeb/target/ehotels.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

