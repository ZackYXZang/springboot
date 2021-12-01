package com.example.kafka_mq.interceptors;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

//消费者拦截器
public class KafkaConsumerInterceptors {

  public static void main(String[] args) {
    //创建KafkaConsumer01
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

    //1.这种是利用了kafka的自分区管理特性，达到负载均衡
    //消费者的group信息
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
    //订阅相关的topics
    consumer.subscribe(Pattern.compile("^topic.*"));


    //遍历消息队列
    while (true) {
      ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));//间隔1秒获取一次
      if (!consumerRecords.isEmpty()) {//从队列中取到了数据
        Iterator<ConsumerRecord<String, String>> recordIterator = consumerRecords.iterator();
        while (recordIterator.hasNext()) {
          //获取到一个消费消息
          ConsumerRecord<String, String> record = recordIterator.next();

          String topic = record.topic();//消息属于的topic
          int partition = record.partition();//消息属于的分区
          long offset = record.offset();//消息的偏移量
          String key = record.key();//消息的key
          String value = record.value();//消息的value
          long timestamp = record.timestamp();//消息的时间戳

          System.out.println(topic + "\t" + partition + "\t" + offset + key + "\t" + value + "\t" + timestamp);
        }
      }
    }

    //关闭消费者
//    consumer.close();
  }
}
