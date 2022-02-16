package cn.iocoder.springboot.lab04.rabbitmqdemo.producer;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo13Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo13Producer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        logger.info("开始执行syncSend方法。。。");
        // 创建 Demo13Message 消息
        Demo13Message message = new Demo13Message();
        message.setId(id);
        // 同步发送消息
        //rabbitTemplate.convertAndSend(Demo13Message.EXCHANGE, Demo13Message.ROUTING_KEY, message);
        //CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String exchange = Demo13Message.EXCHANGE;
        String routingKey = Demo13Message.ROUTING_KEY;
        ExtCorrelationData<Demo13Message> correlationData = new ExtCorrelationData(String.valueOf(id),message);
        correlationData.setExchange(exchange);
        correlationData.setRoutingKey(routingKey);
        //Demo13Message message = correlationData.getMessage();
        rabbitTemplate.convertAndSend(exchange, routingKey, message,correlationData);
    }

    public void syncSendNack(Integer id) {
        // 创建 Demo13Message 消息
        Demo13Message message = new Demo13Message();
        message.setId(id);
        // 同步发送消息
        //模拟指定不存在的exchange（交换机）
        String exchange = "NOTEXISTEXCHANGE";
        String routingKey = Demo13Message.ROUTING_KEY;
        ExtCorrelationData extCorrelationData = new ExtCorrelationData<Demo13Message>(String.valueOf(id),message);
        extCorrelationData.setExchange(exchange);
        extCorrelationData.setRoutingKey(routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, message,extCorrelationData);
    }

    public void syncSendReturn(Integer id) {
        // 创建 Demo13Message 消息
        Demo13Message message = new Demo13Message();
        message.setId(id);
        // 同步发送消息
        String exchange = Demo13Message.EXCHANGE;
        String routingKey = "error";
        ExtCorrelationData extCorrelationData = new ExtCorrelationData<Demo13Message>(String.valueOf(id),message);
        extCorrelationData.setExchange(exchange);
        extCorrelationData.setRoutingKey(routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, message,extCorrelationData);
    }

}
