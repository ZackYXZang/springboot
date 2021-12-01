package com.example.kafka_mq.patitioner;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

//生产者的分区策略
public class KafkaProducerPartitioner {

  public static void main(String[] args) {
    //1.创建KafkaProducer，自定义分区策略
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
    properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, UserDefinePartitioner.class.getName());

    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


    //有key的时候
    for (int i = 0; i < 10; i++) {
      ProducerRecord<String, String> record = new ProducerRecord<>("topic01", "key" + i,
          "value" + i);
      //发送消息给服务器
      producer.send(record);
    }

    //没有key的时候
    for (int i = 0; i < 10; i++) {
      ProducerRecord<String, String> record = new ProducerRecord<>("topic01",
          "value" + i);
      //发送消息给服务器
      producer.send(record);
    }

    //关闭生产者
    producer.close();
  }
}
