package org.sunny.avroServices;

import org.junit.Test;
import org.sunny.services.StockConsumer;

import static org.junit.Assert.*;

public class AvroConsumerTest {

    public static void main(String[] args){
        AvroConsumer consumer = new AvroConsumer("avro-kafka",6);
        consumer.consume();
    }

}