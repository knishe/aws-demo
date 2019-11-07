package com.aegle.aws;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

import java.net.URI;

@SpringBootApplication
public class AwsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsDemoApplication.class, args);
    }

    @Bean
    DynamoDbClient dynamoDBClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:4569"))
                .build();
    }

    @Bean
    ApplicationRunner runner(DynamoDbClient dynamoDB) {
        return args -> {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName("book")
                    .keySchema(KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();

            dynamoDB.createTable(request);

            dynamoDB.listTables().tableNames()
                    .forEach(System.out::println);
        };
    }
}
