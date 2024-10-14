package com.qilin.entiy;

import cn.hutool.core.lang.UUID;

public class TrLock {


    private static final String uuidstr = UUID.randomUUID().toString(true)+"-";

    public String getLockName() {
        long l = Thread.currentThread().threadId();
        System.out.println("线程id："+l);
        return uuidstr;
    }

}
