package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * rabbitmq 单向消息生产者
 */
public class SingleProducer {

    private final static String QUEUE_NAME = "hello"; // 队列名称

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.66.200"); // 绑定端口
        try (Connection connection = factory.newConnection();
             // 创建信道，信道就是程序操作消息队列的客户端，可以理解为client
             Channel channel = connection.createChannel()) {
            // 绑定队列
            /**
             * 参数一：队列名称
             * 参数二：是否持久化队列（注意：这里是队列持久化，和数据持久化还不一样）
             * 参数三：是否只允许当前这个创建消息队列的连接操作消息队列
             * 参数四：在不使用这个队列时，是否使其自动删除
             * 参数五：其他参数
             */
            Scanner scanner = new Scanner(System.in);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            while (scanner.hasNext()) {
                String message = scanner.nextLine(); // 构造消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}