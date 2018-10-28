package al.aldi.sprova4j.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSetExecution extends SprovaObject{

    public static final String TYPE_MANUAL = "MANUAL";
    public static final String TYPE_AUTOMATED = "AUTOMATED";
    public static final String TYPE_SEMI_AUTOMATED = "SEMI_AUTOMATED";

    public String _id;
    public String title;
    public String description;
    public String status;
    public String executionType;

    public String testCaseId;
    public String cycleId;
    public String parentId;

    public Date createdAt;
    public Date updatedAt;
    public Date startedAt;
    public Date finishedAt;


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
