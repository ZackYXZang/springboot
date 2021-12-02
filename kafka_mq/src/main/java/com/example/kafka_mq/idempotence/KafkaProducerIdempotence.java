package com.example.kafka_mq.idempotence;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

//生产者，幂等
public class KafkaProducerIdempotence {

  public static void main(String[] args) {
    //1.创建KafkaProducer
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "CentOSA:9092,CentOSB:9092,CentOSB:9092");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

    //这是kafka Acks以及Retries
    properties.put(ProducerConfig.ACKS_CONFIG, "all");
    //不包含第一次发送，如果尝试发送3次，失败，则系统放弃发送
    properties.put(ProducerConfig.RETRIES_CONFIG, 3);
    //检测超时的时间设置为1ms
    properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1);
    //开启kafka的幂等性
    properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    //如果要开启kafka的幂等性，要开启上面的Acks以及Retries，以及下面这个属性
    //这个属性标识，如果超过了1条，就会阻塞kafka，来处理消息，设置为1，可以保证有序性
    //为了保证幂等性，不能超过5条
    properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,1);

    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


    for (int i = 0; i < 10; i++) {
      ProducerRecord<String, String> record = new ProducerRecord<>("topic01", "key" + i,
          "value" + i);
      //发送消息给服务器
      producer.send(record);
    }

    //关闭生产者
    producer.close();
  }
}
