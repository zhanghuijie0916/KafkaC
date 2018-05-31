package org.sunny.avroServices;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.sunny.avroDAO.AvorDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AvroConsumer {
    private static final String BOOTSTRAP_SERVER = "LOCALHOST:9092";
    private List<TopicPartition> topicPartitionList = new ArrayList<>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(6);

    public AvroConsumer(String topic,int partitions){
        TopicPartition topicPartition = null;
        for (int i=0;i<partitions;i++){
            topicPartition = new TopicPartition(topic,i);
            topicPartitionList.add(topicPartition);
        }
    }

    private Properties initConfig(){
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"avro-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvorDeserializer.class.getName());
        return config;
    }

    public void consume(){
        try {
            Properties config = initConfig();
            for (int i=0;i<6;i++){
                threadPool.submit(new AvroConsumerThread(config,topicPartitionList));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
