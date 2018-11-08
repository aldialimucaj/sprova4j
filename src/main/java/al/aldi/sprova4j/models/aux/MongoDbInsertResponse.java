package al.aldi.sprova4j.models.aux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MongoDbInsertResponse {
    public int n;
    public int ok;
    public String _id;

    public boolean isSuccessful() {
        return n == 1 && ok == 1 && _id != null && !_id.isEmpty();
    }

    public static MongoDbInsertResponse toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, MongoDbInsertResponse.class);
    }
}
