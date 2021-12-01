package com.example.kafka_mq.patitioner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

//自己定义的生产者的分区规则
public class UserDefinePartitioner implements Partitioner {

  private AtomicInteger counter = new AtomicInteger(0);
  //返回分区号
  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
      Cluster cluster) {
    //获取所有分区
    List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
    int numPartitions = partitions.size();
    if (keyBytes == null) {
      //如果key为空，轮询（用当前的counter模上分区数）
      int nextValue = this.counter.getAndIncrement();
      List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
      if (availablePartitions.size() > 0) {
        int part = Utils.toPositive(nextValue) % availablePartitions.size();
        return ((PartitionInfo)availablePartitions.get(part)).partition();
      } else {
        return Utils.toPositive(nextValue) % numPartitions;
      }
    } else {
      //如果key不为null，用key的hash模上分区数
      return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> map) {

  }
}
