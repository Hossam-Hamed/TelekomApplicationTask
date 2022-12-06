# TelekomApplicationTask
Programing Task for Job application at Telekom
## Overview
* It is a Java-based app that uses Spring boot for the backend and Thymeleaf as a frontend server-side Java template engine.
* Java 18, spring boot 2.7.6, and bouncycastle (bcpkix-jdk18on v.1.72) are used.

## Requirments Description
Develop a PKCS#10 Certificate Signing Request (CSR) parser that accepts a CSR, parses it, and displays the contents in a web interface. The application should consist of a javascript frontend and java backend; in detail:

* Functions of the frontend web interface
    * Upload the CSR; via form or file upload :white_check_mark:
    * The contents of the CSR (requester name, public key algorithm, any subject AltNames) should be displayed :white_check_mark:

* unit/integration tests for backend and frontend :x:

* The app should be provided as a runnable fat jar and not require an additional application server to be intstalled :white_check_mark:

* The app should write a log file. :white_check_mark:

* Use a build tool, Apache Maven 3 or Gradle :white_check_mark:

## Deliverables
* Source code
* [JAR executable](./build/libs)

## Need To Know
* Gradle is the build tool of choice
* Server runs on http://localhost:8080
* log file (CSRParser.log) is written at the current terminal directory ('.') from which the JAR was executed regardless the location of the JAR.
