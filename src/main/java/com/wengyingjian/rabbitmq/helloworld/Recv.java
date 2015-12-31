/**
 * 
 */
package com.wengyingjian.rabbitmq.helloworld;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消费者
 * 
 * @author <a href="mailto:wengyingjian@foxmail.com">翁英健</a>
 * @version 1.1 2015年12月30日
 * @since 1.1
 */
public class Recv {
    private final static String QUEUE_NAME  = "hello";
    private final static String REMOTE_HOST = "121.42.32.99";

    public static void main(String[] argv) throws Exception {
        // 建立通道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(REMOTE_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明队列，告诉rabbitmq我到时候要监听名称为QUEUE_NAME的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 回调函数，监听到消息了该怎么处理。
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 开始监听（开始等待消费）
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
