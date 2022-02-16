package cn.iocoder.springboot.lab04.rabbitmqdemo.consumer;

import cn.iocoder.springboot.lab04.rabbitmqdemo.message.Demo10Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = Demo10Message.QUEUE)
public class Demo10Consumer_NotSplitQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //方案二，在Consumer端，将Queue摘取到的消息，将相关联的消息发送到相同的线程中来消费。
    private static final List<ExecutorService> EXECUTORSERVICES = new CopyOnWriteArrayList();

    static {
        for (int i = 0; i < Demo10Message.QUEUE_COUNT; i++) {
            ExecutorService executor = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
            EXECUTORSERVICES.add(executor);
        }
    }

    @RabbitHandler(isDefault = true)
    public void onMessage(Message<Demo10Message> message) {

        //handle01(message);
        handle02(message);

    }

    private static String getQueue(Message<Demo10Message> message) {
        return message.getHeaders().get("amqp_consumerQueue", String.class);
    }

    private void handle01(Message<Demo10Message> message){
        mockCallRemote();
        logger.info("[onMessage][线程编号:{} Queue:{} 消息编号：{}]", Thread.currentThread().getId(), getQueue(message),
                message.getPayload().getId());
    }

    /**
     * 模拟远程调用耗时
     */
    private void mockCallRemote(){
        try {
            //2秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handle02(Message<Demo10Message> message) {
        //方案二，在Consumer端，将Queue摘取到的消息，将相关联的消息发送到相同的线程中来消费。
        Integer id = message.getPayload().getId();
        int idx = id % Demo10Message.QUEUE_COUNT;
        ExecutorService executorService = EXECUTORSERVICES.get(idx);
        executorService.submit(() -> {
            mockCallRemote();
            logger.info("[onMessage][线程编号:{} 线程名称:{} Queue:{} 消息编号：{}]", Thread.currentThread().getId(),
                    Thread.currentThread().getName(), getQueue(message),
                    message.getPayload().getId());
        });
    }

}
