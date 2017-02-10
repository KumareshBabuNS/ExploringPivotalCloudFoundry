#!/usr/bin/env bash

../gradlew build

echo "Push application A as service B"
cf push --hostname service-b -f ./manifest-service-b.yml