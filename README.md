# toggle-service
This application allows the management of toggles so that any application/service may apply the feature toggling pattern without having to redeploy.

## Run the application
This application is written with Java 11. You may try to downgrade it to Java 8 in the pom file but there is no guarantee that it will work.

To run, open a terminal and type:
`./mvnw clean spring-boot:run`

There is also a mvnw.cmd file for Windows users.

The application will start on: `http://localhost:8080/`

## Using the application
To try out the application go to `http://localhost:8080/swagger-ui.html`

## Accessing the DB
This application uses the H2 in-memory database, meaning that the data will be removed once the application is stopped.

To access the DB go to: `http://localhost:8080/h2-console`

Enter `jdbc:h2:~/test` in the JDBC URL field and connect to it.
