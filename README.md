# Task-Manager

This project involves creating a REST API using Spring Boot to manage tasks with DynamoDB and AWS SDK for Java 2.x.

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
1. Create an AWS account (if you don't have one).
2. Set up single sign-on access for the SDK:
   - Go to the IAM console (IAM Identity Center).
   - Create a new user.
   - Create a new group (Dev Team).
   - Go to Permissions Set and create predefined policy (Poweruser access).
   - Attach the user to a group.
   - Attach the policy to the group (navigate to Multi-account permissions -> AWS accounts).
   - Download the credentials.
   - Configure the AWS CLI with the SSO credentials using the command `aws configure`.
   - Set the created profile and credentials as default in credentials file and config file.

For more details, refer to [AWS Single Sign-On User Guide](https://docs.aws.amazon.com/singlesignon/latest/userguide/useraccess.html).

## Bootstrapping the Spring Boot Application
1. Go to the [Spring Initializr](https://start.spring.io) website and create a new Spring Boot application.
2. Select Maven Project, Java 17, and Spring Boot 3.0.0.
3. Add the required dependencies (Spring Web).
4. Generate the project and download the zip file.
5. Extract the zip file and open the project in your IDE.

## IDE Setup
1. Install an AWS SDK plugin in your IDE.
2. Go to the AWS Explorer and configure the AWS account.
3. Create a new profile and add the credentials using the command `aws configure sso`.
4. Add the required dependencies in the `pom.xml` file.

## DynamoDB Setup
1. Create a new table in DynamoDB.

**Table Structure:**
- Table name: TaskTable
- Partition key: id (String)
- Attributes:
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

## Running the Application
- Use Postman to perform CRUD operations on the `TaskTable` and `tasks`.

Feel free to explore and extend the project by creating user-interface!
