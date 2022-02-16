package cn.iocoder.springboot.lab04.rabbitmqdemo.core;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo13Message;
import cn.iocoder.springboot.lab04.rabbitmqdemo.producer.ExtCorrelationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducerConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public RabbitProducerConfirmCallback(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(correlationData instanceof ExtCorrelationData){
            ExtCorrelationData<Demo13Message> extCorrelationData = (ExtCorrelationData<Demo13Message>)correlationData;
            Demo13Message message = extCorrelationData.getMessage();
            if (extCorrelationData.getMdcContext() != null) {
                // 日志链路跟踪
                MDC.setContextMap(extCorrelationData.getMdcContext());
            }
            logger.info("消息体的值为{}",message);
        }
        if (ack) {
            logger.info("[confirm][Confirm 成功 correlationData: {}]", correlationData);
        } else {
            logger.error("[confirm][Confirm 失败 correlationData: {} cause: {}]", correlationData, cause);
        }
    }

}
