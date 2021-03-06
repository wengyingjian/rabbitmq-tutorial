/**
 * 
 */
package com.wengyingjian.rabbitmq.publishsubscribe;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * 生产者
 * 
 * @author <a href="mailto:wengyingjian@foxmail.com">翁英健</a>
 * @version 1.1 2016年1月1日
 * @since 1.1
 */

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";
    private final static String REMOTE_HOST   = "121.42.32.99";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(REMOTE_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明一个fanout类型的交换机。交换机必须要有名字。
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = getMessage(argv);
        // 往指定名称的的交换机中发送消息。因为此处指定的交换机为“fanout”模式的，所以不需要指定（绑定）队列。
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "info: Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}