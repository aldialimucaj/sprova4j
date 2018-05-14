package al.aldi.sprova4j.utils;

import javax.validation.constraints.NotNull;;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    private final String jwtToken;
    private final String bearer;

    public AuthorizationInterceptor(@NotNull final String jwtToken) {
        this.jwtToken = jwtToken;
        this.bearer = "Bearer " + jwtToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().addHeader("Authorization", bearer).build();
        Response response = chain.proceed(request);

        return response;
    }
}