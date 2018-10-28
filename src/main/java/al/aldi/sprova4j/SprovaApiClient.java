package al.aldi.sprova4j;

import al.aldi.sprova4j.exceptions.CycleException;
import al.aldi.sprova4j.exceptions.ExecutionException;
import al.aldi.sprova4j.exceptions.SprovaClientException;
import al.aldi.sprova4j.exceptions.TestCaseException;
import al.aldi.sprova4j.models.Cycle;
import al.aldi.sprova4j.models.Execution;
import al.aldi.sprova4j.models.Project;
import al.aldi.sprova4j.models.TestCase;
import al.aldi.sprova4j.models.aux.PutExecutionResponse;
import al.aldi.sprova4j.utils.AuthorizationInterceptor;
import al.aldi.sprova4j.utils.LoggingInterceptor;
import javax.validation.constraints.NotNull;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

import static al.aldi.sprova4j.utils.ApiUtils.*;

public class SprovaApiClient {
    public final String API_URL;
    public final String JWT_TOKEN;

    private OkHttpClient client = null;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    protected SprovaApiClient(@NotNull String apiUrl, @NotNull String jwtToken) throws SprovaClientException {
        if(apiUrl == null) {
            throw new SprovaClientException("API_URL cannot be null");
        }
        if(jwtToken == null) {
            throw new SprovaClientException("JWT_TOKEN cannot be null");
        }
        this.API_URL = apiUrl;
        this.JWT_TOKEN = jwtToken;

        this.client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new AuthorizationInterceptor(this.JWT_TOKEN))
                .build();
    }


    // ----------------------------------------------------------------------------
    // PROJECT
    // ----------------------------------------------------------------------------

    public Project getProject(String projectId) {
        try {
            Project project = Project.toObject(get(String.format("%s/%s", API_PROJECTS, projectId)));
            project.setClient(this);

            return project;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Cycle filterCycleByProjectId(String projectId, String jsonFiler) throws CycleException {
        Cycle result = null;

        try {
            result = Cycle.toObject(post(String.format("%s/%s/%s/findOne", API_PROJECTS, projectId, CYCLES), jsonFiler));
            if (result  == null) {
                throw new CycleException("Cycle not found => filter: " + jsonFiler);
            }
            result.setClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    // ----------------------------------------------------------------------------
    // CYCLE
    // ----------------------------------------------------------------------------
    public List<Cycle> getCyclesByProjectId(String projectId) {
        List<Cycle> result = null;

        try {
            result = Cycle.toObjects(get(String.format("%s/%s/%s", API_PROJECTS, projectId, CYCLES)));
            for (Cycle c : result) {
                c.setClient(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public TestCase filterTestCaseByCycleId(String cycleId, String jsonFiler) throws TestCaseException {
        TestCase result = null;

        try {
            result = TestCase.toObject(post(String.format("%s/%s/%s/findOne", API_CYCLES, cycleId, TESTCASES), jsonFiler));
            if (result  == null) {
                throw new TestCaseException("Test case not found => filter: " + jsonFiler);
            }
            result.setClient(this);
            result.cycleId = cycleId;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    // ----------------------------------------------------------------------------
    // TEST CASE
    // ----------------------------------------------------------------------------

    public List<TestCase> getTestCasesByProjectId(String projectId) {
        List<TestCase> result = null;

        try {
            result = TestCase.toObjects(get(String.format("%s/%s/%s", API_PROJECTS, projectId, TESTCASES)));
            for (TestCase t : result) {
                t.setClient(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TestCase> getTestCasesByCycleId(String projectId) {
        List<TestCase> result = null;

        try {
            result = TestCase.toObjects(get(String.format("%s/%s/%s", API_CYCLES, projectId, TESTCASES)));
            for (TestCase t : result) {
                t.setClient(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    // ----------------------------------------------------------------------------
    // EXECUTION
    // ----------------------------------------------------------------------------
    public Execution startExecution(Execution execution) {
        PutExecutionResponse response;
        String jsonString = execution.toJson();
        try {
            response = PutExecutionResponse.toObject(put(API_EXECUTIONS, jsonString));
            if (response.ok == 1) {
                execution._id = response._id;
            } else {
                throw new ExecutionException("Could not start execution " + execution._id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return execution;
    }

    public boolean passStep(Execution execution, int stepIndex) {
        return setStepStatus(execution, stepIndex, "pass");
    }

    public boolean failStep(Execution execution, int stepIndex) {
        return setStepStatus(execution, stepIndex, "fail");
    }

    private boolean setStepStatus(Execution execution, int stepIndex, String status) {
        boolean result = false;
        String jsonString = execution.toJson();
        try {
            PutExecutionResponse response = PutExecutionResponse.toObject(post(String.format("%s/%s/%s/%s/%s", API_EXECUTIONS, execution._id, STEPS, stepIndex, status), jsonString));
            result = response.ok == 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public Execution passExecution(Execution execution) {
        return setExecutionStatus(execution, "pass");
    }

    public Execution failExecution(Execution execution) {
        return setExecutionStatus(execution, "fail");
    }

    private Execution setExecutionStatus(Execution execution, String status) {
        PutExecutionResponse response;
        String jsonString = execution.toJson();
        try {
            response = PutExecutionResponse.toObject(post(String.format("%s/%s/%s", API_EXECUTIONS, execution._id, status), jsonString));
            if (response.ok == 1) {
                execution._id = response._id;
            } else {
                throw new ExecutionException("Could not pass execution " + execution._id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return execution;
    }


    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------

    public String get(String urlSuffix) throws IOException {
        final String url = API_URL + urlSuffix;
        Request.Builder builder = getBuilder(url);

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String post(final String urlSuffix, String json) throws IOException {
        final String url = API_URL + urlSuffix;
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = getBuilder(url).post(body);

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String put(final String urlSuffix, String json) throws IOException {
        final String url = API_URL + urlSuffix;
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = getBuilder(url).put(body);

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    private Request.Builder getBuilder(String url) {
        Request.Builder result = new Request.Builder().url(url);

        return result;
    }
}
