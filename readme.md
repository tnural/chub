

### Requirements
- Jdk 18
- Gradle 7.4.1
- Lombok IDE configuration (if needed)

### How to run
- $**_./gradlew bootRun_** run command in the root of project to start web server.
- $**_./gradlew clean test_** run command in the root of project to run the tests.

### My two cents as feedback
- of course in this task I avoided soo many things, those immediately coming in my mind, might be logging,validation,error handling, schema definition, localization, virtualization and more depending on request, I have knowledge how to provide them, but in terms of simplicity I just tried to show how I approach to the problem.

**Request example**

`curl --location --request GET 'localhost:8080/customer/top' \
--header 'Content-Type: application/json' \
--data-raw '{
"from": "2020-12-01T15:23:00.000+00:00",
"to": "2023-05-30T23:59:59.999+00:00",
"limit": 10
}'`