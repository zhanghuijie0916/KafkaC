package org.sunny.services;

import kafka.log.Log;
import kafka.log.Log$;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.sunny.DAO.StockInfo;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockProducer {
    private static final Logger LOG = Logger.getLogger(StockProducer.class);

    private static String[] topics = new String[]{"kafka-action"}; //发送到主题列表
    private static KafkaProducer<String,String> producer = null;  //生产者
    private static ExecutorService threadPool = Executors.newFixedThreadPool(100);

    private static final String BROKER_LIST = "localhost:9092";

    static {
        Properties properties = initConfig();
        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 为producer设置参数
     * @return
     */
    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return properties;
    }

    /*
    创造股票价格
     */
    private StockInfo createStockInfo(){
    StockInfo stockInfo = new StockInfo();
    Random r = new Random();
    Integer stockCode = 600100+ r.nextInt(10);
    float random = (float) Math.random();
    //设置涨跌
    if (random/2<0.5){
        random = -random;
    }

    DecimalFormat format = new DecimalFormat(".00");
    stockInfo.setCurrentPrice(Float.valueOf(format.format(11+random)));
    stockInfo.setPreClossPrice(11.80f);
    stockInfo.setOpenPrice(11.5f);
    stockInfo.setLowPrice(10.5f);
    stockInfo.setHighPrice(12.5f);
    stockInfo.setStockCode(stockCode.toString());
    stockInfo.setTradeTime(System.currentTimeMillis());
    stockInfo.setStockName("股票-"+stockCode);
    return stockInfo;
    }

    /*
    发送
     */
    public void send(){
        ProducerRecord<String,String> record = null;  //消息

        int i = 0;
        try {
            while (i <= 1000){
                StockInfo stockInfo = createStockInfo();
                for (String topic:topics){
                    /*
                    ProducerRecord的构造函数
                    ProducerRecord(java.lang.String topic,java.lang.Integer partition, java.lang.Long timestamp, K key, V value)
                     */
                    record = new ProducerRecord<String, String>(topic,null,
                            stockInfo.getTradeTime(),stockInfo.getStockCode(),stockInfo.toString());
                    threadPool.submit(new KafkaProducerThread(producer,record));
                }
                if(i++ %100 == 0){
                    Thread.sleep(2000L);
                }

            }
        }catch (Exception e){
            LOG.error("生产数据异常！");
        }finally {
            threadPool.shutdown();
            producer.close();
        }

    }
}
