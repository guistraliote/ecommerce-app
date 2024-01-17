# ecommerce-app

This project is a e-commerce website and this repository is the backend. This file provides the archictetural schemas and technologies used in the project.

Backend:
Java 17
Springboot
JPA
H2 Database
PostgreSQL

Entity-relational Diagram:

![ecommerceapp - public](https://github.com/guistraliote/ecommerce-app/assets/88463468/eb1713ae-346a-49c8-830f-e69844bbb21d)

*This diagram is still in progress.

DB Configs:

docker run -d
--name ecommerce-app-database   
-e POSTGRES_USER=postgres   
-e POSTGRES_PASSWORD=postgres   
-e POSTGRES_DB=ecommerceapp   
-p 5440:5432   
postgres:latest

ActiveMQ Configs:

docker run -it --rm -p 61617:61616 -p 8162:8161 --name activemq webcenter/activemq:latest
