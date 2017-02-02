#!/usr/bin/env bash

../gradlew build

echo "Push Gatewat App"
cf push --hostname gateway