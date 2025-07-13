🔹 1. Launch Elasticsearch
Run docker-compose up -d

Verify it’s running using curl http://localhost:9200

🔹 2. Build & Run the Spring Boot Application
Run ./mvnw clean install

Then start: ./mvnw spring-boot:run

App will run at http://localhost:8090

🔹 3. Auto-Index Sample Data
Ensure sample-courses.json is placed in src/main/resources

Courses are indexed automatically at startup

Confirm via: curl http://localhost:9200/_cat/indices?v
(look for courses index)

🔹 4. Call Search API
Endpoint: GET /api/courses/search

Available Filters:

q – keyword search

category, type

minAge, maxAge

minPrice, maxPrice

startDate – filter courses from given date (ISO format)

sort – priceAsc, priceDesc, or default (upcoming)

page, size – for pagination

🔹 5. Sample Requests
Search by keyword:
GET /api/courses/search?q=math

Filter by category/type:
GET /api/courses/search?category=Science&type=CLUB

Price range + sort:
GET /api/courses/search?minPrice=100&maxPrice=300&sort=priceDesc
