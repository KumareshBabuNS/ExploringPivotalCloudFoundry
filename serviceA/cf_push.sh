#!/usr/bin/env bash

../gradlew build

echo "Push service A"
cf push --hostname service-a