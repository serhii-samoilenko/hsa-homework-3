#!/usr/bin/env bash
docker_dir=$(dirname "$0")
gradle_root_dir="$docker_dir/../../"
gradle_module_dir=$(readlink -f "$docker_dir/../")
module_name=$(basename "$gradle_module_dir")
command="docker build $gradle_root_dir -f $docker_dir/alpine.Dockerfile -t geo-service --build-arg module_name=$module_name"
echo "Running docker build: $command"
eval "$command"
