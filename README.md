# Ninja Server Application

Small REST API Service that provides core device management and service handling. This stateless service allows 
you to manage devices, services and provides comprehensive cost reports.  

- [Stack Summary](#stack-summary)
- [Public API Endpoints](#api-endpoints) 
- [Development Setup](#development-setup)

## Stack Summary:
- Java 11
- Spring Boot 2.4.2
- Spring 5.2
- Docker 
- PostgresSQL 13
- JWT
- Liquibase 3
- Spring JPA
- JUnit 5.7
- Mockito 3.6
- Gradle 6


## API Endpoints

### **Endpoint Summary:**

|URL|Method|Description|
---|---|---
**Authentication:**||
[/api/v1/auth/login](#login)|POST|Authenticate user with username and password
**Device Management:**||
[/api/v1/companies/{companyId}/devices](#get-list-of-devices)|GET|List all devices for the given company id
[/api/v1/companies/{companyId}/devices](#create-device)|POST|Create a device for the given company id
[/api/v1/companies/{companyId}/devices/{id}](#update-device)|PUT|Update the given device information
[/api/v1/companies/{companyId}/devices/{id}](#delete-device)|DELETE|Delete the given device for the company id
**Management of services for Devices :**||
[/api/v1/companies/{companyId}/devices/{id}/services](#add-service-to-device)|POST|Add a service to the given device
[/api/v1/companies/{companyId}/devices/{id}/services/{id}](#remove-service-from-device)|DELETE|Remove the specified service for the given device
**Company Services Summary:**||
[/api/v1/companies/{companyId}/services](#company-services-summary)|GET|List all services and number of devices 
**Company Monthly Billing Summary:**||
[/api/v1/companies/{companyId}/monthly-cost](#generate-company-monthly-billing)|GET|Generates a summary containing services and total monthly cost

---

### Login:

```
POST http://localhost:8000/api/v1/auth/login
Content-Type: application/json
```

**Payload:**

```
{
	"username": "admin",
	"password": "Pa$$word"
}
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "username": "admin",
  "token": "Bearer xxx.yyy.zzz"
}
```
---

### Get list of devices:
```
GET http://localhost:8000/api/v1/companies/{companyId}/devices
Authorization: Bearer xxx.yyy.zzz
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": "45cc44b8-8ba4-4210-bf16-7fd0f1b69db2",
        "companyId": "0ee4a0fa-224c-4b0b-ac22-101acab87b7b",
        "systemName": "Domain Controller",
        "type": "WINDOWS_SERVER",
        "createdAt": "2021-01-31T23:18:09.178481",
        "updatedAt": "2021-01-31T23:18:09.180195"
    },
    {
        "id": "54d6810f-5e81-408d-a16d-04f1fbfbad8d",
        "companyId": "0ee4a0fa-224c-4b0b-ac22-101acab87b7b",
        "systemName": "Mac workstation",
        "type": "MAC",
        "createdAt": "2021-01-31T23:19:33.994986",
        "updatedAt": "2021-01-31T23:19:33.995007"
    }
]
```

---

### Create device:
```
POST http://localhost:8000/api/v1/companies/{companyId}/devices
Authorization: Bearer xxx.yyy.zzz
Content-Type: application/json
```

**Payload:**

```
{
  "systemName": "Mac workstation",
  "type": "MAC"
}
```

**Body Parameters:**

Name|Type|Required|Accepted values|Default|Description
---|---|---|---|---|---
systemName|String|Yes|||Name of the device
type|String|Yes|["WINDOWS_SERVER", "WINDOWS_WORK_STATION", "MAC"]||Type of device

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "54d6810f-5e81-408d-a16d-04f1fbfbad8d",
  "companyId": "0ee4a0fa-224c-4b0b-ac22-101acab87b7b",
  "systemName": "Mac workstation",
  "type": "MAC",
  "createdAt": "2021-01-31T23:19:33.994986",
  "updatedAt": "2021-01-31T23:19:33.995007"
}
```

---

### Update device:
```
PUT http://localhost:8000/api/v1/companies/{companyId}/devices/{deviceId}
Authorization: Bearer xxx.yyy.zzz
Content-Type: application/json
```

**Payload:**

```
{
  "systemName": "New system name",
  "type": "Windows Server"
}
```

**Body Parameters:**

Name|Type|Required|Accepted values|Default|Description
---|---|---|---|---|---
systemName|String|Yes|||Name of the device
type|String|Yes|["WINDOWS_SERVER", "WINDOWS_WORK_STATION", "MAC"]||Type of device

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "54d6810f-5e81-408d-a16d-04f1fbfbad8d",
  "companyId": "0ee4a0fa-224c-4b0b-ac22-101acab87b7b",
  "systemName": "New system name",
  "type": "WINDWS_SERVER",
  "createdAt": "2021-01-31T23:19:33.994986",
  "updatedAt": "2021-01-31T23:19:33.995007"
}
```

---

### Delete device:
```
DELETE http://localhost:8000/api/v1/companies/{companyId}/devices/{deviceId}
Authorization: Bearer xxx.yyy.zzz
```

**Response:**

```
HTTP/1.1 204 No Content
```

---

### Add service to device:
```
POST http://localhost:8000/api/v1/companies/{companyId}/devices/{id}/services
Authorization: Bearer xxx.yyy.zzz
Content-Type: application/json
```

**Payload:**

```
{
  "serviceId": "1b023d42-1502-4e3e-acf2-f91d8ca50baf"
}
```

**Body Parameters:**

Name|Type|Required|Accepted values|Default|Description
---|---|---|---|---|---
serviceId|String|Yes|||UUID of the service to be added to the device

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": "2e0c4603-2a4e-45be-8398-a9c7ecec5b29",
  "serviceId": "1b023d42-1502-4e3e-acf2-f91d8ca50baf",
  "serviceName": "Windows Antivirus",
  "serviceDescription": "",
  "purchasedAt": "2021-01-31T23:42:22.047565"
}
```

---

### Delete device:
```
DELETE http://localhost:8000/api/v1/companies/{companyId}/devices/{deviceId}/services/{deviceServiceId}
Authorization: Bearer xxx.yyy.zzz
```

**Response:**

```
HTTP/1.1 204 No Content
```

---

### Company Services Summary:
```
GET http://localhost:8000/api/v1/companies/{companyId}/services
Authorization: Bearer xxx.yyy.zzz
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": "1b023d42-1502-4e3e-acf2-f91d8ca50baf",
    "name": "Windows Antivirus",
    "description": "",
    "price": 5,
    "numberOfDevices": 1
  },
  {
    "id": "3986ccb1-0890-451b-b819-e48b5b83ccab",
    "name": "Mac Antivirus",
    "description": "",
    "price": 7,
    "numberOfDevices": 1
  },
  {
    "id": "73b27887-4da7-451f-bb91-c9fd3989c89e",
    "name": "TeamViewer",
    "description": "",
    "price": 2,
    "numberOfDevices": 2
  },
  {
    "id": "9ea98956-7651-42e8-a704-a55acb55ea1d",
    "name": "Cloudberry",
    "description": "",
    "price": 3,
    "numberOfDevices": 2
  },
  {
    "id": "f8bb1483-2e08-49ea-a506-c726cd0a2d6c",
    "name": "PSA",
    "description": "",
    "price": 2,
    "numberOfDevices": 2
  }
]
```

---

### Generate company monthly billing:
```
GET http://localhost:8000/api/v1/companies/{companyId}/monthly-cost
Authorization: Bearer xxx.yyy.zzz
```

**Response:**

```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "totalCost": 34,
  "services": [
    {
      "serviceName": "Windows Antivirus",
      "cost": 5
    },
    {
      "serviceName": "Mac Antivirus",
      "cost": 7
    },
    {
      "serviceName": "TeamViewer",
      "cost": 4
    },
    {
      "serviceName": "Cloudberry",
      "cost": 6
    },
    {
      "serviceName": "PSA",
      "cost": 4
    },
    {
      "serviceName": "Devices cost",
      "cost": 8
    }
  ]
}
```

## Development Setup
### Requirements:
- [Java Development Kit 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Docker](https://www.docker.com/products/docker-desktop)

### Installation:

Let's clone the repo from Github using the following command:

```
$ git clone git@github.com:mookofe/rmm-services-server-app.git
```

Next step setup PostgresSQL using docker:

```
$ cd rmm-services-server-app
$ docker-compose up -d
```

Run tests:

```batch
$ ./gradlew test
```

Execute Application:

```batch
$ ./gradlew bootRun
```

### Default Development Data:

For a straight forward development setup I'm using Liquibase to generate the database schema and data seeding.

**Companies:**

id|name
---|---
0ee4a0fa-224c-4b0b-ac22-101acab87b7b|Demo Company

**Services:**

id|name|price
---|---|---
1b023d42-1502-4e3e-acf2-f91d8ca50baf|Windows Antivirus|5.00
3986ccb1-0890-451b-b819-e48b5b83ccab|Mac Antivirus|7.00
9ea98956-7651-42e8-a704-a55acb55ea1d|Cloudberry|3.00
f8bb1483-2e08-49ea-a506-c726cd0a2d6c|PSA|2.00
73b27887-4da7-451f-bb91-c9fd3989c89e|TeamViewer|2.00

**Default User:**

```batch
username: admin
password: Pa$$word
```