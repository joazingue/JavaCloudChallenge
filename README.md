# Java Cloud Challenge

Code solution for the Java Cloud Challenge. Made with Java Springboot, h2 database and docker.

## Installation
Created using [Spring Initializr](https://start.spring.io/). Maven pom installs dependencies

## Build
```bash
mvnw package
java -jar target/challenge-0.0.1-SNAPSHOT.jar
```

#### Docker Image
```bash
docker build --build-arg JAR_FILE=target/*.jar -t gbm_challenge .
docker run -p 2811:2811 --name challenge gbm_challenge
```

### AWS Elastic Beanstalk

builspec.yml file ready to upload to codebuild and pipeline
