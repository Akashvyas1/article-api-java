# article-api-java
Article API using Java and Spring Boot

## Requirements
Create a simple API with three endpoints.

The first endpoint, POST /articles should handle the receipt of some article data in json format, and store it within the service.

The second endpoint GET /articles/{id} should return the JSON representation of the article.

The final endpoint, GET /tags/{tagName}/{date} will return the list of articles that have that tag name on the given date and some summary data about that tag for that day.

An article has the following attributes id, title, date, body, and list of tags. for example:

{
  "id": "1",
  "title": "latest science shows that potato chips are better for you than sugar",
  "date" : "2016-09-22",
  "body" : "some text, potentially containing simple markup about how potato chips are great",
  "tags" : ["health", "fitness", "science"]
}

The GET /tag/{tagName}/{date} endpoint should produce the following JSON. Note that the actual url would look like /tags/health/20160922.

{
  "tag" : "health",
  "count" : 17,
    "articles" :
      [
        "1",
        "7"
      ],
    "related_tags" :
      [
        "science",
        "fitness"
      ]
}


The related_tags field contains a list of tags that are on the articles that the current tag is on for the same day. It should not contain duplicates.

The count field shows the number of tags for the tag for that day.

The articles field contains a list of ids for the last 10 articles entered for that day.

## Setup Prerequisites

This solution has been developed using Java, Spring Boot, Spring-data-jpa and Postgresql/h2 in-memory datbase.
Following would be required to run this api on a system.

- Java 8

- Maven

- Postgresql (optional)

While using postgresql is recommended, if you don't have postgresql installed on your system, it is possible to run the api without that using h2 in-memory database. However, with h2 db, persisted data would get lost when the application is stopped.

## How to run the application?

- Clone/download the repository and ensured above prerequisites are taken care of.
- Before running the application, check the configurations and postgresql database connection and credentials meet your local setup. You can find them and update if required in application.properties file in src/main/resources directory.
- If you don't have postgresql, then ignore those credentials as instead h2 in-memory db properties would be used as per application-h2.properties file.
- Now, to run the application, execute following maven command in project directory:

        mvn spring-boot:run
- If you are using h2 in-memory database instead of postgresql, run the application using spring profile "h2".
        
        mvn spring-boot:run -Dspring-boot.run.profiles=h2
- Alternately, you can import the project as a maven project in an IDE and run it there.
- By default, the application would start running on port 8080, which you can change in application.properties.
- Verify the app is running by invoking healthcheck api at http://localhost:8080/articles/_health
- Now, you can invoke post  & get article endpoints along with tag & date based search as described in the requirements.

## Assumptions & Solution Notes

- The requirement doesn't state the use of any specific database but solution uses postgresql/h2 in-memory database for convenience. 
- No need to create any tables before running the app, it would be created by hibernate framework. 
- Application does some basic 'not null' validations for certain fields in the API input requests, assuming them to be mandatory.
- A generic error response is returned if assumed mandatory field is missing in the json request.
- Unit tests are included in the solution, can be executed using 'mvn test' or in any IDE.

