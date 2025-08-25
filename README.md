# Budget Tracker

## Project Description  
Budget Tracker is a simple Java application for tracking personal finances.  
It allows adding, deleting, and viewing transactions, distinguishing between incomes and expenses, and organizing them into categories.  
The data is stored in a JSON file, and the application uses the Gson library for parsing and handling data.  

## Project Structure  
- `src/main/java` – source code of the application (classes `Transaction`, `Category`, `BudgetService`, `App`)  
- `src/main/resources` – `data.json` file containing financial records  
- `src/test/java` – JUnit tests  

## Tools and Automation  
- Maven – dependency management and project build  
- JUnit – testing of functionalities  
- SpotBugs – static code analysis and bug detection  

## How to Run the Project  
To run the application, Maven must be installed.  

1. Run the application and tests:  
   ```bash
   mvn -q exec:java
   mvn spotbugs:check

## Note  
This project was created as part of the Software Development Automation course.  
The focus is on demonstrating the use of Maven, JUnit, and static analysis tools within a practical example.
