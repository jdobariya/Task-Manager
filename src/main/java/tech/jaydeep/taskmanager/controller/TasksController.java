package tech.jaydeep.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import tech.jaydeep.taskmanager.config.DynamoDBClient;
import tech.jaydeep.taskmanager.repository.TasksRepository;

import java.util.Map;

@RestController
@RequestMapping("/tasks-tables")
public class TasksController {

    private final TasksRepository taskTable;
    private static final String TABLE_NAME = "TasksTable";
    private DynamoDbClient ddbClient;


    public TasksController(TasksRepository taskTable) {
        this.taskTable = taskTable;
    }

    @PostMapping("/{keyVal}/create")
    public void insertTask(@RequestBody Map<String, String> taskObj, @PathVariable String keyVal){
        ddbClient = DynamoDBClient.getddbClient();
        taskTable.insertTask(ddbClient,
                TABLE_NAME,
                "id",
                keyVal,
                "taskList",
                taskObj);
        ddbClient.close();
    }

    @GetMapping("/{keyVal}/{taskId}")
    public Map<String, Object> findById(@PathVariable String keyVal, @PathVariable String taskId){
        ddbClient = DynamoDBClient.getddbClient();
        Map<String, Object> task = taskTable.getTaskById(ddbClient, TABLE_NAME, "id", keyVal, taskId, "taskList");
        if (task == null || task.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!");
        ddbClient.close();
        return task;
    }

    @PutMapping("/{keyVal}/{taskId}/update")
    public void updateTask(@RequestBody Map<String, String> taskObj, @PathVariable String keyVal, @PathVariable String taskId){
        ddbClient = DynamoDBClient.getddbClient();
        taskTable.updateTaskById(ddbClient, TABLE_NAME, "id", keyVal, taskId, "taskList", taskObj);
        ddbClient.close();
    }

    @DeleteMapping("/{keyVal}/{taskId}/delete")
    public void deleteTask(@PathVariable String keyVal, @PathVariable String taskId){
        ddbClient = DynamoDBClient.getddbClient();
        taskTable.deleteTaskById(ddbClient, TABLE_NAME, "id", keyVal, "taskList", taskId);
        ddbClient.close();
    }

}
