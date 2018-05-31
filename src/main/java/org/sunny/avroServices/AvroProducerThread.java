package org.sunny.avroServices;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.sunny.avroDAO.StockAvroBean;

public class AvroProducerThread implements Runnable {
    private KafkaProducer<String,StockAvroBean> producer = null;
    private ProducerRecord<String,StockAvroBean> record = null;

    public AvroProducerThread(KafkaProducer<String,StockAvroBean> producer,ProducerRecord<String,StockAvroBean> record){
        this.producer = producer;
        this.record = record;
    }

    @Override
    public void run(){
        this.producer.send(this.record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null){
                    e.printStackTrace();
                }
                if (recordMetadata != null){
                    System.out.printf("[topic]=%s,[partitions]=%d,[offset]=%d,[timestamp]=%d%n",
                            recordMetadata.topic(),recordMetadata.partition(),
                            recordMetadata.offset(),recordMetadata.timestamp());
                }
            }
        });
    }
}
