# FROM payara/server-full:5.181
# COPY target/library-service.war $DEPLOY_DIR

FROM payara/micro:5.181
COPY target/library-service.war /opt/payara/deployments

ENTRYPOINT ["java", "-Xmx196m", "-client", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--noCluster", "--deploymentDir", "/opt/payara/deployments"]
