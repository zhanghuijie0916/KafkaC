package org.sunny.avroServices;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvroProducerTest {

    public static void main(String[] args){
        AvroProducer producer = new AvroProducer(new String[]{"avro-kafka"});
        producer.produce();
        System.out.println("end");
    }

}