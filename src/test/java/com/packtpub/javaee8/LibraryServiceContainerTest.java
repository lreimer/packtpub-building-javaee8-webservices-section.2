package com.packtpub.javaee8;

import com.packtpub.javaee8.domain.Book;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LibraryServiceContainerTest {

    @ClassRule
    public static GenericContainer container = new GenericContainer(new ImageFromDockerfile()
            .withFileFromFile("Dockerfile", new File(basePath(), "Dockerfile"))
            .withFileFromFile("target/library-service.war", new File(basePath(), "target/library-service.war")))
            .waitingFor(Wait.forHttp("/library-service/api/application.wadl")
                    .withStartupTimeout(Duration.ofSeconds(90)))
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(LibraryServiceContainerTest.class)))
            .withExposedPorts(8080)
            .withExtraHost("localhost", "127.0.0.1");

    private WebTarget api;
    private Client client;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .register(JsonBindingFeature.class)
                .build();

        String uri = String.format("http://%s:%s/library-service/api",
                container.getContainerIpAddress(), container.getMappedPort(8080));
        api = client.target(uri);
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void getVersion() {
        Response response = api.path("/version/v2").request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("v2.0"));
    }

    @Test
    public void getBooks() {
        List<Book> books = api.path("/books").request().accept(MediaType.APPLICATION_JSON).get(bookList());
        assertThat(books, hasSize(4));
    }

    @Test
    public void getHitchhikersGuideToTheGalaxy() {
        Book book = api.path("/books/{isbn}").resolveTemplate("isbn", "0345391802")
                .request().accept(MediaType.APPLICATION_JSON).get(Book.class);

        assertThat(book, is(notNullValue()));
        assertThat(book.getIsbn(), is(equalTo("0345391802")));
        assertThat(book.getTitle(), is(equalTo("The Hitchhiker's Guide to the Galaxy")));
        assertThat(book.getAuthor().getName(), is(equalTo("Douglas Adams")));
        assertThat(book.getLoans(), hasSize(1));
    }

    private static String basePath() {
        URL resource = LibraryServiceContainerTest.class.getResource("/");
        File file;
        try {
            file = new File(resource.toURI()).getParentFile().getParentFile();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        return file.getAbsolutePath();
    }

    private static GenericType<List<Book>> bookList() {
        return new GenericType<List<Book>>() {
        };
    }
}
