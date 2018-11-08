# sprova4j
Java client for Sprova Test Framework

## Getting started

Start a connection to the sprova server
```javascript
Connector conn = new Connector("http://localhost:8181/", "admin", "admin");

```


When the connection in set up and your user authorized then get the client as entry point

```javascript
SprovaApiClient apiClient = conn.getApiClient();

```

With the client you can get elements by IDs or filter with **SprovaObjectFilter**
```javascript
Project project = apiClient.getProject("5ae5bda75b435f3d2b999c79");

Cycle cycle = project.findOneCycle(new SprovaObjectFilter().add("title", "Release 1.0"));
```

You can start execution directly on cycle test cases
```javascript
SprovaObjectFilter filter = new SprovaObjectFilter().add("jiraId", "SYSTEST-1");
TestCase test1 = cycle.findOneTest(filter);
exec = test1.startExecution();
exec.passTest()
```

Or you can find your test set and plan execution only for that set
```javascript
TestSet oneTestSet = cycle.findOneTestSet(new SprovaObjectFilter().add("title", "functional"));
TestSetExecution setExecution = oneTestSet.createExecution();

Execution nextTestExecution = execution.getNextPending()
nextTestExecution.passTest();

```