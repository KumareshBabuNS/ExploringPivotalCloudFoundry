#!/usr/bin/env bash

# Build route service
../gradlew build

echo "1. Push service"
cf push --hostname https://gateway-route-service.local.pcfdev.io

echo "2. Create a user provided service that contains the route service configuration information"
cf create-user-provided-service gateway-route-service -r https://gateway-route-service.local.pcfdev.io

echo "3. Bind route"
cf bind-route-service local.pcfdev.io gateway-route-service -n service-a