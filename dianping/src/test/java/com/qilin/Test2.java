package com.qilin;

import com.qilin.entiy.TrLock;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        Redisson redissonClient = (Redisson) Redisson.create(config);
        RedissonLock lock = (RedissonLock) redissonClient.getLock("ordaw");
        RLock multiLock = redissonClient.getMultiLock(lock);
        boolean b = multiLock.tryLock(50,60, TimeUnit.SECONDS);
        System.out.println(b);

        CompletableFuture<Long> completableFuture = new CompletableFuture<>();
        completableFuture.completeExceptionally(new RuntimeException("获取失败"));
       // boolean complete = completableFuture.complete(1L);
        Long l = completableFuture.get(3, TimeUnit.SECONDS);
        System.out.println(l);
        CompletableFuture<Integer> integerCompletableFuture = completableFuture.thenApply(e -> {
            return 3;
        });




    }

}
