# Quarkus REST API Project for CouchDB

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

# Run REST API
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/exercise-quarkus-jvm .
docker run -i --rm -p 8080:8080 quarkus/exercise-quarkus-jvm

# Couch DB

Run Couch DB:
docker run -d -p 5984:5984 -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=student \
--network lib-network --network-alias couchdb \
-v /home/harald/couchdb/data:/opt/couchdb/data \
-v /home/harald/couchdb/config:/opt/couchdb/etc/local.d --name couchdb couchdb:3

# Curl Commands 
[Overview and more commands] (https://documenter.getpostman.com/view/14671395/Uyr4HyxK#f9a1425b-78fd-4030-85cb-3751df5bd3f4)
Please use the postman documentation as it also includes test cases and can be easily implemented in Postman (installed on VM).
Else:
getAll: curl --location --request GET 'http://localhost:8080/api/v1/getall'
getByISBN: curl --location --request GET 'http://localhost:8080/api/v1/get_isbn/978-3-15-009145-6'
getBookByLang: curl --location --request GET 'http://localhost:8080/api/v1/get_lang/de'
getHealth: curl --location --request GET 'http://localhost:8080/api/v1/health'
getCount: curl --location --request GET 'http://localhost:8080/api/v1/count'
createBook: curl --location --request PUT 'http://localhost:8080/api/v1/create'--data-raw '{"author": "Ellis, Bret Easton", "title": "American Psycho", "lang": "en", "isbn": "978-1-5290-7715-5"}'

For Kubernetes deployment change localhost and port!
