# Team Managment

This project was built to solve the following problem:

```
In a team of n people, a celebrity is known by everyone but he/she doesn't know anybody.
```

## Pre-requisites

The solution was built with Java JDK 8 and Spring Boot 2.0.4.RELEASE. In order to run the solution is necessary to verify in the system:


* The JAVA HOME environment variable should be set to a Java 8 installation. The version can be verified executing the next command ```java –version```
* Maven have to be installed in the system. To verify it, you can run the command ```mvn –version```


## Application Layer

![Can't load](doc/application_layer.png)

## Responsabilities

### Business/Service Layer

#### TeamResource

RestFul web service who exposes a resource to upload a file. It will process a file with the test cases of the problem and it will response a JSON object with the results of the test cases received. 

This resource will invoke to TeamMemberFinderService service in order to find the celebrity of a team.

#### CelebrityFinderServiceImpl

It's implementation class for an interface called TeamMemberFinderService, has the logic to find a celebrity of a team given.

###### **Request Info**

+ **Http Method:** POST
+ **URI:**  ````/v1/teams/upload/filterByCelebrity````
+ **Content-Type:** text/plain
+ **Headers:**  multipart/form-data
+ **Request PayLoad:**  Content-Type: text/plain

## How Can I Test it?

You need to upload a file with the test cases. This file will contain the data of the members of a team. The input format will explain what it means.

### Input Format 

The first line contains an integer T, the number of test-cases. T testcases follow. 

For each test case, the first line will contain two integers N and M separated by a single space. N defines the team ID and M defines the number of the members of the team. Each test cases corresponds to a team concept.

```
1 5  [ [N,M] = Team Id, members of the Team ]
```

The next M lines will contain separated by a single space:

```
[J, K...] = Member Id, Members known by A
```

### Output Format 

The service will response a JSON document with the result of each test case. 

### Sample Input

```
2
1 5
1 
2 1 3
3 1 4
4 1 2
5 1 3
2 3
1 1
2 1
3 1
```

### Sample Output

```json

{
  "testCase": [
    {
      "idTeam": 1,
      "celebrity": 1,
      "message": "Celebrity Found with ID: 1"
    },
    {
      "idTeam": 2,
      "celebrity": -1,
      "message": "Celebrity not found"
    }
  ]
}
```

### Explanation

There are two test cases. The first with team number one, have five members, the second one with team ID two, have 3 members.

In the first team, the member 1 doesn't have a known. (celebrity). The others members knows to the member 1 and other members. The celebrity found is the member 1 of the team.

In the second team, all members known a member with ID 1. There isn't a celebrity.

## Running the solution

locate in the root folder of the project and run the following commands: 

```
mvn compile
mvn spring-boot:run
```

Open your browser and copy the next URL:

```
http://localhost:8585/swagger-ui.html
```

## Uploading a Test File


The port defined for the server is 8585.


The documentation of the rest service is exposed using Swagger UI.

```
http://localhost:8585/swagger-ui.html
```

Then, we need to find the resource ```POST /v1/teams/upload/filterByCelebrity``` in order to upload the file with the test cases.

![Can't load](doc/uploadfile_swagger.png)

Then, try it out!


[Data Test Cases Example](doc/data.txt)
