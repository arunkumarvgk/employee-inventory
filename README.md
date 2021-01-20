# employee-inventory
The Employee Inventory System that allows us to upload employee data from file to database asynchronously and perform operations on them.


## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.6](https://maven.apache.org)

## Compile and package

This also runs tests

```shell
mvn clean install
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.empinventory.EmployeeInventoryApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

The above one runs the project with default property values, we can override them using the below one:

#### Few properties useful while testing  
**file.upload.logAfterRecordsCount = 10**  Updates the log after every 10 records inserted to database  
**file.upload.delay = 500**  Delay in milliseconds between insert operation   

```shell
mvn spring-boot:run -Dspring-boot.run.arguments="--file.upload.logAfterRecordsCount=10 --file.upload.delay=500"
```
You can access the application http://localhost:8080

## APIs  

### 1. Upload file 
  ###### Post request http://localhost:8080/api/employee/upload  
  Choose form-data, Key will be **file** and value will be upload file
![image](https://user-images.githubusercontent.com/30832427/105221115-ab20ea00-5b7e-11eb-93d4-23b7971f6c78.png)    


Response will be the Task ID to check the status


### 2. Verify status 
###### Get Request http://localhost:8080/api/employee/status/1  


![image](https://user-images.githubusercontent.com/30832427/105221948-cfc99180-5b7f-11eb-8480-ff94a4c8e3ab.png)



  
  
