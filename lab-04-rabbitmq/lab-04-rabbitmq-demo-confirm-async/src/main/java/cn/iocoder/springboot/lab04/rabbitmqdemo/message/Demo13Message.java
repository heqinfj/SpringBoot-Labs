package cn.iocoder.springboot.lab04.rabbitmqdemo.message;

import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Map;

public class Demo13Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_13";

    public static final String EXCHANGE = "EXCHANGE_DEMO_13";

    public static final String ROUTING_KEY = "ROUTING_KEY_13";

    private static final long serialVersionUID = 2628732938324138968L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * MDC容器
     * 获取父线程MDC中的内容，做日志链路
     *
     * 返回当前线程上下文映射的副本，键和值类型为 String
     */
    private Map<String, String> mdcContext = MDC.getCopyOfContextMap();

    public Demo13Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Map<String, String> getMdcContext() {
        return mdcContext;
    }

    public void setMdcContext(Map<String, String> mdcContext) {
        this.mdcContext = mdcContext;
    }

    @Override
    public String toString() {
        return "Demo13Message{" +
                "id=" + id +
                '}';
    }

}
