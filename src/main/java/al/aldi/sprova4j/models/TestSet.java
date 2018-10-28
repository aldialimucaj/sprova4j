package al.aldi.sprova4j.models;

import al.aldi.sprova4j.exceptions.TestCaseException;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestSet extends SprovaObject{
    public String _id;
    public String title;
    public String description;
    public String status;

    public String testCaseId;
    public String cycleId;
    public String projectId;

    public List<String> testCaseIDs;

    public List<TestCase> getTestCases() {
        List<TestCase> result = client.getTestCasesByCycleId(_id);
        for (TestCase testCase : result) {
            testCase.cycleId = _id;
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
