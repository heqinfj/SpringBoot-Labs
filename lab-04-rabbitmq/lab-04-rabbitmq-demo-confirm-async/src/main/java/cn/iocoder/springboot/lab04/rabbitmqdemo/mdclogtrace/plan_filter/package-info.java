/**
 * 过滤器拦截 方案
 * <p>
 * 既然我们要实现日志链路的追踪，最直观的思路就是在访问的源头生成一个请求ID，然后一路传下去，直到这个请求完成。
 * 这里以Http为例，通过Filter来拦截请求，并将数据通过Http的Header来存储和传递数据。
 * 涉及到系统之间调用时，调用方设置requestId到Header中，被调用方从Header中取即可。
 * https://juejin.cn/post/6950772721139580936
 *
 * @Author heqin
 * @Date 2022/1/14 10:25
 */
package cn.iocoder.springboot.lab04.rabbitmqdemo.mdclogtrace.plan_filter;