package com.packtpub.javaee8.boundary;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * A simple Jersey test for the {@link VersionResource}.
 */
public class VersionResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(VersionResource.class);

        // this here is required for dependency injection
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(Logger.getAnonymousLogger());
            }
        });

        return config;
    }

    @Override
    protected void configureClient(ClientConfig config) {
        // for JSON-B marshalling
        config.register(JsonBindingFeature.class);
    }

    @Test
    public void v2() {
        Response response = target("/version/v2").request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("v2.0"));
    }

    @Test
    public void v1() {
        Response response = target("/version/v1").request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("v1.0"));
    }
}