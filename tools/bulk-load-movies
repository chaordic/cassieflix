#!/bin/bash
set -e

if [[ $# != 1 ]]; then
  echo "Usage: $0 <movieJson>"
  exit 1
fi

while read json
do
    curl -X POST -d"$json" http://localhost:8080/movies --header "Content-Type:application/json"
    echo
done < $1
