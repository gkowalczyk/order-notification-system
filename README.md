

#  Order Notification System

Event-driven service for ingesting high-volume order status updates and
generating mock email notifications.

**Tech stack:**\
Spring Boot • Kafka • PostgreSQL • Docker • Resilience4j

------------------------------------------------------------------------

## Public Deployment (VPS)

The application is deployed on a public VPS using Docker and Docker
Compose.

### Base URL

http://213.199.45.51:18080

------------------------------------------------------------------------

## Verify the deployment

### Health check

``` bash
curl http://213.199.45.51:18080/actuator/health
```

Expected response:

``` json
{
  "status": "UP"
}
```
------------------------------------------------------------------------

### Ingest event

``` bash
curl -X POST http://213.199.45.51:18080/api/order-events   -H "Content-Type: application/json"   -d '{"shipmentNumber":"ABC123","recipientEmail":"a@b.com","recipientCountry":"PL","senderCountry":"DE","statusCode":10}'
```

Expected:

    HTTP 202 Accepted

Response contains `eventId`.


------------------------------------------------------------------------

##  Architecture

![Architecture
Diagram](https://github.com/gkowalczyk/order-notification-system/raw/refs/heads/main/schemat.bmp)

------------------------------------------------------------------------

##  Local Run (Docker)

### 1️⃣ Start Infrastructure

``` bash
docker compose up -d
```

------------------------------------------------------------------------

### 2️⃣ Verify Running Containers

``` bash
docker ps
```

You should see containers like:

-   `ons-postgres`
-   `kafka`

------------------------------------------------------------------------

### 3️⃣ Run the Spring Boot Application

#### Option A -- From IntelliJ

-   Open the main application class
-   Click **Run**

#### Option B -- Using Maven Wrapper

``` bash
./mvnw spring-boot:run
```

Application will start at:

    http://localhost:8080

------------------------------------------------------------------------

##  Application Health Check

``` bash
curl http://localhost:8080/actuator/health
```

Expected response:

``` json
{
  "status": "UP"
}
```

------------------------------------------------------------------------

##  Ingest Event 

### Send Order Event

``` bash
curl -X POST http://localhost:8080/api/order-events \
  -H "Content-Type: application/json" \
  -d '{
        "shipmentNumber":"ABC123",
        "recipientEmail":"a@b.com",
        "recipientCountry":"PL",
        "senderCountry":"DE",
        "statusCode":10
      }'
```

### Expected Response

    HTTP 202 Accepted

``` json
{
  "eventId": "generated-uuid"
}
```

------------------------------------------------------------------------

##  Verify Audit Persistence (PostgreSQL)

``` bash
docker exec -it ons-postgres psql -U orders_user -d orders \
  -c "select * from order_event_audit order by received_at desc limit 10;"
```

------------------------------------------------------------------------

##  Verify Email Mock

Check application logs for:

    MOCK EMAIL ->

Example:

    MOCK EMAIL -> to=a@b.com, subject=Shipment ABC123 updated

-----------------------------------------------------------------------