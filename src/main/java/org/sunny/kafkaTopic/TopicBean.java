package org.sunny.kafkaTopic;

import java.util.Properties;

public class TopicBean {

    private String topicName;
    private int replication_factor;
    private String zookeeper;
    private int partitions;
    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getReplication_factor() {
        return replication_factor;
    }

    public void setReplication_factor(int replication_factor) {
        this.replication_factor = replication_factor;
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public int getPartitions() {
        return partitions;
    }

    public void setPartitions(int partitions) {
        this.partitions = partitions;
    }

    public TopicBean(String topicName, int replication_factor, String zookeeper, int partitions,Properties properties){
        this.topicName = topicName;
        this.replication_factor = replication_factor;
        this.zookeeper = zookeeper;
        this.partitions = partitions;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "TopicConfig{" +
                "topicName='" + topicName + '\'' +
                ", replication_factor=" + replication_factor +
                ", zookeeper='" + zookeeper + '\'' +
                ", partitions=" + partitions +
                ", properties=" + properties +
                '}';
    }
}
