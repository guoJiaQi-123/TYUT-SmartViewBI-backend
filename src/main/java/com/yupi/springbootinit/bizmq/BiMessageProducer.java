package com.yupi.springbootinit.bizmq;

import com.yupi.springbootinit.constant.MqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version v1.0
 * @author OldGj 2024/9/2
 * @apiNote 消息生产者
 */
@Component
public class BiMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息生产者
     * @param message
     */
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(MqConstant.BI_EXCHANGE, MqConstant.BI_ROUTEING, message);
    }

}
