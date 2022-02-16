package cn.iocoder.springboot.labs.lab10.springdatarediswithjedis;

import cn.iocoder.springboot.labs.lab10.springdatarediswithjedis.cacheobject.UserCacheObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringSetKey() {
        stringRedisTemplate.opsForValue().set("yunai", "shuai");
    }

    @Test
    public void testStringSetKey02() {
        redisTemplate.opsForValue().set("yunai", "shuai");
    }

    @Test
    public void testSetAdd() {
        stringRedisTemplate.opsForSet().add("yunai_descriptions", "shuai", "cai");
    }

    @Test
    public void testStringSetKeyUserCache() {
        Object targetObject = null;
        UserCacheObject userCacheObject = new UserCacheObject()
                .setId(1)
                .setName("芋道源码")
                .setGender(1); // 男
        String key = String.format("user:%d", userCacheObject.getId());

        //测试设置对象 UserCacheObject
        targetObject = userCacheObject;

        //测试设置List
//        List<UserCacheObject> list = new ArrayList<>();
//        list.add(userCacheObject);
//        targetObject = list;

        redisTemplate.opsForValue().set(key, targetObject);
    }

    @Test
    public void testStringGetKeyUserCache() {
        String key = String.format("user:%d", 1);
        Object value = redisTemplate.opsForValue().get(key);
        //List<UserCacheObject> list = (List<UserCacheObject>)value;
        UserCacheObject userCacheObject = (UserCacheObject)value;
        System.out.println(value);
    }

}
