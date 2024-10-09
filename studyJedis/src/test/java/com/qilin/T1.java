package com.qilin;

import com.qilin.bean.User;
import com.qilin.bean.UserAddress;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
public class T1 {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1(){
        String userId = UUID.randomUUID().toString();
        ArrayList<UserAddress> userAddressArrayList = new ArrayList<>();
        UserAddress userAddress1 = new UserAddress(UUID.randomUUID().toString(),userId,"河南省","郑州市","高新区","药厂街兰寨新城");
        UserAddress userAddress2 = new UserAddress(UUID.randomUUID().toString(),userId,"河南省","郑州市","高新区","药厂街兰寨新城");
        userAddressArrayList.add(userAddress1);
        userAddressArrayList.add(userAddress2);

        User user1 = new User(userId,"孙麒麟",31,userAddressArrayList);

        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("name1",user1);

        Object name1 = valueOperations.get("name1");
        System.out.println(name1);

    }

    @Test
    public void test2(){
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();



    }



}
