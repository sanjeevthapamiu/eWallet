./mvnw clean package
docker-compose down
docker build -t ewallet:latest .
docker-compose up -d && docker-compose logs -f