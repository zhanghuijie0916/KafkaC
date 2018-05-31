package org.sunny.services;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockConsumer {
    private ExecutorService threadPool = Executors.newFixedThreadPool(6);
    private static final Logger LOG = Logger.getLogger(StockConsumer.class);

    private Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer-group-c1");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        //设置偏移量自动提交以及偏移量提交的时间间隔
        /*properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);*/
        return properties;
    }

    public void consumer(){
        Properties config = initConfig();
        try {
            for (int i=1;i<6;i++){
                config.put(ConsumerConfig.CLIENT_ID_CONFIG,"consumer"+i);
                threadPool.submit(new KafkaConsumerThread(config,"kafka-action"));
            }
        }catch (Exception e){
            LOG.error("StockConsumer error!");
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }
}
