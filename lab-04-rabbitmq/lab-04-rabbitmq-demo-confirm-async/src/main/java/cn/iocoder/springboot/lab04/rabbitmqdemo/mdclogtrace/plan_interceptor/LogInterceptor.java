package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_interceptor;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.Constants;
import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.TraceIdUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author heqin
 * @Date 2022/1/13 17:22
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = TraceIdUtils.getTraceId();
        }

        MDC.put(Constants.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //调用结束后删除
        MDC.remove(Constants.TRACE_ID);
    }
}
