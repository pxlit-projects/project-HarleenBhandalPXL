# Fullstack Java Project

## Harleen Bhandal (3AONA)

## Folder structure

- Readme.md
- _architecture_: this folder contains documentation regarding the architecture of your system.
- `docker-compose.yml` : to start the backend (starts all microservices)
- _backend-java_: contains microservices written in java
- _demo-artifacts_: contains images, files, etc that are useful for demo purposes.
- _frontend-web_: contains the Angular webclient

Each folder contains its own specific `.gitignore` file.  
**:warning: complete these files asap, so you don't litter your repository with binary build artifacts!**

## How to setup and run this application

:heavy_check_mark:_(COMMENT) Add setup instructions and provide some direction to run the whole  application: frontend to backend._

- Make sure you have 3 Mysql Databases ready:  postservice_db, reviewservice_db, commentservice_db
- Run docker-compose.yml
- Run microservices of backend in this order:
    1. ConfigServiceApplication
    2. DiscoveryApplication
    3. GatewayServiceApplication
    4. PostServiceApplication
    5. ReviewServiceApplication
    6. CommentServiceApplication
- Go to ```http://localhost```
