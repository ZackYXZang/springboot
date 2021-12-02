package com.example.kafka_mq.service;

public interface IMessageSender {

  public void sendMessage(String topic, String key, String message);
}
