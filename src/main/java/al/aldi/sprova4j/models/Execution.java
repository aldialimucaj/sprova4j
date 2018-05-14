package al.aldi.sprova4j.models;

import al.aldi.sprova4j.SprovaApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Execution extends SprovaObject {
    public String _id;
    public String title;
    public String description;
    public String testCaseId;
    public String testVersion;
    public String cycleId;
    public String parentId;
    public String status;
    public List<TestStep> testSteps;
    public Date createdAt;
    public Date updatedAt;

    public void passTest(){
        client.passExecution(this);
    }

    public void failTest() {
        client.failExecution(this);
    }

    public boolean passStep(int stepIndex){
        return client.passStep(this, stepIndex);
    }

    public boolean failStep(int stepIndex){
        return client.failStep(this, stepIndex);
    }


    public static Execution toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, Execution.class);
    }

    public static List<Execution> toObjects(String json) {
        Type listType = new TypeToken<ArrayList<Execution>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }

}
