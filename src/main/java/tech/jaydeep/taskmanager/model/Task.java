package tech.jaydeep.taskmanager.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;

@DynamoDbBean
public class Task {

    private String taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public Task() {
    }
    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = null;
    }


    @DynamoDbPartitionKey
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String id) {
        this.taskId = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
    void setTaskStatus(String status) {
        this.taskStatus = TaskStatus.valueOf(status);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
