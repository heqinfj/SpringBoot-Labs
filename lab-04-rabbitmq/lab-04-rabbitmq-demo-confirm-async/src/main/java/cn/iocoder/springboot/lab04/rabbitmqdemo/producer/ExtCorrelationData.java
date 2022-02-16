package cn.iocoder.springboot.lab04.rabbitmqdemo.producer;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.Map;

/**
 * @Author heqin
 * @Date 2022/1/13 8:06
 */
public class ExtCorrelationData<T> extends CorrelationData {

    /**
     * MDC容器
     * 获取父线程MDC中的内容，做日志链路
     *
     * 返回当前线程上下文映射的副本，键和值类型为 String。
     */
    private Map<String, String> mdcContext = MDC.getCopyOfContextMap();

    /**
     * 消息体
     */
    private T message;

    /**
     * 交换机名称
     */
    private String exchange;

    /**
     * 路由key
     */
    private String routingKey;


    public ExtCorrelationData(String id, T message) {
        super(id);
        this.message = message;
    }

    public Map<String, String> getMdcContext() {
        return mdcContext;
    }

    public void setMdcContext(Map<String, String> mdcContext) {
        this.mdcContext = mdcContext;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String toString() {
        return "ExtCorrelationData{" +
                "mdcContext=" + mdcContext +
                ", message=" + message +
                ", exchange='" + exchange + '\'' +
                ", routingKey='" + routingKey + '\'' +
                "} " + super.toString();
    }
}
