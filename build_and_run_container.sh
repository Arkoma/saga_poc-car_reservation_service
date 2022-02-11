#!/bin/bash

./gradlew clean build

version=$(git rev-parse --short HEAD)

docker build --tag=car-reservation-server:"$version" .

docker tag car-reservation-server:"$version" aaronburk/car-reservation-server:"$version"

docker push aaronburk/car-reservation-server:"$version"

docker run -p8883:8083 car-reservation-server:"$version"