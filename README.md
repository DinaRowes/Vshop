## Hot to run test suite :wave:
- The project contains 2 feature file 1 for each API and UI suite
- You can run both by the bellow command
 ```
 mvn clean test
 ```

- Tech Stack: Selenium JAVA, TestNG, Cucumber and RestAssured.

## The Reports :tada:
- The project use the **Allure** report for generating the reports after running
- The reports files located in project root file under /allure-results folder
- but you need to install Allure in your local machine to be able to read these reports

- If you are on **windows** you can execute the bellow commands to install Allure:  :shipit:
```
 Set-ExecutionPolicy RemoteSigned -scope CurrentUser
 iwr -useb get.scoop.sh | iex
 scoop install allure
```

- After install the Allure on your machine and run tests, you can use this command to open the report:
 ```
 allure serve allure-results
 ```
