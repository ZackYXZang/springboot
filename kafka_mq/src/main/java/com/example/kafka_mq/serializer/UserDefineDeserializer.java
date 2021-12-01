package com.example.kafka_mq.serializer;


import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

//自己定义的消费者的反序列化规则
public class UserDefineDeserializer implements Deserializer<Object> {


  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    System.out.println("UserDefineDeserializer configure");
  }

  @Override
  public Object deserialize(String topic, byte[] data) {
    return SerializationUtils.deserialize(data);
  }

  @Override
  public Object deserialize(String topic, Headers headers, byte[] data) {
    return null;
  }

  @Override
  public void close() {
    System.out.println("UserDefineDeserializer close");

  }
}
