package al.aldi.sprova4j.models;

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

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_WORKING = "WORKING";
    public static final String STATUS_SUCCESSFUL = "SUCCESSFUL";
    public static final String STATUS_FAILED = "FAILED";

    public TestStep() {
        this.status = STATUS_PENDING;
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
