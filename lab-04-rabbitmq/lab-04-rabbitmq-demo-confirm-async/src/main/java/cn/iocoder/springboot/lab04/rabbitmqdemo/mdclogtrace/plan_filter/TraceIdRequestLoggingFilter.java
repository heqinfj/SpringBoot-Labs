package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_filter;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.Constants;
import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.TraceIdUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author heqin
 * @Date 2022/1/13 16:11
 */
public class TraceIdRequestLoggingFilter extends AbstractRequestLoggingFilter {
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String requestId = request.getHeader(Constants.TRACE_ID);
        if (!StringUtils.isEmpty(requestId)) {
            TraceIdConfig.TraceIdContext.setTraceId(requestId);
        } else {
            TraceIdConfig.TraceIdContext.setTraceId(TraceIdUtils.getTraceId());
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        TraceIdConfig.TraceIdContext.removeTraceId();
    }
}
