package com.example.kafka_mq;

import com.example.kafka_mq.service.IMessageSender;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaOperations.OperationsCallback;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class KafkaMqApplicationTests {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private IMessageSender messageSender;

  @Test
  void contextLoads() {
  }


  //非事务下执行生产者发送消息
  public void testSendMessageWithOutTransaction() {
    kafkaTemplate.send(new ProducerRecord<>("topic02", "key2", "value2"));
  }

  //方式一：事务下执行生产者发送消息
  public void testSendMessageWithTransaction01() {
    kafkaTemplate.executeInTransaction(new OperationsCallback<String, String, Object>() {
      @Override
      public Object doInOperations(KafkaOperations<String, String> kafkaOperations) {
        kafkaOperations.send(new ProducerRecord<>("topic02", "key2", "value2"));
        return null;
      }
    });
  }

  //方式二：事务下执行生产者发送消息，利用spring的事务管理
  public void testSendMessageWithTransaction02() {
    messageSender.sendMessage("topic02", "key2", "value2");
  }


}
