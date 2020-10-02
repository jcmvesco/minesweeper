# minesweeper-api

## Tecnologies used
* Java 14
* Spring Boot 2.3.4
* Spring Data Jpa
* Yaml 1.27
* Springfox Swagger 3.0.0
* MySQL 8.0.21

## Testing
Swagger was used to document exposed services in this api, so you could find the details here:
https://minesweeper-amz.herokuapp.com/api/minesweeper/swagger-ui/

To test this services with a custom client, you could find inside the resources' folder, 3 files made in python with examples and data in order to use this api.


## Notes
The project is divided in three layers (api, domain and controller) in order to separate responsibilities and made it able to scale.
* Domain: This layer has the model classes, exceptions, repositories and the service.
* Controller: This layer has the entry points of the project and handles the output to clients.
* Api: Has the request and response classes used by the controller layer.

I started building this project with the domain classes, and some logic of the game itself and using a H2 database to speed up the whole process, then I replace it with a MySQL.
Some events in the game (like winning, loosing, and some actions) are handled with business exceptions in order to keep the game logic clean of decisions related with the responses, and the responsibilities between layers.
I used Yaml to provide clarity in the properties' configuration.