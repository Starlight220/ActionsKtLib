#!/bin/bash

cd /test/
echo "$(pwd -P)"

echo "Running Tests"

/test/gradlew runTest -q
