# HotelBookingForm-EE
This repo consists of maven implementation of java selenium cucumber. 
It performs some happy path and some negative Frontend tests as mentioned in the Feature files.

# Web Driver Setup
This project will use Selenium WebDriver to interact with the Chrome browser. 
The code can be easily changed to work with any other web browsers after installing the respective web drivers. 
The tests were implemented using latest version of Chrome (Version 88.0.4324.96) and latest Chrome driver which has been included in the project.
In case of any tests failure please confirm Chrome browser and Chromedriver versions are compatible.

# Java version
The tests were implemented with project on Intellij configured to use Java version - openjdk-11.0.2.jdk

# Running Tests
This project uses Maven.
On terminal inside the project folder, run the following maven command:
"mvn clean install"
To run tests, run the following maven command:
"mvn test". 

Individual tests can also be executed using Intellij's Run Configuration.
The test report would be generated inside the target folder.

# Sample Report:
![alt text](https://github.com/devenbairat/HotelBookingEE/blob/master/Cluecumber-Report-All-Scenarios.png?raw=true)
