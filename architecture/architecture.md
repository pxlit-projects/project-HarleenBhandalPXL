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
- Synchroon: Wanneer er een post wordt geplaatst door de redacteur. Van de frontend wordt er een request gestuurd naar de gataway, die stuurt de request naar de Post Service.
- Asynchroon: Message van de Post Service naar de Review Service zodat ze weten dat er een nieuwe post klaar staat voor een review.
### US2:
- Synchroon: Wanneer de post wordt opgeslagen door de redacteur. Van de frontend wordt er een request gestuurd naar de Gateway, die stuurt dan de request naar de Post Service.
### US3:
- Synchroon: De update request voor de post van de redacteur. Van de frontend wordt er een request via de gateway naar de Post Service gestuurd.
- Asynchroon: Message van de Post Service naar de Review Service zodat de post opnieuw gereviewed kan worden
### US4:
- Synchroon: Wanneer de gebruiker de posts ophaalt. Via de frontend wordt er een request gestuurd via de gateway naar de Post Service
### US5:
- Synchroon: De posts moeten na het filteren terug gestuurd worden zodat de gebruiker ze meteen kan zien. Filter data wordt via de gateway naar de Post Service gestuurd. Daar wordt de query uitgevoerd en die stuurt de data terug naar de frontend.
### US7:
- Synchroon: De get request en de update request van de posts worden vanuit de frontend via de gateway naar de Review Service gestuurd.
- Asynchroon: Message van review Service naar de Post Service voor de statuswijziging van de post.
### US8:
- Asynchroon: Message voor de redacteur zodat hij weet dat er een nieuwe status is van zijn post.
### US9:
- Synchroon: Wanneer de opmerkingen woden opgeslagen. Request vanuit de frontend via de gateway naar de review service.
- Asynchroon: Message van de review service naar de post service zodat de redacteur weet dat er een opmerking is geplaatst.
### US10:
- Synchroon: Wanneer de reactie wordt geplaatst. Request vanuit de frontend via de gateway naar de comment service.
### US11:
- Synchroon: Wanneer de reacties worden opgehaald. Request vanuit de frontend via de gateway naar de comment service.
### US12:
- Synchroon: Wanneer de reacties worden opgehaald, verwijderd of worden geupdate. Request vanuit de frontend via de gateway service naar de comment service.
