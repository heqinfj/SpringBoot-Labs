package cn.iocoder.springboot.lab04.rabbitmqdemo.core;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo13Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducerReturnCallback implements RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RabbitTemplate rabbitTemplate;

    public RabbitProducerReturnCallback(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //反序列化消息message 方法1
//        Object deserialize = SerializationUtils.deserialize(message.getBody());
//        if(deserialize instanceof Demo13Message){
//            Demo13Message demo13Message = (Demo13Message)deserialize;
//            logger.info("message反序列化后的对象内容为{}",demo13Message);
//        }

//         * 0-9-1 AMQP协议中并没有的定义 Message 类或者接口，但是当我们在做一些基本的 操作的是时候，例如 basicPublish() 的时候，传输的内容是一个字节数据和一些额外的分开的属性值。
//         * 在 Spring AMQP 中，就定义了一个 Message 类来代表一个更通用的AMQP 域。
//         * Message 类的主要目的就在于封装 content 和 一些属性。
//         * 从 1.5.7, 1.6.11, 1.7.4, 和 2.0.0 版本开始，如果一个Message的 Body 是一个序列化的后的 Serializable 对象，那么调用toString() 方法的时候，默认不会再进行反序列操作。
//         * 主要是为了预防不安全的反序列化操作。
//         * 默认情况下，只有 java.util 和 java.lang 两个包下的类会被反序列化。 如果你想增加某些类的反序列化操作，你可以调用 ``Message.addWhiteListPatterns(…). 方法，增加全限定符合通配符来指定某些类。
//         *
//         * https://blog.csdn.net/m0_46684016/article/details/118517681

        //反序列化消息message 方法2
        //Message.addWhiteListPatterns("cn.iocoder.*");

        //反序列化消息message 方法3
        Object msg = rabbitTemplate.getMessageConverter().fromMessage(message);
        if(msg instanceof Demo13Message){
            Demo13Message demo13Message = (Demo13Message)msg;
            if(demo13Message.getMdcContext() != null){
                MDC.setContextMap(demo13Message.getMdcContext());
            }
        }

        logger.error("[returnedMessage][message: [{}] replyCode: [{}] replyText: [{}] exchange: [{}] routingKey: [{}]]",
                message, replyCode, replyText, exchange, routingKey);
    }

}
