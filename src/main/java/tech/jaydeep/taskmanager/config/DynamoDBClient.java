package tech.jaydeep.taskmanager.config;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;


@Component("DynamoDBClient")
public class DynamoDBClient {
    private static DynamoDbClient ddbClient;
    public static DynamoDbClient getddbClient(){
        try {
            Region region = Region.US_EAST_1;
            ddbClient = DynamoDbClient.builder()
                    .region(region)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return ddbClient;
    }

}
