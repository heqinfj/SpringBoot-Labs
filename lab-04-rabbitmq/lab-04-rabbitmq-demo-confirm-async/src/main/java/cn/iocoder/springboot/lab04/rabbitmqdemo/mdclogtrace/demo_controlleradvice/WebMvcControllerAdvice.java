package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.demo_controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author heqin
 * @Date 2022/2/15 16:14
 */
@ControllerAdvice
public class WebMvcControllerAdvice {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception e) {
        return ServerResponse.error(ResponseCode.SERVER_ERROR.getMsg());
    }

    @ModelAttribute(name = "md")
    public Map<String,Object> mydata() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", 99);
        map.put("gender", "男");
        return map;
    }
}
