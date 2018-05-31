package org.sunny.services;

public class ProducerTest {
    public static void main(String[] args){
        StockProducer stockProducer = new StockProducer();
        stockProducer.send();
        System.out.println("all");
    }
}
