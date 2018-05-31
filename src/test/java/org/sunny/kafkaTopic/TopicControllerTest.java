package org.sunny.kafkaTopic;

import org.junit.Test;

import static org.junit.Assert.*;


import org.junit.Ignore;
import org.junit.Test;
import org.sunny.kafkaTopic.TopicBean;
import org.sunny.kafkaTopic.TopicController;

import java.util.Properties;

public class TopicControllerTest {
    @Ignore
    public void createTopic() throws Exception {
        Properties properties = new Properties();
        properties.put("max.message.bytes","204800");
        TopicBean config = new TopicBean("java-kafka",
                1,"localhost:2181",2,properties);
        TopicController.createTopic(config);
    }

    @Ignore
    public void createTopic2()throws Exception {
        String s = "--zookeeper localhost:2181 --create --topic kafka-action " +
                "--partitions 3 --replication-factor 1" +
                " --if-not-exists --config max.message.bytes=204800 ,flush.messages=2";
        TopicController.createTopic(s);
    }

    @Test
    public void listAllTopic() throws Exception {
        TopicController.listAllTopic("localhost:2181");
    }

    @Ignore
    public void alterTopicConfig() throws Exception {
        Properties properties = new Properties();
        properties.put("flush.messages","3");
        TopicController.alterTopicConfig("java-kafka",properties);

    }

    @Test
    public void listTopicAllConfig() throws Exception {
        TopicController.listTopicAllConfig();
    }


    @Ignore
    public void deleteTopic() throws Exception {
        TopicController.deleteTopic("kafka-action");
    }

}