package al.aldi.sprova4j.models;

import al.aldi.sprova4j.exceptions.CycleException;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Project extends SprovaObject {
    public String _id;
    public String title;
    public String description;


    public Cycle findOneCycle(SprovaObjectFilter filter) throws CycleException {
        Cycle result = client.filterCycleByProjectId(_id, filter.toJson());

        return result;
    }

    public static Project toObject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, Project.class);
    }

    public List<Cycle> getCycles() {
        return client.getCyclesByProjectId(_id);
    }

    public List<TestCase> getTestCases() {
        return client.getTestCasesByProjectId(_id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
