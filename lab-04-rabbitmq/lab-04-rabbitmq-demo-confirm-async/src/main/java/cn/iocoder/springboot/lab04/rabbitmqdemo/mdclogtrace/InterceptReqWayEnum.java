package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace;

/**
 * 拦截请求方式
 * spring aop、过滤器、拦截器
 *
 * @Author heqin
 * @Date 2022/1/14 10:47
 */
public enum InterceptReqWayEnum {

    AOP("Aop", "aop方式"),
    FILTER("Filter", "过滤器方式"),
    Interceptor("Interceptor", "拦截器方式");


    private String code;

    private String desc;

    InterceptReqWayEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
