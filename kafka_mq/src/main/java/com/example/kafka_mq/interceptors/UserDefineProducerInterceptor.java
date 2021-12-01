package com.example.kafka_mq.interceptors;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

//生产者拦截器
public class UserDefineProducerInterceptor implements ProducerInterceptor {

  @Override
  public ProducerRecord onSend(ProducerRecord producerRecord) {
    System.out.println("UserDefineProducerInterceptor send");
    //这里可以对消息进行一些处理
    return null;
  }

  //通知，无论成功还是失败都会通知
  @Override
  public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

  }

  @Override
  public void close() {
    System.out.println("UserDefineProducerInterceptor close");

  }

  @Override
  public void configure(Map<String, ?> map) {

  }
}
