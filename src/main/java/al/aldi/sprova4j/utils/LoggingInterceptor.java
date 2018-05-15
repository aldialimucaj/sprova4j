package al.aldi.sprova4j.utils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String body = null;

        // not all requests have body.
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            body = buffer.readUtf8();
        } catch (final Exception e) {
            body = "{}";
        }

        long t1 = System.nanoTime();
        logger.debug(String.format("REQ [%s] %s on %n%s %s", request.method(),  request.url(), request.headers(), body));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        logger.debug(String.format("RES %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}