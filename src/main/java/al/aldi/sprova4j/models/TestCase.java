package al.aldi.sprova4j.models;

import al.aldi.sprova4j.SprovaApiClient;
import al.aldi.sprova4j.exections.TestCaseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static al.aldi.sprova4j.models.TestStep.PENDING;

public class TestCase extends SprovaObject {
    public String _id;
    public String title;
    public String description;
    public String version;
    public String projectId;
    public String cycleId;
    public String parentId;
    public String status;
    public List<TestStep> testSteps;

    public Execution startExecution() throws TestCaseException {
        if (cycleId == null) throw new TestCaseException("Cycle ID cannot be null");

        Execution execution = getExecution();
        client.startExecution(execution);

        return execution;
    }

    private Execution getExecution() {
        Execution result = new Execution();
        result.setClient(client);
        result.testCaseId = _id;
        result.cycleId = cycleId;
        result.title = title;
        result.description = description;
        result.testSteps = testSteps;
        result.status = PENDING;

        for (int i = 0; i < result.testSteps.size(); i++) {
            result.testSteps.get(i).execution = result;
            result.testSteps.get(i).index = i;
        }

        return result;
    }

    public void setClient(SprovaApiClient client) {
        this.client = client;
    }

    public static TestCase toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, TestCase.class);
    }

    public static List<TestCase> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<TestCase>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }
}
