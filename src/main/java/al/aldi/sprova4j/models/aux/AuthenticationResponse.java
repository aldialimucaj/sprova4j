package al.aldi.sprova4j.models.aux;

import al.aldi.sprova4j.models.SprovaObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthenticationResponse extends SprovaObject {
    public String message;
    public String token;

    public static AuthenticationResponse toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, AuthenticationResponse.class);
    }
}
