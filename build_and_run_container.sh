#!/bin/bash

./gradlew clean build

version=$(git rev-parse --short HEAD)

docker build --tag=hotel-reservation-server:"$version" .

docker tag hotel-reservation-server:"$version" aaronburk/hotel-reservation-server:"$version"

docker push aaronburk/hotel-reservation-server:"$version"

docker run -p8882:8082 hotel-reservation-server:"$version"