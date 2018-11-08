package al.aldi.sprova4j.models.aux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class MongoDbInsertManyResponse {
    public int insertedCount;
    public Nok result;
    public HashMap<Integer, String> insertedIds;

    public static MongoDbInsertManyResponse toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, MongoDbInsertManyResponse.class);
    }

    static class Nok {
        public int n;
        public int ok;
    }
}
