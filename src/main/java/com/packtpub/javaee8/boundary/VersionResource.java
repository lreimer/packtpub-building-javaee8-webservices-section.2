package com.packtpub.javaee8.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * A version REST resource for path based API versioning.
 */
@Path("version")
@RequestScoped
public class VersionResource {

    @Path("/v1")
    public Class<V1> v1() {
        // this does not work due to injection
        return V1.class;
    }

    @Path("/v2")
    public Class<V2> v2() {
        // this works
        return V2.class;
    }

    /**
     * This sub resource is a CDI bean, but injection does not work here.
     */
    @RequestScoped
    public static class V1 {
        @Inject
        private Logger logger;

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String get() {
            logger.info("Version 1.0 sub resource does not work.");
            return "v1.0";
        }
    }

    /**
     * This sub resource does not use or require injection.
     */
    public static class V2 {

        private final Logger logger = Logger.getLogger(V2.class.getName());

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String get() {
            logger.info("Version 2.0 sub resource works.");
            return "v2.0";
        }
    }

}
