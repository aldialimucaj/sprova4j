package al.aldi.sprova4j.models;

import al.aldi.sprova4j.exections.TestCaseException;
import al.aldi.sprova4j.models.enums.TestSetExecutionStatus;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TestSetExecution extends SprovaObject {
    public String _id;
    public String title;
    public String description;
    public String status;

    public String projectId;
    public String cycleId;
    public String testSetId;

    public Instant createdAt;
    public Instant updatedAt;
    public Instant startedAt;
    public Instant finishedAt;

    public TestSetExecution() {
    }

    public TestSetExecution(String title, String description, String testSetId, String cycleId) {
        this.title = title;
        this.description = description;
        this.testSetId = testSetId;
        this.cycleId = cycleId;
        this.status = TestSetExecutionStatus.PLANNED;
    }

    public TestCase findOneTest(SprovaObjectFilter filter) throws TestCaseException {
        TestCase result = client.filterTestCaseByCycleId(_id, filter.toJson());

        return result;
    }

    public List<TestCase> getExecutions() {
        List<TestCase> result = client.getTestCasesByCycleId(_id);
        for (TestCase testCase : result) {
            testCase.cycleId = cycleId;
        }

        return result;
    }

    public static TestSetExecution fromTestSet(TestSet testSet) {
        return new TestSetExecution(testSet.title, testSet.description, testSet._id, testSet.cycleId);
    }

    public static TestSetExecution toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, TestSetExecution.class);
    }


    public static List<TestSetExecution> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<TestSetExecution>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }

}
