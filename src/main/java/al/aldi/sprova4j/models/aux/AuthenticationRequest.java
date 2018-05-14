package al.aldi.sprova4j.models.aux;

import al.aldi.sprova4j.models.SprovaObject;

public class AuthenticationRequest extends SprovaObject {
    public String username;
    public String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
