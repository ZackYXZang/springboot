package com.example.kafka_mq.transaction;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

//生产者&消费者事务
public class KafkaProducerTransactionProducerAndConsumer {

  public static void main(String[] args) {


    KafkaProducer<String, String> producer = buildKafkaProducer();
    KafkaConsumer<String, String> consumer = buildKafkaConsumer("g1");


    //1.初始化事务
    producer.initTransactions();
    //订阅了生产者发送过来的topic01
    consumer.subscribe(Arrays.asList("topic01"));

    while (true) {
      //消费者开始消费
      ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
      if (!consumerRecords.isEmpty()) {
        HashMap<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        Iterator<ConsumerRecord<String, String>> recordIterator = consumerRecords.iterator();
        //开启事务
        producer.beginTransaction();

        try {
          //进行业务处理
          while (recordIterator.hasNext()) {
            //获取消息
            ConsumerRecord<String, String> record = recordIterator.next();
            //存储元数据
            offsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic02", record.key(),record.value() + "发送给下个消费者");
            producer.send(producerRecord);
          }
          //事务提交
          producer.sendOffsetsToTransaction(offsets, "g1");//提交消费者的偏移量
          producer.commitTransaction();
        } catch (Exception e) {
          System.err.println("错误" + e.getMessage());
          //终止事务
          producer.abortTransaction();
        } finally {
          producer.close();
        }
      }
    }
  }

  public static  KafkaProducer<String, String> buildKafkaProducer() {
    //1.创建KafkaProducer
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

    //配置事务id，必须是唯一的
    properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id" + UUID.randomUUID().toString());

    //配置kafka批处理大小
    properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);
    //如果等待5秒，batch中的数据大小不足上面规定的1024，也发送
    properties.put(ProducerConfig.LINGER_MS_CONFIG, 5);

    //配置kafka的重试机制和幂等性
    properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    properties.put(ProducerConfig.ACKS_CONFIG, "all");
    properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,20000);

    return new KafkaProducer<String, String>(properties);
  }

  public static KafkaConsumer<String, String> buildKafkaConsumer(String groupId) {
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

    //设置消费者的消费事务的隔离级别read_committed，默认是read_uncommitted
    properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

    //1.这种是利用了kafka的自分区管理特性，达到负载均衡
    //消费者的group信息
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    //必须关闭消费者端的offset自动提交，因为需要等生产者角色完成事物之后，才能一起提交
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

    return new KafkaConsumer<>(properties);
  }
}
