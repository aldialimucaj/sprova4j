package al.aldi.sprova4j;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectorTest {

    Connector connector = null;
    final String apiAddress = "http://localhost:8181/";
    final String username = "admin";
    final String password = "admin";

    @Before
    public void setUp() {
        try {
            connector = new Connector(apiAddress, username, password);
            assertNotNull("connection object should exist", connector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnect() {
        Connector conn = null;
        try {
            conn = new Connector(apiAddress, username, password);
            assertNotNull("connection object should exist", conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAuthenticate() {
        assertTrue(connector.authenticate());
    }
}
