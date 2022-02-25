package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_interceptor;

import cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.InterceptReqWayEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author heqin
 * @Date 2022/1/13 19:05
 */
@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {

    @Value("${mcdWholeLinkLogTrace.interceptWay}")
    private String interceptWay;

    @Bean
    //@ConditionalOnProperty(prefix="mcdWholeLinkLogTrace",name = "interceptWay",havingValue = "Interceptor")
    public LogInterceptor logInterceptor(){
        if(InterceptReqWayEnum.Interceptor.getCode().equals(interceptWay)){
            return new LogInterceptor();
        }else{
            return null;
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LogInterceptor logInterceptor = logInterceptor();//正确的写法：这样获取LogInterceptor的注入属性testComponent有值
        //LogInterceptor logInterceptor = new LogInterceptor();//有坑的写法：这样获取LogInterceptor的注入属性testComponent为null
        if(logInterceptor != null){
            logInterceptor.printTestComponent();
            registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
        }
    }
}
