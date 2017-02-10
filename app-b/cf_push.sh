#!/usr/bin/env bash

../gradlew build

echo "Push application A"
cf push --hostname app-b