package org.sunny.services;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

public class KafkaProducerThread implements Runnable{
    private static final Logger LOG = Logger.getLogger(StockProducer.class);

    private KafkaProducer<String,String> producer = null;
    private ProducerRecord<String,String> record = null;

    public KafkaProducerThread(KafkaProducer<String,String> producer,ProducerRecord<String,String> record){
        this.producer = producer;
        this.record = record;
    }

    @Override
    public void run() {
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
               if (e!=null){
                   LOG.error("send messages error!");
               }
               if(recordMetadata!=null){
                   System.out.println("[offset]="+recordMetadata.offset()+
                           ",[partition]="+recordMetadata.partition()+
                           ",[topic]="+recordMetadata.topic());
               }
            }
        });
    }
}
