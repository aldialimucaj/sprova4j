package al.aldi.sprova4j;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectorTest {
    @Test
    public void testConnect() {
        Connector conn = null;
        try {
            conn = new Connector("http://localhost:8181/", "admin", "admin");
            assertNotNull("connection object should exist",conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
