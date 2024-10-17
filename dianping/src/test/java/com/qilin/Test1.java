package com.qilin;

import cn.hutool.core.util.BooleanUtil;
import com.qilin.service.IdGenerate;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.core.StreamOperations;
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


    @Test
    public void test1() throws InterruptedException {




    }


}
