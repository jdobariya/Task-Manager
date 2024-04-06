package tech.jaydeep.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import tech.jaydeep.taskmanager.config.DynamoDBClient;
import tech.jaydeep.taskmanager.repository.TaskTableRepository;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks-tables")
public class TaskTablesController {

    private final TaskTableRepository taskTables;
    private static final String TABLE_NAME = "TasksTable";
    private DynamoDbClient ddbClient;

    public TaskTablesController(TaskTableRepository taskTable) {
        this.taskTables = taskTable;
    }
    @GetMapping("")
    public List<Map<String, Object>> findAll(){
        ddbClient = DynamoDBClient.getddbClient();
        List<Map<String, Object>> taskTable = taskTables.getTaskTableItems(ddbClient, TABLE_NAME);
        if (taskTable == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content found!");
        ddbClient.close();
        return taskTable;
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable String id){
        ddbClient = DynamoDBClient.getddbClient();
        Map<String, Object> taskTable = taskTables.getTaskTableItemById(ddbClient, TABLE_NAME, id);
        if (taskTable == null || taskTable.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Table not found!");
        ddbClient.close();
        return taskTable;
    }

    @PostMapping("/create")
    public void insertTaskTable(@RequestBody Map<String, String> tableObj){
        ddbClient = DynamoDBClient.getddbClient();
        taskTables.insertTaskTable(ddbClient, TABLE_NAME, tableObj);
        ddbClient.close();
    }

    @DeleteMapping("/{id}/delete")
    public void deleteTaskTable(@PathVariable String id){
        ddbClient = DynamoDBClient.getddbClient();
        taskTables.deleteTaskTableItem(ddbClient, TABLE_NAME, id);
        ddbClient.close();
    }

    @PutMapping("/{id}/update")
    public void updateTaskTable(@PathVariable String id, @RequestBody Map<String, String> tableObj){
        ddbClient = DynamoDBClient.getddbClient();
        taskTables.updateTaskTableItem(ddbClient, TABLE_NAME, "id", id, tableObj);
        ddbClient.close();
    }

}

