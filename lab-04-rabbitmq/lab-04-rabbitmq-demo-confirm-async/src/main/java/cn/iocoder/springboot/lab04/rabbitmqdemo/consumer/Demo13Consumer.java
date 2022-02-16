package cn.iocoder.springboot.lab04.rabbitmqdemo.consumer;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo13Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = Demo13Message.QUEUE)
public class Demo13Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo13Message message) {
        Map<String, String> mdcContext = message.getMdcContext();
        if(mdcContext != null){
            MDC.setContextMap(mdcContext);
        }
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        sayHello();
    }

    private void sayHello(){
        logger.info("Welcome to Here !");
    }

}
