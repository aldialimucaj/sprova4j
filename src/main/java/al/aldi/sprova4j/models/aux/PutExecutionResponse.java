package al.aldi.sprova4j.models.aux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PutExecutionResponse {
    public int n;
    public int ok;
    public String _id;

    public static PutExecutionResponse toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, PutExecutionResponse.class);
    }
}
