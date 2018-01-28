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
    public Class<Version1Resource> v1() {
        // this does not work due to injection
        return Version1Resource.class;
    }

    @Path("/v2")
    public Class<Version2Resource> v2() {
        // this works because no injection is required
        return Version2Resource.class;
    }

    /**
     * This sub resource is a CDI bean, but injection does not work here.
     */
    @RequestScoped
    public static class Version1Resource {
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
    public static class Version2Resource {

        private final Logger logger = Logger.getLogger(Version2Resource.class.getName());

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String get() {
            logger.info("Version 2.0 sub resource works.");
            return "v2.0";
        }
    }

}
