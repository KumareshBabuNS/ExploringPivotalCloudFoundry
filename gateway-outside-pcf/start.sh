#!/usr/bin/env bash

echo "Starting Gateway on localhost.."

export SIG_SECRET_KEY=JWTSecretKeyDontUseInProduction!
export EXTERNAL_SIG_SECRET_KEY=JWTExtSecKeyDontUseInProduction!
export EXTERNAL_ENC_SECRET_KEY=JWTExtEncKeyDontUseInProduction!

java -jar gateway.jar