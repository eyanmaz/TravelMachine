# Travel Machine

This Api is used to create travel details and store it in memory db when the profile is "inMemory"
which is default active profile.

### Api Documentation can be found on below swagger url
* http://localhost:8080/travelMachine/swagger-ui/

### Prerequisite for building and running application
* Jdk 11 
* Maven 3

### Run the Application
Build the application with below maven command

`mvn clean install`

Then go to target folder and run below command

`java -jar TravelMachine.jar`

### Example of Success Api Call
After running the application on your local:

Use `POST` method and send like below json to the url of
`http://localhost:8080/travelMachine/v1/travel-details`

```
{
"pgi" : "abcde2134",
"place": "Tokyo",
"date": "2021-05-31"
}
```

You will receive `Http 201` status code and location of the new 
resource in the header for the success case. 
