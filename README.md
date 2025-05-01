A hotel management system built with **Java Servlets**, **JSP**, **PostgreSQL**, and **Docker Compose**.
Details about the detailed project objectives, features, etc is in CSI 2132 Project 2 Report.pdf.

**How to run the project:**
1. In your computer terminal, type:
git clone https://github.com/geeraysury/HotelWeb-DatabaseClassProject.git
cd HotelWeb-DatabaseClassProject

2. Make sure you have Maven installed, then run:
mvn -f HotelWeb/pom.xml clean package

3. Then run:
docker-compose up --build

4. Once both containers are up, go to:
http://localhost:8080

- To stop the containers:
docker-compose down

- To reset and reinitialize the database:
docker-compose down -v
