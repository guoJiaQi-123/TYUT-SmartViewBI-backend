package com.yupi.springbootinit.bizmq;

import com.yupi.springbootinit.constant.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version v1.0
 * @author OldGj 2024/9/2
 * @apiNote 用于创建交换机和队列
 */
@Configuration
public class RabbitMQConfig {

    // 创建交换机
    @Bean(MqConstant.BI_EXCHANGE)
    public Exchange getExchange() {
        return ExchangeBuilder
                .topicExchange(MqConstant.BI_EXCHANGE) // 交换机类型 （交换机名称）
                .durable(true) // 是否持久化
                .build();
    }


    // 创建队列
    @Bean(MqConstant.BI_QUEUE)
    public Queue getMessageQueue() {
        return new Queue(MqConstant.BI_QUEUE); // 队列名
    }


    // 交换机绑定队列
    @Bean
    public Binding bindMessageQueue(
            @Qualifier(MqConstant.BI_EXCHANGE) Exchange exchange,
            @Qualifier(MqConstant.BI_QUEUE) Queue queue) {
        return BindingBuilder
                .bind(queue)    //队列
                .to(exchange)    //交换机
                .with(MqConstant.BI_ROUTEING)//通配符路由关键字
                .noargs();//没有其他参数
    }

}
