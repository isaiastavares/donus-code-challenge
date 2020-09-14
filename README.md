# Donus Code Challenge

[![Build Status](https://travis-ci.org/isaiastavares/donus-code-challenge.svg?branch=master)](https://travis-ci.org/github/isaiastavares/donus-code-challenge)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=alert_status)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=coverage)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=ncloc)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=bugs)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=code_smells)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=isaiastavares-github)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=isaiastavares-github&metric=sqale_index)](https://sonarcloud.io/dashboard?id=isaiastavares-github)

## Architecture
The project is built as follows:

![](architecture.png)

## Requirements
* Java 11+
* Kotlin 1.3+
* Spring Boot 2.3+
* Docker (Engine and Compose)

## How to run the application locally

There are two ways to run the application. The first is using docker via command: 

```./start.sh```

The other way is to run the application separately via IDE or via maven command: 

```./mvnw spring-boot:run```

## Environments
| Environment | URL |
| ------------- | ------------- |
| Local | http://localhost:8089 |

## Swagger
The API documentation is available at: 

```${environmentUrl}/swagger-ui.html```