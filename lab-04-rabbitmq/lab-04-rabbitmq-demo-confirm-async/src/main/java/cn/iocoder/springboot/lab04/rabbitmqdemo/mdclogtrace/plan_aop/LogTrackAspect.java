package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_aop;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.Constants;
import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.TraceIdUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author heqin
 * @Date 2022/1/13 10:30
 */
@Component
@Aspect
@ConditionalOnProperty(prefix = "mcdWholeLinkLogTrace", name = "interceptWay", havingValue = "Aop")
public class LogTrackAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogTrackAspect.class);

    //@Pointcut("within(cn.iocoder.springboot.lab04.rabbitmqdemo..*)")
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping)")
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        boolean isSuccess = setMdc();
        Object result;
        // 执行方法，并获取返回值
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (isSuccess) {
                    mockExecBiz(methodName);
                    MDC.remove(Constants.TRACE_ID);
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    /**
     * 模拟业务耗时
     */
    private void mockExecBiz(String mockMethodName) {
        String targetMethodName = "testSyncSendxxx";
        if (targetMethodName.equals(mockMethodName)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("模拟业务耗时执行完毕。");
        }
    }

    /**
     * 为每个请求设置唯一标示到MDC容器中
     *
     * @return
     */
    private boolean setMdc() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        String traceId = null;
        if(servletRequestAttributes != null){
            logger.info("servletRequestAttributes的值为{}",servletRequestAttributes);
            //spring aop中获取request与response
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            traceId = request.getHeader(Constants.TRACE_ID);
        }
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.getTraceId();
        }
        try {
            // 设置 traceId
            if (StringUtils.isEmpty(MDC.get(Constants.TRACE_ID))) {
                MDC.put(Constants.TRACE_ID, traceId);
                return true;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }

        return false;
    }

}
