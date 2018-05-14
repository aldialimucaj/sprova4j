package al.aldi.sprova4j.utils;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.StringWriter;

public class SprovaObjectFilter {

    public JsonObjectBuilder jsonObject;

    public SprovaObjectFilter() {
       this.jsonObject = Json.createObjectBuilder();
    }

    public SprovaObjectFilter add(String key, String value){
        jsonObject.add(key, value);

        return this;
    }

    public String toJson() {
        final StringWriter stringWriter = new StringWriter();
        final JsonWriter jsonWriter = Json.createWriter(stringWriter);
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder root = factory.createObjectBuilder();
        root.add("filter", jsonObject);
        jsonWriter.writeObject(root.build());

        return stringWriter.toString();
    }

}
