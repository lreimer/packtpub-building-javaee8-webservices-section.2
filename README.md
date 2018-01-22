# Building Web Services with Java EE 8
# Section 2: Building Synchronous Web Services


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
