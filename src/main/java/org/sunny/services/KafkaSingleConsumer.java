package org.sunny.services;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.util.*;

public class KafkaSingleConsumer {
    private KafkaConsumer<String,String> consumer = null;
    private static final int MIN_COMMIT_NUM = 30;
    private List<TopicPartition> partitions = new ArrayList<>();

    private Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer-group-c2");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"consumerc");
        //注意，这里是反序列化的xxxDeserializer类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        //设置偏移量自动提交以及偏移量提交的时间间隔
        /*properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);*/
        return properties;
    }

    public KafkaSingleConsumer(String[] topics){
        Properties config = initConfig();
        this.consumer = new KafkaConsumer<String, String>(config);
        //this.consumer.subscribe(Arrays.asList(topics));

        partitions.add(new TopicPartition("kafka-action",0));
        partitions.add(new TopicPartition("kafka-action",1));
        partitions.add(new TopicPartition("kafka-action",2));
        this.consumer.assign(partitions);

        //将每一个分区的
        /*for (TopicPartition partition: partitions){
            consumer.seek(partition,0L);
        }*/
        //consumer.seekToBeginning(partitions); //和上面的for循环等价
        //consumer.seekToEnd(partitions);  //将offset定位到最末尾

    }

    public void consume(){
        List<ConsumerRecord<String,String>> buffer = new ArrayList<>();
        ConsumerRecords<String,String> records = null;

        boolean flag = true;
        while (flag){
            records = consumer.poll(1000);
            System.out.println("records is empty:"+records.isEmpty());

            for (ConsumerRecord<String,String> record : records){
                buffer.add(record);
            }
            if (buffer.size()>MIN_COMMIT_NUM){
                simulateSaveToDB(buffer);
                buffer.clear();
            }
            if (records.isEmpty()){
                simulateSaveToDB(buffer);
                flag = false;
            }
        }
    }

    /**
     * 模拟批量存储数据库的操作
     * @param buffer
     */
    public void simulateSaveToDB(List<ConsumerRecord<String,String>> buffer){
        for (ConsumerRecord<String,String> record : buffer){
            System.out.printf("[topic]=%s,[partitions]=%d,[offsets]=%d,[key]=%s,[value]=%s%n",
                    record.topic(),record.partition(),record.offset(),record.key(),record.value());
        }

    }
}
