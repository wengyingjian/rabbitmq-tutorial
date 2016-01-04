/**
 * 
 */
package com.wengyingjian.rabbitmq.publishsubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 * 
 * @author <a href="mailto:wengyingjian@foxmail.com">翁英健</a>
 * @version 1.1 2016年1月1日
 * @since 1.1
 */

public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";
    private final static String REMOTE_HOST   = "121.42.32.99";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(REMOTE_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明一个名称为EXCHANGE_NAME，类型为fanout的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 然后从交换机中获取一个队列
        String queueName = channel.queueDeclare().getQueue();
        // 让交换机与队列绑定上
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
