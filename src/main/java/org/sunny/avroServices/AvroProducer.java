package org.sunny.avroServices;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.sunny.avroDAO.AvroPartition;
import org.sunny.avroDAO.AvroSerializer;
import org.sunny.avroDAO.StockAvroBean;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AvroProducer {
    private ExecutorService threadPool = Executors.newFixedThreadPool(100);
    private static final String BROKER_LIST = "localhost:9092";
    private String[] topics = null;  //话题
    private static KafkaProducer<String,StockAvroBean> producer = null;

    public AvroProducer(String[] topics){
        this.topics = topics;
    }

    private static Properties initconfig(){
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);//broker_list
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());
        config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,AvroPartition.class.getName()); //自定义的分区准则
        return config;
    }

    static {
        Properties config = initconfig();
        producer = new KafkaProducer<String, StockAvroBean>(config);
    }


    private StockAvroBean createDate(){
        Random r = new Random();
        Integer stockCode = 600100+ r.nextInt(10);
        float random = (float) Math.random();
        //设置涨跌
        if (random/2<0.5){
            random = -random;
        }
        DecimalFormat format = new DecimalFormat(".00");
        StockAvroBean stock = StockAvroBean.newBuilder()
                .setCurrentPrice(Float.valueOf(format.format(11+random)))
                .setPreclosePrice(11.80f)
                .setOpenPrice(11.5f)
                .setStockCode(stockCode.toString())
                .setTradeTime(System.currentTimeMillis())
                .setStockName("股票-"+stockCode).build();

        return stock;
    }


    public void produce(){
        ProducerRecord<String,StockAvroBean> record = null;
        int i = 0;
        try {
            while (i<1000){
                StockAvroBean stock = createDate();
                //System.out.println(stock.toString());
                for (String topic : topics){
                    record =  new ProducerRecord<String, StockAvroBean>(topic,null,
                            stock.getTradeTime(),stock.getStockCode().toString(),stock);
                    threadPool.submit(new AvroProducerThread(producer,record));
                }

                if (i++%20 == 0){
                    Thread.sleep(2000L);
                }
            }

        }catch (Exception e){
                e.printStackTrace();
        }finally {
            producer.close();
            threadPool.shutdown();
        }
    }
}
