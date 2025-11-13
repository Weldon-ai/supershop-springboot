#!/bin/bash

# Navigate to your SuperShop project folder
cd ~/Downloads/supershop

# Start Spring Boot in the background
echo "Starting Spring Boot app..."
./mvnw spring-boot:run &
SPRING_PID=$!

# Wait a few seconds for Spring Boot to start
sleep 10

# Start NGROK tunnel
echo "Starting NGROK tunnel..."
ngrok http 8080 &
NGROK_PID=$!

# Wait a few seconds for NGROK to initialize
sleep 5

# Show NGROK public URL
echo "Fetching NGROK public URL..."
curl --silent http://127.0.0.1:4040/api/tunnels | grep -o '"public_url":"https[^"]*"' | sed 's/"public_url":"//;s/"//'

echo "SuperShop is live! Keep this terminal open."
echo "To stop: kill $SPRING_PID and $NGROK_PID"
