package tech.jaydeep.taskmanager.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.HashMap;
import java.util.Map;


@DynamoDbBean
public class TaskTable {
    private String id;
    private String tableName;
    private String tableDescription;
    private final Map<String,Task> taskList;

    public TaskTable() {
        this.taskList = new HashMap<>();
    }


    public TaskTable(String tableName, String tableDescription) {
        this.tableName = tableName;
        this.tableDescription = tableDescription;
        this.taskList = new HashMap<>();
    }


    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableDescription() {
        return tableDescription;
    }
    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription;
    }
    public Map<String, Task> getTaskList() {
        return taskList;
    }
    public void addTask(Map<String, Task> tasks) {
        taskList.putAll(tasks);
    }
    public void removeTask(String id) {
        taskList.remove(id);
    }
}
