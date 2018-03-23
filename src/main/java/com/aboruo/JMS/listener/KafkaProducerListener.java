package com.aboruo.JMS.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * 类名称: KafkaProducerListener
 * 类描述: kafka消息生产监听者
 *
 * @author aboruo
 * @date 2018-03-22 12:06
 */
@Component(value = "producerListener")
public class KafkaProducerListener implements ProducerListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * @Title: onSuccess
     * @Description: 生产者消息发送成功时，日志记录
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午12:16
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        logger.info("========kafka发送数据成功，日志记录开始=======>");
        logger.info("====消息标题topic[{}]",topic);
        logger.info("====消息分区信息partition[{}]",partition);
        logger.info("====key：{}",key);
        logger.info("====value:{}",value);
        logger.info("====recordMetadata:{}",recordMetadata);
        logger.info("====kafka发送消息数据成功，日志记录结束======>");
    }
    /**
     * @Title: onError
     * @Description: 生产者消息发送失败时，日志记录
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午12:16
     */
    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception e) {
        logger.info("========kafka发送数据失败，日志记录开始=======>");
        logger.info("====消息标题topic[{}]",topic);
        logger.info("====消息分区信息partition[{}]",partition);
        logger.info("====key：{}",key);
        logger.info("====value:{}",value);
        logger.info("====kafka消息发送异常，异常信息：{}",e);
        logger.info("====kafka发送消息数据成功，日志记录结束======>");
    }
    /**
     * @Title: isInterestedInSuccess
     * @Description: 是否对 消息发送成功 感兴趣
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午12:17
     */
    @Override
    public boolean isInterestedInSuccess() {
        logger.info("kafkaProducer监听器启动");
        return true;
    }
}
