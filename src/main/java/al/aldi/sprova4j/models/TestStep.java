package al.aldi.sprova4j.models;

import al.aldi.sprova4j.SprovaApiClient;
import al.aldi.sprova4j.exections.TestCaseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestStep extends SprovaObject {
    public String _id;
    public String action;
    public String payload;
    public String expected;
    public String comment;
    public String updatedAt;
    public String status;
    public String artifacts;
    public transient Execution execution;
    public transient int index;

    public static final String PENDING = "PENDING";

    public TestStep() {
        this.status = PENDING;
    }

    public boolean pass(){
        return execution.passStep(index);
    }

    public static TestStep toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, TestStep.class);
    }

    public static List<TestStep> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<TestStep>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }

}
