# Task-Manager
Creating a REST API using Spring Boot to manage tasks with DynamoDB and AWS SDK for Java 2.x.
```
## Requirements
- Java 17 or later
- Spring Boot 3.0.0
- AWS SDK
- IDE (IntelliJ IDEA, Eclipse, etc.)
- Maven
- Postman (for testing the API)
- AWS Account
- AWS CLI

## AWS Account Setup
- Create an AWS account (if you don't have one)
- Set up single sign-on access for the SDK
    - Go to the IAM console (IAM Identity Center)
    - Create a new user
    - Create a new group (Dev Team)
    - Go to Pemissions Set and create predfineset policy (Poweruser access)
    - Attach the user to a group
    - Attach the policy to the group (navigate to Multi-account permissions -> AWS accounts)
    - Download the credentials
    - Configure the AWS CLI with the sso credentials using the command `aws configure`
    - Set the create new profile and credentials as default in crednetials file and config file

For more details: [https://docs.aws.amazon.com/singlesignon/latest/userguide/useraccess.html]

## Bootstrapping the Spring Boot Application
- Go to the Spring Initializr website and create a new Spring Boot application [https://start.spring.io]
- Select Maven Project, Java 21, and Spring Boot 3.2.3
- Add the required dependencies (Spring Web)
- Generate the project and download the zip file
- Extract the zip file and open the project in your IDE

## IDE Setup
- Install a AWS SDK plugin in your IDE
- Go to the AWS Explorer and configure the AWS account
- Create a new profile and add the credentials using the command `aws configure sso`
- Add the required dependencies in the pom.xml file

## DynamoDB Setup
- Create a new table in DynamoDB

Table Strucutre:
    - Table name: TaskTable
    - Partition key: id (String)
    - tableName (String)
    - tableDescription (String)
    - taskList (Map)
          - id (String)
          - name (String)
          - description (String)
          - status (String)
          - dueDate (String)
          - priority (String)
          - createdAt (String)
          - updatedAt (String)

Run The Application:
- Use Postman to perform the CRUD operations on the taskTables and tasks



