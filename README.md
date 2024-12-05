# eWallet

## Problem Statement

Many modern digital finance applications fall short of providing an all-in-one solution. Some focus solely on money transfers between users, while others are designed for e-commerce transactions. This fragmentation creates inconvenience for users. To address this, eWallet has been introduced as a Minimum Viable Product (MVP). It combines seamless peer-to-peer money transfers with the ability to purchase products directly within the app, eliminating the need to navigate third-party sites. This unified approach enhances user convenience and simplifies financial transactions.

eWallet is a web based wallet system that helps manage finances conveniently and digitally from one centralized system.

## Requirement Analysis
### Functional Requirements
#### Customer should be able to:
* Register, login and manage their profile.
* Deposit money into the wallet or withdraw money away from  eWallet.
* View his/her current balance and income & expense.
* Purchase a product, provided by a merchant, directly from eWallet

#### Merchant should be able to:
* Create a product that the customer can buy directly from eWallet
* View total earnings
* View earnings made based on product
* Withdraw money away from eWallet

### Non Functional Requirements
#### Security:
* Secure authentication and authorization for customers and merchants
* Role based authorization where only merchants are able to create, update and delete product

#### Auditability:
* Maintain detail logs of all transactions preformed customers and merchants which also serves as a history

#### Containerization:
* Containerize to ensure portability and consistent across different operating systems and environments

#### Cloud Deployment:
* Deploy the system to a cloud platform (e.g. AWS) to make it publicly accessible

## Software Modeling
### Class Diagram
![Class Diagram](https://github.com/sanjeevthapamiu/eWallet/blob/main/diagrams/UML.jpeg)

## Architecture
![Architecture](https://github.com/sanjeevthapamiu/eWallet/blob/main/diagrams/Architecture.jpeg)

## ER Diagram
![ER Diagram](https://github.com/sanjeevthapamiu/eWallet/blob/main/diagrams/ERD.jpeg)

## Software Setup Instructions
### Local Installation
Install JDK 23 from the following link: <br/>
https://www.oracle.com/java/technologies/downloads

Download or clone this repository: <br/>
`git clone https://github.com/sanjeevthapamiu/eWallet.git`

Run PostgreSQL

Configure PostgreSQL in `application.yml` file: <br/>

![application.yml](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/application-yml.png)

Package the application: <br/>
`./mvnw clean package` <br/>

![mvnw clean package](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/mvn-clean-package.png)

Run the application: <br/>
`java -jar target/app.jar` <br/>

![java jar](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/java-jar.png)

### Installation using Docker
eWallet Application as well as the PostgreSQL Database can be run on docker using the following commands: <br/>
`chmod +x run_docker.sh` <br/>
`sh run_docker.sh` or `./run_docker.sh`

run_docker.sh shell script contains: <br/>
```
#!/bin/bash

# Package the spring boot application
docker-compose down && docker-compose up -d postgres # needed for database connection while packaging spring boot app
./mvnw clean package

# Build image
docker build -t ewallet:latest .

# Run the application and required tools using docker-compose
docker-compose down && docker-compose up -d && docker-compose logs -f
```

### Deployment to Kubernetes
Kubernetes Deployment can be found on  [DEPLOYMENT.md](https://github.com/sanjeevthapamiu/eWallet/blob/main/DEPLOYMENT.md)