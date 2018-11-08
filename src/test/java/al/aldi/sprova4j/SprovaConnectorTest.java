package al.aldi.sprova4j;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SprovaConnectorTest {

    final String apiAddress = "http://127.0.0.1:8181/";
    final String username = "admin";
    final String password = "admin";
    MockWebServer server = null;
    SprovaConnector connector = null;

    @Before
    public void setUp() {
        try {
            server = new MockWebServer();
            server.start(8181);

            MockResponse mockedResponse = new MockResponse();
            mockedResponse.setResponseCode(200);
            mockedResponse.setBody("{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWUwNWYzN2RkYTU5NzFkYzIwYzllMjEiLCJ1c2VybmFtZSI6ImFkbWluIiwiYWRtaW4iOnRydWUsImZpcnN0bmFtZSI6IkFkbWluaXN0cmF0b3IiLCJsYXN0bmFtZSI6IkFkbWluaXN0cmF0b3IiLCJpYXQiOjE1MjvxMDkxMDMsImV4cCI6MTc4NjMwOTEwM30.eJOHpQm231P4YjVhA3fXKrRWNbgZwFA8zO4yKCilZy0\",\"message\":\"Successfully logged in!\"}");
            server.enqueue(mockedResponse);

            connector = new SprovaConnector(apiAddress, username, password);
            assertNotNull("connection object should exist", connector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnect() {
        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody("{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWUwNWYzN2RkYTU5NzFkYzIwYzllMjEiLCJ1c2VybmFtZSI6ImFkbWluIiwiYWRtaW4iOnRydWUsImZpcnN0bmFtZSI6IkFkbWluaXN0cmF0b3IiLCJsYXN0bmFtZSI6IkFkbWluaXN0cmF0b3IiLCJpYXQiOjE1MjvxMDkxMDMsImV4cCI6MTc4NjMwOTEwM30.eJOHpQm231P4YjVhA3fXKrRWNbgZwFA8zO4yKCilZy0\",\"message\":\"Successfully logged in!\"}");
        server.enqueue(mockedResponse);

        SprovaConnector conn = null;
        try {
            conn = new SprovaConnector(apiAddress, username, password);
            final RecordedRequest recordedRequest = server.takeRequest();
            assertNotNull("connection object should exist", conn);
            assertEquals("/authenticate", recordedRequest.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIsAuthenticated() {
        assertTrue(connector.isAuthenticated());
    }
}
