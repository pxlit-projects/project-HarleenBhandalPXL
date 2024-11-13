# Architecture

![Architecture](https://github.com/pxlit-projects/project-HarleenBhandalPXL/blob/main/architecture/Architectuur.png)

# Uitleg
## Post Service
Laat redacteurs toe om posts aan te maken en aan te passen.
Deze microservice heeft zijn eigen database.

## Review Service
Laat redacteurs toe om posts goed te keuren of af te wijzen.
Deze microservice heeft zijn eigen database.

## Comment Service
Laat gebruikers toe om reacties te plaatsen op posts
Deze microservice heeft zijn eigen database.

## Config Service
Hier staan de configuraties van de microservices gecentraliseerd. 

## Discovery Service
De microservices gaan zich registreren bij de Discovery Service. Deze is gemaakt met Eureka Netflix. Dit vergemakkelijkt de communicatie en de samenwerking tussen de services.

## Gateway
De Gateway dient als centraal punt voor de client requests. De Gateway stuurt de request naar de juiste service.

## Messaging Service
De Messaging Service maakt gebruik van RabbitMQ. Deze gaat asynchroon communiceren met de services om meldingen tussen de services te ontvangen.
