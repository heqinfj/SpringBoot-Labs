package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace;

import java.util.UUID;

/**
 * @Author heqin
 * @Date 2022/1/13 16:10
 */
public class TraceIdUtils {

    /**
     * 生成traceId
     *
     * @return TraceId 基于UUID
     */
    public static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
