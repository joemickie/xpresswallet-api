# xpresswallet-api

Overview
Welcome to the API service for authenticating users and facilitating airtime payments. Below, you will find an overview of the modules, features, and guidelines for setting up and using this API service.

# Folder Structure

```
xpresswallet-api
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── xpresswalletapi
│   │   │   │   ├── controllers
│   │   │   │   │   ├── AirtimeController.java
│   │   │   │   │   └── AuthController.java
│   │   │   │   ├── enums
│   │   │   │   │   ├── TransactionStatus.java
│   │   │   │   │   └── TransactionType.java
│   │   │   │   ├── entities
│   │   │   │   │   ├── BaseEntity.java
│   │   │   │   │   └── User.java
│   │   │   │   ├── payloads
│   │   │   │   │   ├── requests
│   │   │   │   │   │   ├── AirtimeRequest.java
│   │   │   │   │   │   ├── AirtimeRequestDetails.java
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── SignupRequest.java
│   │   │   │   │   │   └── XpressAirtimeRequest.java
│   │   │   │   │   ├── responses
│   │   │   │   │   │   ├── AirtimeResponse.java
│   │   │   │   │   │   ├── AirtimeResponseData.java
│   │   │   │   │   │   ├── ApiResponse.java
│   │   │   │   │   │   ├── LoginResponse.java
│   │   │   │   │   │   └── SignupResponse.java
│   │   │   │   ├── repositories
│   │   │   │   │   └── UserRepository.java
│   │   │   │   ├── services
│   │   │   │   │   ├── implementations
│   │   │   │   │   │   ├── AuthImplementation.java
│   │   │   │   │   │   ├── JwtImplementation.java
│   │   │   │   │   │   └── AirtimeService.java
│   │   │   │   │   ├── provider
│   │   │   │   │   │   ├── AuthService.java
│   │   │   │   │   │   ├── JwtService.java
│   │   │   │   │   │   └── SecurityConfiguration.java
│   │   │   │   │   ├── XpressWalletConfiguration.java
│   │   │   │   ├── exceptions
│   │   │   │   │   └── XpressException.java
│   │   │   │   │   └── XpressExceptionHandlers.java
│   │   │   │   ├── filters
│   │   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   │   └── XpressSecurityFilter.java
│   │   │   │   ├── utils
│   │   │   │   │   └── PayUtils.java
│   │   │   │   └── XpresswalletApiApplication.java
│   │   ├── resources
│   │   │   └── application.properties
│   ├── test
│   └── HELP.md
├── target
├── .idea
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Modules

1. Authentication Module

Description: Implements an API for user authentication using JSON Web Tokens (JWT).
Usage: Clients can securely authenticate users and obtain JWTs for subsequent API requests.

2. Airtime VTU Module

Description: Consumes the airtime Virtual Top-Up (VTU) API from the Biller Service Test environment portal.
Usage: Allows clients to initiate airtime payments seamlessly through integration with the Biller Service Test environment.

3. Unit Testing Module

Description: Includes unit tests with a minimum code coverage of 50% to ensure the reliability and correctness of the implemented functionality.
Usage: Developers can run these tests to validate the behavior of the codebase and catch potential issues early in the development process.

# Getting Started

To use this API service, follow the steps below:

1. Clone the Repository:

git clone https://github.com/joemickie/xpresswallet-api.git

2. Set Up Authentication:

Configure authentication settings by editing the application.properties file.
Provide necessary credentials and configurations required for JWT authentication.

3. Airtime VTU Integration:

Visit the Biller Service Test environment portal (URL provided in the documentation) to create an account.
Review the documentation for the Airtime VTU API and obtain any necessary API keys.

4. Configure Airtime VTU Module:

Update the application.properties file with the API keys and configurations obtained from the Biller Service Test environment.

5. Run Unit Tests:

Execute unit tests to ensure the correctness and reliability of the implemented features.

6. Build and Run the Application:

The API will be available at http://localhost:8080. Refer to the API documentation for specific endpoints and usage details.

# Contributing

If you would like to contribute to the development of this API service, please reachout for the guidelines outlined.

# Issues and Support

If you encounter any issues or have questions, please open an issue on the GitHub repository.





