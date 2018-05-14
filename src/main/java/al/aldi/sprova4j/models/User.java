package al.aldi.sprova4j.models;

public class User extends SprovaObject {
    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public String email;
    public boolean admin;

    User toObject(String json) {
        return gson.fromJson(json, User.class);
    }
}
