# FROM payara/server-full:5-SNAPSHOT
# COPY target/library-service.war $DEPLOY_DIR

FROM payara/micro:5-SNAPSHOT
COPY target/library-service.war /opt/payara/deployments
