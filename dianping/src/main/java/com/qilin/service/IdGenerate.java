package com.qilin.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class IdGenerate {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public BigInteger getGloballyUniqueId(String keyPrefix){
        long l = System.currentTimeMillis();
        String format = LocalDate.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        long increment = stringRedisTemplate.opsForValue().increment(keyPrefix+":"+format);
        String s = new DecimalFormat("0000").format(increment);
        return new BigInteger(l+s);


    }
}
