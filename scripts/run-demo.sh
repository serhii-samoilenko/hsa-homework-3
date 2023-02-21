#!/bin/sh

# start the container stack
docker-compose --profile siege up -d

# wait for the service to be ready
while ! curl --fail --silent --head http://localhost:8080/actuator/health; do
  sleep 1
done

# open the Grafana dashboards
open http://localhost:3000/dashboards
