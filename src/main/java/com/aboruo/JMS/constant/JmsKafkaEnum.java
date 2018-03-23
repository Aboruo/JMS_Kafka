package com.aboruo.JMS.constant;

/**
 * 类名称: JmsKafkaEnum
 * 类描述: kafka消息队列常量枚举类型
 * @author aboruo
 * @date 2018-03-22 11:56
 */
public enum JmsKafkaEnum {
    SUCCESS_CODE("00000","成功"),
    KAFKA_SEND_ERROR("30001","发送消息超时,联系相关技术人员"),
    KAFKA_NO_RESULT("30002","未查询到返回结果,联系相关技术人员"),
    KAFKA_NO_OFFSET("30003","未查到返回数据的offset,联系相关技术人员");
    private String code;
    private String codeDesc;
    JmsKafkaEnum(String code,String codeDesc){
        this.code = code;
        this.codeDesc = codeDesc;
    }
    /**
     * @Title: code
     * @Description: 获取枚举类型的编码值
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午12:02
     */
    public String code(){
        return this.code;
    }
    /**
     * @Title: desc
     * @Description: 获取枚举类型的说明值
     * @author: aboruo
     * @param
     * @return
     * @date 18-3-22 下午12:02
     */
    public String desc(){
        return this.codeDesc;
    }
}
