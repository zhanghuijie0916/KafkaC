package org.sunny.avroServices;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.sunny.avroDAO.StockAvroBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AvroConsumerThread implements Runnable{
    private KafkaConsumer<String,StockAvroBean> consumer = null;


    public AvroConsumerThread(Properties config, List<TopicPartition> topicPartitionList){
        consumer = new KafkaConsumer<String, StockAvroBean>(config);
        consumer.assign(topicPartitionList);  //订阅
        consumer.seekToBeginning(topicPartitionList);  //移动到起始位置
    }

    /**
     * 模拟批量存储数据库的操作
     * @param buffer
     */
    public void simulateSaveToDB(List<ConsumerRecord<String,StockAvroBean>> buffer){
        for (ConsumerRecord<String,StockAvroBean> record : buffer){
            System.out.printf("[topic]=%s,[partitions]=%d,[offsets]=%d,[key]=%s,[value]=%s%n",
                    record.topic(),record.partition(),record.offset(),record.key(),
                    record.value().toString());
        }

    }

    @Override
    public void run() {
        List<ConsumerRecord<String,StockAvroBean>> buffer = new ArrayList<>();
        boolean flag = true;
        try {
            while (flag){
                ConsumerRecords<String,StockAvroBean> records = consumer.poll(1000);
                for (ConsumerRecord<String,StockAvroBean> record : records){
                    buffer.add(record);
                }

                if (records.isEmpty()){
                    flag = false;
                    simulateSaveToDB(buffer);  //打印不足的50的
                    consumer.commitAsync();
                    System.out.println("拉取结束");
                }

                if (buffer.size()>50){
                    consumer.commitAsync(new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                            if (e != null){
                                e.printStackTrace();
                            }
                            else
                                System.out.println("offset提交成功");
                        }
                    });
                    simulateSaveToDB(buffer);
                    buffer.clear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }
}
