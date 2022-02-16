package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_filter;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.Constants;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @Author heqin
 * @Date 2022/1/13 16:14
 */
@Configuration
public class TraceIdConfig {

    @Bean
    @ConditionalOnProperty(prefix="mcdWholeLinkLogTrace",name = "interceptWay",havingValue = "Filter")
    public TraceIdRequestLoggingFilter traceIdRequestLoggingFilter() {
        return new TraceIdRequestLoggingFilter();
    }

    /**
     * @Author heqin
     * @Date 2022/1/13 16:07
     */
    public static class TraceIdContext {

        public static void setTraceId(String traceId) {
            if (!StringUtils.isEmpty(traceId)) {
                MDC.put(Constants.TRACE_ID, traceId);
            }
        }

        public static String getTraceId() {
            String traceId = MDC.get(Constants.TRACE_ID);
            return traceId == null ? "" : traceId;
        }

        public static void removeTraceId() {
            MDC.remove(Constants.TRACE_ID);
        }

        public static void clearTraceId() {
            MDC.clear();
        }
    }
}