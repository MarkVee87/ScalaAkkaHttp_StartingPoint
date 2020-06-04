# ScalaAkkaHttp_StartingPoint

This codebase is designed as a simple starting point for an Akka HTTP application.

### Modules:

#### Api

A simple HTTP API that can be easily extended.

#### Performance

Basic performance tests written using Gatling.

### Useful commands

Create a fat JAR for dockerising `sbt clean assembly`

Build API docker image `cd api` then `docker build . -t starting-point-api`

### Running the app with monitoring (Prometheus and Grafana)

Note: Make sure you've built the api docker image as shown in the section above, and you are in the root of the project before running these commands.
 
Windows (PowerShell) `docker-compose -f .\docker\docker-compose-api-and-monitoring.yml up -d`
Unix `docker-compose -f ./docker/docker-compose-api-and-monitoring.yml up -d`

### Sources

* https://dzone.com/articles/dockerizing-your-scala-app
* https://www.scala-sbt.org/1.x/docs/Multi-Project.html
* https://stackoverflow.com/a/58024050/3059314
* https://timber.io/blog/promql-for-humans/

### Very basic PromQL queries

Show increase in 2xx responses over the last 10 minutes `increase(http_server_requests_total{http_status_code="2xx"}[10m])`

Show increase in 4xx responses over the last 10 minutes `increase(http_server_requests_total{http_status_code="4xx"}[10m])`