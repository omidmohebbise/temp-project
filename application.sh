#!/bin/bash

# Stop and remove the existing container if it exists
docker stop application-demo-container >/dev/null 2>&1
docker rm application-demo-container >/dev/null 2>&1

# Remove the old "application-demo" image if it exists
docker rmi application-demo >/dev/null 2>&1

# Build the Docker image with the "application-demo" tag
docker build -t application-demo .

# Run a container from the "application-demo" image, mapping port 8070
docker run -d -p 8070:8080 --name application-demo-container application-demo

# Sleep for a moment to ensure the application has started
sleep 2



# URL to open
url="http://localhost:8070/swagger-ui/index.html"

echo Open below link
echo $url



