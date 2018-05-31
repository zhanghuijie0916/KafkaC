package org.sunny.avroDAO;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.lang.StringUtils;

/**
 * 将topic和value对应的类对应起来
 */
public enum TopicEnum {
    STOCK_AVOR("avro-kafka",new StockAvroBean()); //实例

    private String topic;
    private SpecificRecordBase record;

    private TopicEnum(String topic,SpecificRecordBase record){
        this.topic = topic;
        this.record = record;
    }

    public String getTopic() {
        return topic;
    }

    public SpecificRecordBase getRecord() {
        return record;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setRecord(SpecificRecordBase record) {
        this.record = record;
    }

    public static TopicEnum getTopicEnum(String topicName){
        if (topicName.isEmpty()){
            return null;
        }

        for (TopicEnum topicEnum : values()){
            if (StringUtils.equalsIgnoreCase(topicEnum.getTopic(),topicName)){
                return topicEnum;
            }
        }
        return null;
    }
}
