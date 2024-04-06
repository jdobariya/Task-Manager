package tech.jaydeep.taskmanager.repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import java.util.*;



@Repository
public class TaskTableRepository {


    public TaskTableRepository() {
    }
    public void insertTaskTable(DynamoDbClient ddb, String tableName, Map<String, String> tableObj) {
        try {

            String id = UUID.randomUUID().toString();
            String taskTableName = tableObj.get("tableName");
            String tableDescription = tableObj.get("tableDescription");

            HashMap<String, AttributeValue> itemValues = new HashMap<>();
            itemValues.put("id", AttributeValue.builder().s(id).build());
            itemValues.put("tableName", AttributeValue.builder().s(taskTableName).build());
            itemValues.put("tableDescription", AttributeValue.builder().s(tableDescription).build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(tableName)
                    .item(itemValues)
                    .build();
            ddb.putItem(request);
        } catch ( DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    public void deleteTaskTableItem(DynamoDbClient ddb, String tableName, String keyVal) {
        try {
            String key = "id";
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            DeleteItemRequest deleteRequest = DeleteItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();

            ddb.deleteItem(deleteRequest);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Map<String, Object> transformedAttributives(Map<String, AttributeValue> response){
        Map<String, Object> output = new HashMap<>();
        response.forEach((itemKey, value) -> {
            if(value.hasM()) {
                output.put(itemKey, transformedAttributives(value.m()));
            }else if(value.hasL()){
                value.l().forEach(item -> {
                    if(item.hasM()){
                        output.put(itemKey, transformedAttributives(item.m()));
                    }else {
                        output.put(itemKey, item.s());
                    }
                });
            }else {
                output.put(itemKey, value.s());
            }
        });
        return output;
    }

    public Map<String, Object> getTaskTableItemById(DynamoDbClient ddb, String tableName, String keyVal) {
        try {

            String key = "id";
            HashMap<String, AttributeValue> keyToGet = new HashMap<>();
            keyToGet.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            GetItemRequest request = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();

            return transformedAttributives(ddb.getItem(request).item());

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public List<Map<String, Object>> getTaskTableItems(DynamoDbClient ddb, String tableName) {

        try {
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .projectionExpression("id, tableName, tableDescription")
                    .build();

            ScanResponse response = ddb.scan(scanRequest);

            List<Map<String, Object>> result = new ArrayList<>();
            response.items().forEach(item -> result.add(transformedAttributives(item)));

            return result;

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public void updateTaskTableItem(DynamoDbClient ddb,
                                    String tableName,
                                    String key,
                                    String keyVal,
                                    Map<String, String> tableObj) {
        try {

            HashMap<String, AttributeValue> itemKey = new HashMap<>();
            itemKey.put(key, AttributeValue.builder()
                    .s(keyVal)
                    .build());

            HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();

            tableObj.forEach((k, v) -> updatedValues.put(k, AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(v).build())
                    .action(AttributeAction.PUT)
                    .build()));
            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName(tableName)
                    .key(itemKey)
                    .attributeUpdates(updatedValues)
                    .build();

            ddb.updateItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}