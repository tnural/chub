

### Requirements
- Jdk 18
- Gradle 7.4.1
- Lombok IDE configuration (if needed)

### How to run
- $**_./gradlew bootRun_** run command in the root of project to start web server.
- $**_./gradlew clean test_** run command in the root of project to run the tests.


**Request example**

`curl --location --request GET 'localhost:8080/customer/top' \
--header 'Content-Type: application/json' \
--data-raw '{
"from": "2020-12-01T15:23:00.000+00:00",
"to": "2023-05-30T23:59:59.999+00:00",
"limit": 10
}'`
