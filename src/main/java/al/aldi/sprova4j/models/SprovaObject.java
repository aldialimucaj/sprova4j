package al.aldi.sprova4j.models;

import al.aldi.sprova4j.SprovaApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class SprovaObject {
    public transient GsonBuilder builder = new GsonBuilder();
    public transient Gson gson = builder.create();

    protected transient SprovaApiClient client;

    public void setClient(SprovaApiClient client) {
        this.client = client;
    }

    public String toJson() {
        return gson.toJson(this);
    }

}
