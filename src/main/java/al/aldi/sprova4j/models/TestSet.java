package al.aldi.sprova4j.models;

import al.aldi.sprova4j.exections.TestCaseException;
import al.aldi.sprova4j.exections.TestSetException;
import al.aldi.sprova4j.models.aux.TestSetExecutionResponse;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestSet extends SprovaObject {
    public String _id;
    public String title;
    public String description;
    public String projectId;
    public String cycleId;
    public String status;

    public TestSet findOneTest(SprovaObjectFilter filter) throws TestCaseException {
        TestSet result = client.filterTestSetByCycleId(_id, filter.toJson());

        return result;
    }

    public List<TestCase> getTestCases() {
        List<TestCase> result = client.getTestCasesByCycleId(_id);
        for (TestCase testCase : result) {
            testCase.cycleId = cycleId;
        }

        return result;
    }

    public TestSetExecution createExecution() throws TestSetException {
        TestSetExecution result = null;
        TestSetExecutionResponse response = client.createTestExecution(TestSetExecution.fromTestSet(this));

        if (response.isSuccessful()) {
            result = client.getTestSetExecutionById(response._id);
        } else {
            throw new TestSetException("Could not create execution");
        }

        return result;
    }

    public static TestSet toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, TestSet.class);
    }

    public static List<TestSet> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<TestSet>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }

}
