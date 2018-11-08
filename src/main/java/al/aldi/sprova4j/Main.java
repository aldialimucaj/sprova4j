package al.aldi.sprova4j;

import al.aldi.sprova4j.exections.CycleException;
import al.aldi.sprova4j.exections.TestCaseException;
import al.aldi.sprova4j.models.*;
import al.aldi.sprova4j.models.aux.TestSetExecutionResponse;
import al.aldi.sprova4j.utils.SprovaObjectFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Project project;
    private static Cycle cycle;
    private static SprovaApiClient apiClient;
    private static Execution exec;

    public static void main(String[] args) {
        logger.info("Sprova4J running");
        logger.info("Implementation still missing");

        Connector conn = null;
        try {
            conn = new Connector("http://localhost:8181/", "admin", "admin");

            apiClient = conn.getApiClient();
            project = apiClient.getProject("5af582d1dccd6600137334a0");
            cycle = project.findOneCycle(new SprovaObjectFilter().add("title", "Release 6.3"));

            SprovaObjectFilter filter = new SprovaObjectFilter().add("jiraId", "SYSTEST-1");
            TestCase test1 = cycle.findOneTest(filter);
            exec = test1.startExecution();

            TestSet oneTestSet = cycle.findOneTestSet(new SprovaObjectFilter().add("title", "import"));
            TestSetExecutionResponse execution = oneTestSet.createExecution();

        } catch (Exception e) {
            System.err.println("Tried to connect to Sprova server but got " + e.getMessage());
        }



        logger.info("Exiting");
    }
}
