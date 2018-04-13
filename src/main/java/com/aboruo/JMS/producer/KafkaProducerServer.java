package com.aboruo.JMS.producer;

import com.aboruo.JMS.constant.JmsKafkaEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * 类名称: KafkaProducerServer
 * 类描述: 消息生产者
 *
 * @author aboruo
 * @date 2018-03-22 20:18
 */
@Component(value = "kafkaProducerServer")
public class KafkaProducerServer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * @Title: sendMsgForTemplate
     * @Description: 发送消息
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午8:56
     */
    public Map<String,Object> sendMsgForTemplate(String topic,Object value,String ifPartition,Integer partitionNum,String role){
        String key = role + value.hashCode();
        String valueString = value == null ? "" : JSON.toJSONString(value);
        if(ifPartition.equals("0")){
            //表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            Map<String,Object> res = checkProRecord(result);
            return res;
        }else{
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
            Map<String,Object> res = checkProRecord(result);
            return res;
        }
    }
    /**
     * @Title: getPartitionIndex
     * @Description: 获取分区索引
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午8:37
     */
    private int getPartitionIndex(String key,int partitionNum){
        if(StringUtils.isEmpty(key)){
            Random random = new Random();
            return random.nextInt(partitionNum);
        }else {
            int result = Math.abs(key.hashCode()) % partitionNum;
            return result;
        }
    }
    /**
     * @Title: checkProRecord
     * @Description: 检查发送返回结果
     * @author: aboruo
     * @param 
     * @return 
     * @date 18-3-22 下午8:45
     */
    private Map<String,Object> checkProRecord(ListenableFuture<SendResult<String, String>> res){
        Map<String,Object> map = Maps.newConcurrentMap();
        if(res != null){
            try {
                SendResult r = res.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if(offsetIndex!=null && offsetIndex>=0){
                    map.put("code", JmsKafkaEnum.SUCCESS_CODE.code());
                    map.put("message", JmsKafkaEnum.SUCCESS_CODE.desc());
                    return map;
                }else{
                    map.put("code", JmsKafkaEnum.KAFKA_NO_OFFSET.code());
                    map.put("message", JmsKafkaEnum.KAFKA_NO_OFFSET.desc());
                    return map;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                map.put("code", JmsKafkaEnum.KAFKA_SEND_ERROR.code());
                map.put("message", JmsKafkaEnum.KAFKA_SEND_ERROR.desc());
                return map;
            } catch (ExecutionException e) {
                e.printStackTrace();
                map.put("code", JmsKafkaEnum.KAFKA_SEND_ERROR.code());
                map.put("message", JmsKafkaEnum.KAFKA_SEND_ERROR.desc());
                return map;
            }
        }else {
            map.put("code", JmsKafkaEnum.KAFKA_NO_RESULT.code());
            map.put("message", JmsKafkaEnum.KAFKA_NO_RESULT.desc());
            return map;
        }
    }
}
