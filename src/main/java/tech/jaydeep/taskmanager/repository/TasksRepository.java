package tech.jaydeep.taskmanager.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tech.jaydeep.taskmanager.repository.TaskTableRepository.transformedAttributives;


@Repository
public class TasksRepository {

    public TasksRepository() {
    }

    public void insertTask(DynamoDbClient ddb,
                                                   String tableName,
                                                   String key,
                                                   String keyVal,
                                                   String name,
                                                   Map<String, String> taskObj) {
        try {

            String id = UUID.randomUUID().toString();
            String taskName = taskObj.get("taskName");
            String taskDescription = taskObj.get("taskDescription");
            String taskStatus = taskObj.get("taskStatus");


            HashMap<String, AttributeValue> itemKey = new HashMap<>();
            itemKey.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            Map<String, AttributeValue> newTask = new HashMap<>();
            newTask.put("taskName", AttributeValue.builder().s(taskName).build());
            newTask.put("taskDescription", AttributeValue.builder().s(taskDescription).build());
            newTask.put("taskStatus", AttributeValue.builder().s(taskStatus).build());




            Map<String, String> attributeNames = new HashMap<>();
            attributeNames.put("#taskList", name);
            attributeNames.put("#newTaskId", id);

            Map<String, AttributeValue> attributeValues = new HashMap<>();
            attributeValues.put(":newTask", AttributeValue.builder().m(newTask).build());

            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName(tableName)
                    .key(itemKey)
                    .expressionAttributeNames(attributeNames)
                    .expressionAttributeValues(attributeValues)
                    .updateExpression("SET #taskList.#newTaskId = :newTask")
                    .build();

            ddb.updateItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public Map<String, Object> getTaskById(DynamoDbClient ddb, String tableName, String key, String keyVal, String taskId, String attrName) {
        try {
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            GetItemRequest request = GetItemRequest.builder()
                    .key(keyToGet)
                    .projectionExpression("#attrName.#taskId")
                    .expressionAttributeNames(new HashMap<>() {{
                        put("#attrName", attrName);
                        put("#taskId", taskId);
                    }})
                    .tableName(tableName)
                    .build();

            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();

            if (returnedItem != null) {
                return transformedAttributives(returnedItem);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public void updateTaskById(DynamoDbClient ddb, String tableName, String key, String keyVal, String taskId,String name, Map<String, String> taskObj) {
        try {
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            GetItemRequest request = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();

            Map<String, AttributeValue> response = ddb.getItem(request).item();
            Map<String, AttributeValue> taskmap = response.get(name).m().get(taskId).m();
            Map<String, AttributeValue> updatedValues = new HashMap<>();
            taskmap.forEach((k, v) -> {
                if (taskObj.containsKey(k)) {
                    updatedValues.put(k, AttributeValue.builder().s(taskObj.get(k)).build());
                }else {
                    updatedValues.put(k, v);
                }
            });

            Map<String, String> attributeNames = new HashMap<>();
            attributeNames.put("#taskList", name);
            attributeNames.put("#newTaskId", taskId);

            Map<String, AttributeValue> attributeValues = new HashMap<>();
            attributeValues.put(":newTask", AttributeValue.builder().m(updatedValues).build());

            UpdateItemRequest updateRequest = UpdateItemRequest.builder()
                    .tableName(tableName)
                    .key(keyToGet)
                    .expressionAttributeNames(attributeNames)
                    .expressionAttributeValues(attributeValues)
                    .updateExpression("SET #taskList.#newTaskId = :newTask")
                    .build();

            ddb.updateItem(updateRequest);


        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    public void deleteTaskById(DynamoDbClient ddb, String tableName, String key, String keyVal, String name, String taskId) {
        try {
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());


            Map<String, String> attributeNames = new HashMap<>();
            attributeNames.put("#taskList", name);
            attributeNames.put("#taskId", taskId);

            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName(tableName)
                    .key(keyToGet)
                    .expressionAttributeNames(attributeNames)
                    .updateExpression("REMOVE #taskList.#taskId")
                    .build();
            ddb.updateItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
