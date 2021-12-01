package com.example.kafka_mq.dml;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import lombok.val;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;

public class KafkaTopicDML {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    //1.创建KafkaAdminClient
    Properties properties = new Properties();
    properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    KafkaAdminClient adminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);


    //2.创建topic信息
    //注意：这里创建topic是异步的
    adminClient.createTopics(Arrays.asList(new NewTopic("topic01", 3, (short) 3)));
    //这样就是同步的了
    CreateTopicsResult topic02 = adminClient
        .createTopics(Arrays.asList(new NewTopic("topic02", 3, (short) 3)));
    topic02.all().get();

    //3.查看topic列表
    ListTopicsResult listTopicsResult = adminClient.listTopics();
    Set<String> names = listTopicsResult.names().get();
    for (String name : names) {
      System.out.println(name);
    }


    //删除topic
    //注意：这里删除topic是异步的
    adminClient.deleteTopics(Arrays.asList("topic01", "topic02"));
    //这样就是同步的了
    DeleteTopicsResult deleteTopicsResult = adminClient
        .deleteTopics(Arrays.asList("topic01", "topic02"));
    deleteTopicsResult.all().get();


    //查看topic详细信息
    DescribeTopicsResult describeTopics = adminClient.describeTopics(Arrays.asList("topic01"));
    Map<String, TopicDescription> topicMap = describeTopics.all().get();
    for (Entry<String, TopicDescription> entry : topicMap.entrySet()) {
      System.out.println(entry.getKey() + "\t" + entry.getValue());
    }


    //关闭KafkaAdminClient
    adminClient.close();

  }
}
