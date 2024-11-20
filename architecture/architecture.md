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

## Communicatie tussen de services
Alle communicatie tussen de services gebeurt synchroon. De communicatie met de Messaging Service gebeurt asynchroon.

### US1:
- Synchroon: Wanneer er een post wordt geplaatst door de redacteur.
- Asynchroon: De message met de Message Service.
### US2:
- Synchroon: Wanneer de post wordt opgeslagen door de redacteur.
### US3:
- Synchroon: De update request voor de post van de redacteur.
- Asynchroon: De message met de Message Service.
### US4:
- Synchroon: Wanneer de gebruiker de posts ophaalt.
### US5:
- Synchroon: De posts moeten na het filteren terug gestuurd worden zodat de gebruiker ze meteen kan zien.
### US7:
- Synchroon: De get request en de update request van de posts.
- Asynchroon: De message met de Messaging Service.
### US8:
- Asynchroon: De message met de Messaging Service.
### US9:
- Synchroon: Wanneer de opmerkingen woden opgeslagen.
- Asynchroon: De message met de Messaging Service.
### US10:
- Synchroon: Wanneer de reactie wordt geplaatst.
- Asynchroon: De message met de Messaging Service.
### US11:
- Synchroon: Wanneer de reacties worden opgehaald.
### US12:
- Synchroon: Wanneer de reacties worden opgehaald, verwijderd of worden geupdate.
- asynchroon: De message met de Messaging Service.