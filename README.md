# ecommerce-app

This project is a e-commerce website and this repository is the backend. This file provides the archictetural schemas and technologies used in the project.

Backend:
Java 17
Springboot
JPA
H2 Database
PostgreSQL

Entity-relational Diagram:

![image](https://github.com/guistraliote/ecommerce-app/assets/88463468/06d423f6-e887-472b-af37-7c29e343def5)

*This diagram is still in progress.

DB Configs:

docker run -d
--name ecommerce-app-database   
-e POSTGRES_USER=postgres   
-e POSTGRES_PASSWORD=postgres   
-e POSTGRES_DB=ecommerceapp   
-p 5440:5432   
postgres:latest
