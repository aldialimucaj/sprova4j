package al.aldi.sprova4j.models.aux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestSetExecutionResponse extends MongoDbInsertResponse {
    public MongoDbInsertManyResponse executions;

    public static TestSetExecutionResponse toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, TestSetExecutionResponse.class);
    }
}
