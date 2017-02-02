#!/usr/bin/env bash

# Build route service
../gradlew build

echo "1. Push service"
cf push


echo "2. Create a user provided service that contains the route service configuration information"
cf create-user-provided-service route-service -r https://route-service-unchurlish-microwave.local.pcfdev.io



echo "3. Bind route"
cf bind-route-service local.pcfdev.io route-service -n service-a