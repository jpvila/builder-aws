#!/bin/bash

echo "Waiting for 120 seconds before checking health.."
sleep 120

status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:80)
if [[ "$status_code" -ne 200 ]] ; then
  echo "App is not healthy - $status_code"
  exit 1
else
  echo "App is responding with $status_code"
  exit 0
fi


