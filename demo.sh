#!/usr/bin/env bash

echo "This script will launch a docker-compose with services, generate some load on them and open the Grafana dashboards to observe."
read -r -p "Continue (Y/n)? " answer

if [[ ! $answer =~ ^[Yy]$ ]] && [[ -n $answer ]]; then
  echo "Bye!"
  exit 0
fi

echo "Starting docker-compose in the background..."
docker-compose up nginx grafana -d

printf "\nWaiting for the Nginx...  "
while ! curl --fail --silent --head -o /dev/null http://localhost:8080/actuator/health; do
  sleep 1
done
echo "OK"
printf "Waiting for the Grafana...  "
while ! curl --fail --silent --head -o /dev/null http://localhost:3000; do
  sleep 1
done
echo "OK"

printf "\nTo log in to the Grafana dashboard, use the following credentials:\n\n"
echo "  username: admin"
echo "  password: admin"
printf "\n"
echo "Launching Siege..."
docker-compose up siege -d
sleep 2

## open the Grafana dashboards
open http://localhost:3000/dashboards
