package cn.iocoder.springboot.lab04.rabbitmqdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author heqin
 * @Date 2022/1/15 10:22
 */
@RestController
public class AnotherController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void test01(){
        logger.info("test01....");
    }
}
