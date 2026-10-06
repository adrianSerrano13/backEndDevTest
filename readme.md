SIMILAR PRODUCTS

The service exposes an API endpoint that retrieves the products similar to a given product and returns their details ordered according to the similarity provided by the external product service.

TECHNOLOGIES:
      Java 17
      Spring Boot
      Spring WebFlux
      Project Reactor
      WebClient
      Maven
      Docker & Docker Compose
      OpenAPI / Swagger
      k6
      InfluxDB
      Grafana
      Architecture

ARCHITECTURE:

      Controller
          ↓
      Service
          ↓
      ProductClient
          ↓
      External Product API
      
      The application uses a fully reactive flow based on Mono and Flux, from the HTTP controller through the service layer to the external HTTP client.
      
      The external product requests are executed concurrently with a configurable concurrency limit while preserving the order returned by the similarity service.

CONFIGURATION:

      The main configuration is located at:
        
      similar-products/src/main/resources/application.properties
        
      Default configuration:
        
      similar-products.client.base-url=http://localhost:3001
      similar-products.client.max-concurrency=3
      similar-products.client.response-timeout=10s
      similar-products.client.connect-timeout-millis=2000
        
      These values can be adjusted depending on the environment.

API Documentation

      The API is documented using OpenAPI.
      
      Once the application is running, Swagger UI is available at:
      
      http://localhost:5000/swagger-ui.html
      
      The generated OpenAPI specification is available at:
      
      http://localhost:5000/v3/api-docs
      
ERROR HANDLING:

      Errors returned by the external product service are handled and mapped to appropriate HTTP responses.
      
      The API currently handles:
      
      404 → Product not found
      5xx → Unable to retrieve product information
      Connection errors and timeouts → Unable to retrieve product information
      
      A response timeout is configured for the external HTTP client to prevent a slow downstream service from keeping requests open indefinitely.
