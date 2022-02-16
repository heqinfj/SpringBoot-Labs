package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.demo_controlleradvice;

import java.io.Serializable;

/**
 * @Author heqin
 * @Date 2022/2/15 16:18
 */
public class ServerResponse implements Serializable {

    private static final long serialVersionUID = -6238840061398692881L;

    private Integer status;

    private String msg;

    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ServerResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.status.equals(ResponseCode.SUCCESS.getCode());
    }

    public static ServerResponse success() {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), null, null);
    }

    public static ServerResponse success(String msg) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), null, data);
    }

    public static ServerResponse success(String msg, Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static ServerResponse error(String msg) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static ServerResponse error(Object data) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), null, data);
    }

    public static ServerResponse error(String msg, Object data) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), msg, data);
    }
}
