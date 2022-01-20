# URL Explain Video
https://youtu.be/CBMly4up8pU

# Contact Email
DP1L81.etsii@gmail.com

# Spring SevenIslands Sample Application 

This is a fork of https://github.com/spring-projects/spring-SevenIslands to be used for the DP1 course. The main changes that have been performed were:
- Trimming several parts of the application to keep the example low
- Reorganize some parts of the code according to best practices introduced in the course

## Understanding the Spring SevenIslands application with a few diagrams
<a href="https://speakerdeck.com/michaelisvy/spring-SevenIslands-sample-application">See the presentation here</a>

## Running SevenIslands locally
SevenIslands is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/gii-is-DP1/spring-SevenIslands.git
cd spring-SevenIslands
./mvnw package
java -jar target/*.jar
```

You can then access SevenIslands here: http://localhost:8080/

<img width="1042" alt="SevenIslands-screenshot" src="![lobby](https://user-images.githubusercontent.com/80202474/150423306-8089f9a6-a1b2-4073-8646-16fdf8a00701.jpg)">

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

## In case you find a bug/suggested improvement for Spring SevenIslands
Our issue tracker is available here: https://github.com/gii-is-DP1/spring-SevenIslands/issues


## Database configuration

In its default configuration, SevenIslands uses an in-memory database (H2) which
gets populated at startup with data. 

## Working with SevenIslands in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 8 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line
```
git clone https://github.com/gii-is-DP1/spring-SevenIslands.git
```
2) Inside Eclipse or STS
```
File -> Import -> Maven -> Existing Maven project
```

Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA

In the main menu, choose `File -> Open` and select the SevenIslands [pom.xml](pom.xml). Click on the `Open` button.

CSS files are generated from the Maven build. You can either build them on the command line `./mvnw generate-resources`
or right click on the `spring-SevenIslands` project then `Maven -> Generates sources and Update Folders`.

A run configuration named `IslasApplicaction` should have been created for you if you're using a recent Ultimate
version. Otherwise, run the application by right clicking on the `IslasApplicaction` main class and choosing
`Run 'IslasApplicaction'`.

4) Navigate to SevenIslands

Visit [http://localhost:8080](http://localhost:8080) in your browser.


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [IslasApplicaction](https://github.com/gii-is-DP1/spring-SevenIslands/blob/master/src/main/java/org/springframework/samples/SevenIslands/IslasApplicaction.java) |
|Properties Files | [application.properties](https://github.com/gii-is-DP1/spring-SevenIslands/blob/master/src/main/resources) |
|Caching | [CacheConfiguration](https://github.com/gii-is-DP1/spring-SevenIslands/blob/master/src/main/java/org/springframework/samples/SevenIslands/system/CacheConfiguration.java) |

## Interesting Spring SevenIslands branches and forks

The Spring SevenIslands master branch in the main [spring-projects](https://github.com/spring-projects/spring-SevenIslands)
GitHub org is the "canonical" implementation, currently based on Spring Boot and Thymeleaf. There are
[quite a few forks](https://spring-SevenIslands.github.io/docs/forks.html) in a special GitHub org
[spring-SevenIslands](https://github.com/spring-SevenIslands). If you have a special interest in a different technology stack
that could be used to implement the Pet Clinic then please join the community there.

# Contributing

The [issue tracker](https://github.com/gii-is-DP1/spring-SevenIslands/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <https://editorconfig.org>. If you have not previously done so, please fill out and submit the [Contributor License Agreement](https://cla.pivotal.io/sign/spring).

# License

The Spring SevenIslands sample application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[spring-SevenIslands]: https://github.com/spring-projects/spring-SevenIslands
[spring-framework-SevenIslands]: https://github.com/spring-SevenIslands/spring-framework-SevenIslands
[spring-SevenIslands-angularjs]: https://github.com/spring-SevenIslands/spring-SevenIslands-angularjs 
[javaconfig branch]: https://github.com/spring-SevenIslands/spring-framework-SevenIslands/tree/javaconfig
[spring-SevenIslands-angular]: https://github.com/spring-SevenIslands/spring-SevenIslands-angular
[spring-SevenIslands-microservices]: https://github.com/spring-SevenIslands/spring-SevenIslands-microservices
[spring-SevenIslands-reactjs]: https://github.com/spring-SevenIslands/spring-SevenIslands-reactjs
[spring-SevenIslands-graphql]: https://github.com/spring-SevenIslands/spring-SevenIslands-graphql
[spring-SevenIslands-kotlin]: https://github.com/spring-SevenIslands/spring-SevenIslands-kotlin
[spring-SevenIslands-rest]: https://github.com/spring-SevenIslands/spring-SevenIslands-rest
