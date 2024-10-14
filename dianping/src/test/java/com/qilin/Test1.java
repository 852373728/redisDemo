package com.qilin;

import com.qilin.service.IdGenerate;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class Test1 {


    @Resource
    private IdGenerate idGenerate;


    @Resource
    private StringRedisTemplate stringRedisTemplate;



    ExecutorService executorService = Executors.newFixedThreadPool(500);
    @Test
    public void test1() throws InterruptedException {

        BigInteger order = idGenerate.getGloballyUniqueId("order");
        long l = order.longValue();
        System.out.println(l);

    }



}
