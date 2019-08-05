# Field Condition Statistics

 This api records incoming field condition measurements and provide field condition statistics from the last 30 days
 
 ## Getting Started
 
 Run `mvn clean install` to install the package after cloning.
 
 ### Prerequisites
 
 You will need below things to run project smoothly,
 ````
 jdk 1.8
 maven 3.3+
 ```` 
 
 once the repository is cloned. please run 
  ```
  mvn clean test
  ```
 
 ## Running the tests
 To run the test please run `mvn clean test`

 
### Installing

 * Please clone the whole repository. `git clone <project>`
 * Once clone is complete, please run `mvn clean install`
 
 ### Test
 Test can run using command `mvn test`
 Have tried to cover all the basic testcases. (Controller test cases have the IT test cases as well).
 It contains around 10 test cases. 
 
 ### Implementation
 
 It uses the navigable map which is a threadsafe map and can help in getting rid of the data which is older than 30 days in constant time.
 [Navigable Map](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/NavigableMap.html)
 
 ### Repository
 
 As of now in this project we are using a internal cache which is a map. (Map is used just to group the similar days data)
 But we can use the proper persistant store. 
 
 ### Application
 
 Application is accessible with the port 8080.(default port)
 
 You can perform the below operations
 
 Get field stats
 ````
 GET /field-statistics
 ````
 
 Add field stats
 ````
 POST /field-conditions
 
 Request : 
 {
        "vegetation" : 0.45,
        "occurrenceAt" : "2019-07-23T08:50Z"
 }
 ```` 
 
 [Swagger](http://localhost:8080/swagger-ui.html#/)
 
 ````
 http://localhost:8080/swagger-ui.html#/
 ````
 