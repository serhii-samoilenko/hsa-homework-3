# Highload Software Architecture 8 Lesson 3 Homework

Presentation
---

[Grafana](http://localhost:3000) contains 8 dashboards for different aspects of monitoring:

![dashboards](docs/dashboards.png "Dashboards")

## [Host Overview](http://localhost:3000/d/hsal2-01/host-overview)

Metrics about the host itself, like CPU, memory, disk, network, etc. In this demo, it's a Docker virtual machine.

![host overview](docs/host.png "Host Overview")

## [Docker Overview](http://localhost:3000/d/hsal2-02/docker-overview)

General metrics about Docker containers, like CPU, memory, network, etc.

![docker overview](docs/docker-overview.png "Docker Overview")

## [Docker Containers](http://localhost:3000/d/hsal2-03/docker-containers)

Individual container metrics

![docker containers](docs/docker-containers.png "Docker Containers")

## [Elasticsearch](http://localhost:3000/d/hsal2-04/elasticsearch)

![elasticsearch](docs/elasticsearch.png "Elasticsearch")

## [MongoDB](http://localhost:3000/d/hsal2-05/mongodb)

![mongodb](docs/mongodb.png "MongoDB")

## [JVM Applications](http://localhost:3000/d/hsal2-06/jvm-applications)

JVM metrics for both services

![jvm applications](docs/jvm-apps.png "JVM Applications")

## [Application Business Metrics](http://localhost:3000/d/hsal2-07/application-business-metrics)

Metrics about the application itself, like requests per second, response time, cache performance, method invocations, etc.

![application business metrics](docs/business.png "Application Business Metrics")

## [Nginx Basics](http://localhost:3000/d/hsal2-08/nginx-basics)

Basic metrics about Nginx. Looks like open source version of nginx doesn't provide much information.

![nginx basics](docs/nginx.png "Nginx Basics")
