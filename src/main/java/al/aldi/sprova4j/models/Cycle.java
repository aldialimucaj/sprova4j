package al.aldi.sprova4j.models;

import al.aldi.sprova4j.exceptions.TestCaseException;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Cycle extends SprovaObject {
    public String _id;
    public String title;
    public String description;
    public String projectId;
    public String status;

    public TestCase findOneTest(SprovaObjectFilter filter) throws TestCaseException {
        TestCase result = client.filterTestCaseByCycleId(_id, filter.toJson());

        return result;
    }

    public List<TestCase> getTestCases() {
        List<TestCase> result = client.getTestCasesByCycleId(_id);
        for (TestCase testCase : result) {
            testCase.cycleId = _id;
        }

        return result;
    }

    public static Cycle toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, Cycle.class);
    }

    public static List<Cycle> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<Cycle>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }

}
