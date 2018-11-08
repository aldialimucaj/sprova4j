package al.aldi.sprova4j.utils;

import okhttp3.*;

import java.io.IOException;

import static al.aldi.sprova4j.SprovaApiClient.JSON;

public class ApiUtils {

    public static final String API = "/api";
    public static final String AUTHENTICATE = "/authenticate";

    public static final String PROJECTS = "projects";
    public static final String CYCLES = "cycles";
    public static final String TESTSETS = "testsets";
    public static final String TESTCASES = "testcases";
    public static final String TESTSET_EXECUTIONS = "testset-executions";
    public static final String EXECUTIONS = "executions";
    public static final String STEPS = "steps";

    public static final String FILTER = "filter";
    public static final String FIND_ONE = "findOne";

    public static final String API_PROJECTS = API + "/" + PROJECTS;
    public static final String API_CYCLES = API + "/" + CYCLES;
    public static final String API_TESTSETS = API + "/" + TESTSETS;
    public static final String API_TESTCASES = API + "/" + TESTCASES;
    public static final String API_EXECUTIONS = API + "/" + EXECUTIONS;
    public static final String API_TESTSET_EXECUTIONS = API + "/" + TESTSET_EXECUTIONS;



    public static String sanitizeUrl(final String url) throws Exception {
        String result = url;
        if (url.endsWith("/")) {
            result = result.substring(0, result.length() - 1);
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            throw new Exception("Url is missing protocol. HTTPS is suggested.");
        }
        return result;
    }

    public static String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder().url(url).post(body);

        Request request = builder.build();
        Response response = new OkHttpClient().newCall(request).execute();

        return response.body().string();
    }
}
