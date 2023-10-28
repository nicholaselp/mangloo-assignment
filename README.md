# Mangloo Assignment Spring Boot Application

## Description
This is a spring boot application demonstrating simple CRUD operations on Customers and Orders.

### Technologies used
Hibernate JPA <br>
Liquibase - data migration tool

## Prerequisites
Before running the application, make sure you have the the following tools installed
- [Docker](https://www.docker.com/)

## Recommended Tools
For testing and interacting with the application's APIs, [Postman](https://www.postman.com/downloads/) is recommended.
Collection to all the APIs of the application are provided (see the "Calling APIs" section below)

### Building the project
./mvnw clean install

### How to run the application
- Building the application with maven
  - ./mvnw clean package -Dskiptests
- Use docker compose to create and run containers for the application, PostgreSQL and pgAdmin
  - docker-compose up

#### Calling APIs
Access the APIs by making requests to localhost:8080<br>
API collection for postman can be found [here](postman_collection)

#### Connecting to pgAdmin
- Go to [localhost:5050](localhost:5050) in the browser to enter pgadmin login screen 
  - username: admin@admin.com 
  - password: root
- Once logged in
  - Right click on servers
  - Register -> Server
    - General Tab
      - name: any name e.g. mangloo-assignment
    - Connection Tab
      - Host name/address:
        - to find the host name run in a terminal 'docker container ls'
        - run 'docker inspect $docker_id' e.g. docker inspect 931c2cc21ed3
        - copy the IPAddress e.g. 172.25.0.3
        - Paste it in the Host name/address field
      - Port: 5432
      - Maintenance database: mangloo-assignment
      - username: postgres
      - password: postgres
  - Click save

