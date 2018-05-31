package org.sunny.services;

import org.sunny.services.StockProducer;

public class ConsumerTest {
    public static void main(String[] args){
        /*StockConsumer consumer = new StockConsumer();
        consumer.consumer();*/

        KafkaSingleConsumer consumer = new KafkaSingleConsumer(new String[]{"kafka-action"});
        consumer.consume();
    }
}
