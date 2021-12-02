package com.example.kafka_mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
public class KafkaMqApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaMqApplication.class, args);
  }


  //作为消费者，接收消息
  @KafkaListeners(value = {@KafkaListener(topics = "topic01")})
  public void receiveRecord01(ConsumerRecord<String, String> record) {
    System.out.println(record);
  }

  @KafkaListeners(value = {@KafkaListener(topics = "topic01")})
  @SendTo("topic03")
  public String receiveRecord02(ConsumerRecord<String, String> record) {
    System.out.println(record);
    return "record from topic01 : " + record.value() + "send to topic03";
  }


}
