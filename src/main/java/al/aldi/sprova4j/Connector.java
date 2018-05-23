package al.aldi.sprova4j;

import al.aldi.sprova4j.exections.SprovaClientException;
import al.aldi.sprova4j.models.aux.AuthenticationRequest;
import al.aldi.sprova4j.models.aux.AuthenticationResponse;
import al.aldi.sprova4j.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Connector {
    private static final Logger logger = LoggerFactory.getLogger(Connector.class);

    private final String API_ADDRESS;
    private final String JWT_KEY;
    private final String username;
    private final String password;

    private SprovaApiClient apiClient;


    public Connector(String apiAddress, String jwtKey) throws Exception {
        this.API_ADDRESS = ApiUtils.sanitizeUrl(apiAddress);
        this.JWT_KEY = jwtKey;
        this.username = null;
        this.password = null;
    }

    public Connector(String apiAddress, String username, String password) throws Exception {
        this.API_ADDRESS = ApiUtils.sanitizeUrl(apiAddress);
        this.username = username;
        this.password = password;

        // authenticate
        this.JWT_KEY = this.authenticate(username, password);
    }

    /**
     * Make sure user is authenticated via JWT token.
     *
     * @return authentication success
     */
    public boolean authenticate() {
        return true;
    }

    /**
     * Authenticate by user and password in order to get JWT key for further requests.
     *
     * @param username
     * @param password
     * @return JWT token
     */
    protected String authenticate(final String username, final String password) throws IOException {
        String result = null;

        String url = this.API_ADDRESS + ApiUtils.AUTHENTICATE;
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        String response = ApiUtils.post(url, request.toJson());
        AuthenticationResponse auth = AuthenticationResponse.toObject(response);
        result = auth.token;

        return result;
    }

    /**
     * Get or initialize and get client.
     *
     * @return sprova api client
     */
    public SprovaApiClient getApiClient() {
        if (this.apiClient == null) {
            try {
                this.apiClient = new SprovaApiClient(this.API_ADDRESS, this.JWT_KEY);
            } catch (SprovaClientException e) {
                e.printStackTrace();
            }
        }

        return this.apiClient;
    }
}
