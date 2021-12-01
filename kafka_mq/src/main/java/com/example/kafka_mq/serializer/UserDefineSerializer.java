package com.example.kafka_mq.serializer;


import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

//自己定义的生产者的序列化规则
public class UserDefineSerializer implements Serializer<Object> {


  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    System.out.println("UserDefineSerializer configure");
  }

  @Override
  public byte[] serialize(String topic, Object data) {
    return SerializationUtils.serialize((Serializable) data);
  }

  @Override
  public byte[] serialize(String topic, Headers headers, Object data) {
    return new byte[0];
  }

  @Override
  public void close() {
    System.out.println("UserDefineSerializer close");

  }
}
