# SoPra RESTful Service Template FS18

## Spring Boot

* Documentation: http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
* Guides: http://spring.io/guides
  * Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
  * Building REST services with Spring: http://spring.io/guides/tutorials/bookmarks/


## Setup this Template with IntellJ

Note: Feel free to develop in the IDE of your choice (e.g., [Eclipse](http://www.eclipse.org/downloads/)).

1. File -> Open... -> SoPra Server Template
2. Accept to import the project as a `gradle project`

To build right click the `build.gradle` file and choose `Run Build`


## Building with Gradle

You can use the local Gradle Wrapper to build the application.

Plattform-Prefix:

* MAC OS X:  ``./gradlew``
* Windows:  ``./gradlew.bat``

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

## API Endpoint Testing
### Postman

* We highly recommend to use [Postman](https://www.getpostman.com) in order to test your API Endpoints. 
