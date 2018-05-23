package al.aldi.sprova4j.utils;

import org.junit.Test;
import javax.json.JsonObjectBuilder;

import static org.junit.Assert.assertEquals;

public class SprovaObjectFilterTest {

    @Test
    public void add() {
        SprovaObjectFilter filter = new SprovaObjectFilter();
        filter.add("one", "1");
        assertEquals("json object should contain key","1",filter.jsonObject.build().getString("one"));
    }

    @Test
    public void toJson() {
        SprovaObjectFilter filter = new SprovaObjectFilter();
        filter.add("one", "1");
        assertEquals("json object should contain key","{\"filter\":{\"one\":\"1\"}}",filter.toJson());
        filter.add("one", "1").add("two", "2");
        assertEquals("json object should contain key","{\"filter\":{\"one\":\"1\",\"two\":\"2\"}}",filter.toJson());
    }
}