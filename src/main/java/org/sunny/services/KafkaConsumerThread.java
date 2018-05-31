package org.sunny.services;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.*;

public class KafkaConsumerThread implements Runnable{
    private static final Logger LOG = Logger.getLogger(StockConsumer.class);
    private KafkaConsumer<String,String> consumer;
    private static int MIN_BATCH_SIZE = 2;

    public KafkaConsumerThread(Properties config,String topic){
        this.consumer = new KafkaConsumer<String, String>(config);

        //订阅主题，订阅主题时指定一个回调监听器，当消费者发生平衡操作时，调用
        consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
        /*
        业务情景假设：有一个缓冲区，每拉取30条数据就存到数据库一次
         */
        List<ConsumerRecord<String,String>> buffer = new ArrayList<>();

        try {
            while(true){
                //consumer拉取数据，timeout为拉取时间
                ConsumerRecords<String,String> records = consumer.poll(1000);
                System.out.println(Thread.currentThread().getName()+records.isEmpty());
                for (ConsumerRecord<String,String> record:records){
                    buffer.add(record);
                }
                if (buffer.size()>=MIN_BATCH_SIZE){
                    //模拟存储数据库的操作
                    simulateSaveToDB(buffer);
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        }catch (Exception e){
            LOG.error("kafkaConsumerThread 出现错误！");
            e.printStackTrace();
        }finally {
            consumer.close();
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
