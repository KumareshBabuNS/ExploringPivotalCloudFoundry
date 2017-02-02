#!/usr/bin/env bash

echo "Unbind route"
cf unbind-route-service local.pcfdev.io route-service -n service-a


echo "Remove route service"
cf delete-service route-service