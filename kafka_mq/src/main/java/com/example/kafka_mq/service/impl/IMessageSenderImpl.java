package com.example.kafka_mq.service.impl;

import com.example.kafka_mq.service.IMessageSender;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IMessageSenderImpl implements IMessageSender {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;


  @Override
  public void sendMessage(String topic, String key, String message) {
    kafkaTemplate.send(new ProducerRecord<>(topic, key, message));
  }
}
