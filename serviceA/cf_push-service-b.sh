#!/usr/bin/env bash

../gradlew build

echo "Push service A as service B"
cf push --hostname service-b -f ./manifest-service-b.yml