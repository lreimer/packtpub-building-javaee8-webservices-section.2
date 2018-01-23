# Building Web Services with Java EE 8 <br>Section 2: Building Synchronous Web Services

## Videos

### Video 2.1: Basic REST API

In this video we are going to implement a basic REST API for books.

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/books | 200 | Get the list of all books |
| POST   | /api/books | 201 | Create a new book from JSON body |
| PUT    | /api/books/{isbn} | 200 | Update a book with given ISBN |
| DELETE | /api/books/{isbn} | 200 | Delete a book with given ISBN | 


### Video 2.2: Subresource Locators

In this video we are extending the book API. We add loans for books using
a sub resource (since loans are dependent on books).

| Method | URI | Status | Description |
|--------|-----|--------|-------------|
| GET    | /api/books/{isbn}/author | 200 | Get the author for given book |
| GET    | /api/books/{isbn}/loans | 200 | Get the loans for given book |
| POST   | /api/books/{isbn}/loans | 201 | Create a new loan on a given book |
| DELETE | /api/books/{isbn}/loans/{loanId} | 200 | Delete a loan and return given book | 

Now there are several ways to implement Subresource locators. In all cases
you can perform partial processing by annotating methods using @Path only.
The return type will then be scanned for further JAX-RS annotations.

### Video 2.3: Error Handling

For REST APIs it is considered best practice to signal errors and abnormal
behaviour using HTTP Status code. This includes expected as well as unexpected
exceptional behaviour.

There are a couple of options you can choose from:
* Handle the error explicitly, by checking or catching an exception and then
set a suitable HTTP status code on the `Response` object. This could be a *404*
if something could not be found.
* The other alternative you have is to throw `WebApplicationException` or one
of its subclasses. JAX-RS will handle these and translate them to the appropriate
HTTP status code.
* The final and most flexible option is to implement the `ExceptionMapper` interface
and perform a custom mapping from exception to `Response`.

### Video 2.4: JAX-RS Client API

In this video we add a small standalone CLI client program that uses
the JAX-RS Client APIs to access the Library Service REST API. The
client performs several CRUD interactions with `Book` and `Loan`. 

## Containerization

When you want to use the Payara Server Full base image, write the following `Dockerfile`:
```
FROM payara/server-full:5-SNAPSHOT
COPY target/library-service.war $DEPLOY_DIR
```

When you want to use the Payara Micro base image, write the following `Dockerfile`:
```
FROM payara/micro:5-SNAPSHOT
COPY target/library-service.war /opt/payara/deployments
```

```bash
$ docker build -t library-service:1.0 .

$ docker run -it -p 8080:8080 library-service:1.0
$ docker run -d -p 8080:8080 library-service:1.0
```
