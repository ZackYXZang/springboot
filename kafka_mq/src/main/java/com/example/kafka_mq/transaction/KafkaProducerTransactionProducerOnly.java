package com.example.kafka_mq.transaction;

import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

//生产者事务
public class KafkaProducerTransactionProducerOnly {

  public static void main(String[] args) {


    KafkaProducer<String, String> producer = buildKafkaProducer();

    //1.初始化事务
    producer.initTransactions();

    try {
      //2.开启事务
      producer.beginTransaction();
      //处理业务逻辑
      for (int i = 0; i < 10; i++) {
        if (i == 8) {
          //此时会出现错误，用来测试事物是否已经开启
          int j = 10/ 0;
        }
        ProducerRecord<String, String> record = new ProducerRecord<>("topic01", "key" + i,
            "value" + i);
        //发送消息给服务器
        producer.send(record);
      }
      producer.flush();
      //3.事务提交
      producer.commitTransaction();
    }catch (Exception e) {
      System.err.println("出现错误了："+ e.getMessage());
      //4.终止事务
      producer.abortTransaction();
    } finally {
      producer.close();
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
}
