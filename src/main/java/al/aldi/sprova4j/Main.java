package al.aldi.sprova4j;

import al.aldi.sprova4j.models.*;
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

        SprovaConnector conn = null;
        try {
            conn = new SprovaConnector("http://localhost:8181/", "admin", "admin");

            apiClient = conn.getApiClient();
            project = apiClient.getProject("5ae5bda75b435f3d2b999c79");
            cycle = project.findOneCycle(new SprovaObjectFilter().add("title", "Release 6.3"));

            SprovaObjectFilter filter = new SprovaObjectFilter().add("jiraId", "SYSTEST-1");
            TestCase test1 = cycle.findOneTest(filter);
            exec = test1.startExecution();

            TestSet oneTestSet = cycle.findOneTestSet(new SprovaObjectFilter().add("title", "fe"));
            TestSetExecution execution = oneTestSet.createExecution();

            Execution execution1 = null;
            while((execution1 = execution.getNextPending()) != null){
                execution1.passTest();
            }


        } catch (Exception e) {
            System.err.println("Tried to connect to Sprova server but got " + e.getMessage());
        }



        logger.info("Exiting");
    }
}
