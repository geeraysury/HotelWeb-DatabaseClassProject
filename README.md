This project was developed as part of the Databases I course at the University of Ottawa.
The goal is to simulate a real-world multi-chain hotel booking and renting platform. Customers can search for hotel rooms based on filters like location, price, capacity, and availability, and then either book or rent a room. Employees can manage room assignments, convert bookings into rentals, and perform admin tasks through a web interface.

The backend handles:
- Hotel chains, hotels, rooms, customers, and employees
- Bookings and rentings
- Real-time room availability
- Referential integrity and user-defined constraints
- Triggers, views, and indexes for performance and integrity

**Technologies Used**
Java (Servlets + JSP)
PostgreSQL for database management
Apache Tomcat for deployment
Maven for project build

**How to run the project:**
1. In your computer terminal, type:
- git clone https://github.com/geeraysury/HotelWeb-DatabaseClassProject.git
- cd HotelWeb-DatabaseClassProject

2. Make sure you have Maven installed, then run:
mvn -f HotelWeb/pom.xml clean package

3. Ensure Docker Desktop is downloaded and running. Then run:
docker-compose up --build

4. Once both containers are up, go to:
http://localhost:8080

- To stop the containers:
docker-compose down

- To reset and reinitialize the database:
docker-compose down -v
