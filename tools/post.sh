#!/bin/bash

if [[ $# != 1 ]]; then
  echo "Usage: $0 <movieJson>"
  exit 1
fi

FILENAME=$1

curl -X POST -d @$FILENAME http://localhost:8080/movies --header "Content-Type:application/json"
