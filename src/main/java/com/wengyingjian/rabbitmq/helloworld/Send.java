/**
 * 
 */
package com.wengyingjian.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 * 
 * @author <a href="mailto:wengyingjian@foxmail.com">翁英健</a>
 * @version 1.1 2015年12月30日
 * @since 1.1
 */
public class Send {
    // 队列名称，消息往这个队列里面发送
    private final static String QUEUE_NAME  = "hello";
    // RabbitMQ主机名称，现在有效可用，阿里云服务器，1G内存。。穷
    private final static String REMOTE_HOST = "121.42.32.99";

    public static void main(String[] argv) throws Exception {
        // 新建一个连接，这里只需要指定RabbitMQ-server所在host地址
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(REMOTE_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        // 往指定的queueName的队列中发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        // 关闭
        channel.close();
        connection.close();
    }
}
