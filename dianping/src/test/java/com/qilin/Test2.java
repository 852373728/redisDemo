package com.qilin;

import com.qilin.entiy.TrLock;

import java.util.Collections;
import java.util.List;

public class Test2 {

    public static void main(String[] args) {

        new Thread(()->{
            TrLock trLock = new TrLock();
            String lockName = trLock.getLockName();
            String lockName1 = trLock.getLockName();
            System.out.println(lockName);
            System.out.println(lockName1);

        }).start();

        List<String> strings = Collections.singletonList("1");
        new Thread(()->{
            TrLock trLock1 = new TrLock();
            String lockName2 = trLock1.getLockName();
            System.out.println(lockName2);

        }).start();




    }

}
