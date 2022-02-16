package cn.iocoder.springboot.lab04.rabbitmqdemo.controller;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.demo_controlleradvice.ServerResponse;
import cn.iocoder.springboot.lab04.rabbitmqdemo.producer.Demo13Producer;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @Author heqin
 * @Date 2022/1/13 16:19
 */
@RestController
public class ExampleController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AnotherController anotherController;

    @Autowired
    private Demo13Producer producer;

    //@RequestMapping("/testSyncSend")
    @GetMapping("/testSyncSend")
    public String testSyncSendxxx() {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
        anotherController.test01();
        return "world";
    }

    @RequestMapping("/testSyncSendNack")
    public String testSyncSendNack() {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendNack(id);
        logger.info("[testSyncSendNack][发送编号：[{}] 发送成功]", id);
        return "world";
    }

    @RequestMapping("/testSyncSendReturn")
    public String testSyncSendReturn() {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendReturn(id);
        logger.info("[testSyncSendReturn][发送编号：[{}] 发送成功]", id);
        return "world";
    }

    @RequestMapping("/testControllerAdviceHandleException")
    public ServerResponse testControllerAdviceHandleException(){
        int a = 1/0;
        return ServerResponse.success("test idempotent success");
    }

    @RequestMapping("/testControllerAdviceHandle")
    public String testControllerAdviceHandle(){
        ServerResponse response = ServerResponse.success("test idempotent success");
        return JSON.toJSONString(response);
    }

    @GetMapping("/testControllerAdviceHandleModel")
    public String testControllerAdviceHandleModel(Model model) {
        Map<String, Object> map = model.asMap();
        System.out.println(map);
        return "hello controller advice";
    }
}
