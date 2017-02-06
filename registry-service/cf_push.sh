#!/usr/bin/env bash

echo "Push Registry App"
cf push --hostname registry-service

echo "Registry as a service"
# create-user-provided-service
cf cups registry-service -p '{"uri":"http://registry-service.local.pcfdev.io"}'