package com.qilin.utils;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.BooleanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class RedisDistributedLock {

    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_PREFIX = "distributedLock:";
    private static final String JVM_ID = UUID.randomUUID().toString(true);
    private static final  DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
    static {
        defaultRedisScript.setLocation(new ClassPathResource("redisUnlock.lua"));
        defaultRedisScript.setResultType(Long.class);
    }


    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean tryLock(String key, long expire) {
        long threadId = Thread.currentThread().threadId();

        log.info("{}{}", JVM_ID, threadId);
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + key, JVM_ID+threadId, expire, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(result);
    }



    public void unlock(String key) {
        long threadId = Thread.currentThread().threadId();
        log.info("{}{}", JVM_ID, threadId);
        stringRedisTemplate.execute(defaultRedisScript, Collections.singletonList(LOCK_PREFIX + key),JVM_ID+threadId);
    }
}
