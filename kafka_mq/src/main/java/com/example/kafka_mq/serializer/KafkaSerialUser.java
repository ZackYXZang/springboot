package com.example.kafka_mq.serializer;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class KafkaSerialUser implements Serializable {

  public KafkaSerialUser(Integer id, String name, Date birthDay) {
    this.id = id;
    this.name = name;
    this.birthDay = birthDay;
  }

  Integer id;
  String name;
  Date birthDay;
}
