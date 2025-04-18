![schema](schema.png "Title")
___
**Launch project**
-
in the project's root dir:\
`mvn clean package`\
`sudo docker compose up -d --build`
___
**Microservices**
-
1. Config Server: http://localhost:8888 (health http://localhost:8888/actuator/health)
2. Eureka: http://localhost:8761
3. Gateway: http://localhost:8765 (routes: http://localhost:8765/actuator/gateway/routes)
5. organization-service: 8084 or through gateway
6. licensing-service: 8081 or through gateway (health: http://localhost:8081/health)

___
**Tools**
-
 - Keycloak: http://keycloak:7080
   - Required '127.0.0.1 keycloak' host mapping 
   - UI console auth: _admin_, _nimda_ 
   - Used for `organization-service`
 - PostgreSQL: http://localhost:5432
 - Redis: http://localhost:6379
___
**Get OAuth2 Token**
 -
POST: http://localhost:7080/realms/organizations-realm/protocol/openid-connect/token \
x-www-form-urlencoded:
 - grant_type: password
 - username: <keycloak_any_user_username>
 - password: <keycloak_any_user_password>

**Authorization Header:** Basic Auth:
 - Username: ostock
 - Password: DhzOq8bhc4wnpJsxQcGSFwH9lReNZFEt
___
API examples
-
 - Get License by organization's & license's ID: http://localhost:8765/licensing-service/v1/organization/d898a142-de44-466c-8c88-9ceb2c2429d3/license/f2a9c9d4-d2c0-44fa-97fe-724d77173c62
 - Get Organization by ID: http://localhost:8765/organization-service/v1/organization/d898a142-de44-466c-8c88-9ceb2c2429d3