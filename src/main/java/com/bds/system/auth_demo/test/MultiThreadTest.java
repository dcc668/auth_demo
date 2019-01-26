package com.bds.system.auth_demo.test;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Const;
import com.bds.tool.bds_auth.AuthInterface;
import com.bds.tool.bds_auth.impl.AuthImpl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guoyu on 2018/7/23.
 */
public class MultiThreadTest {
    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(testThread);
            thread.start();
        }
        CountThread countThread = new CountThread();
        Thread thread = new Thread(countThread);
        thread.start();
    }

}

class TestThread implements Runnable {
    @Override
    public void run() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcmVhdGVfdGltZSI6IjIwMTgtMDctMTIgMTg6NDE6MzIiLCJ1c2VyX2lkIjoiNSIsInVzZXJfbmFtZSI6Imd1b2d1byIsImlzcyI6Imd1b3l1IiwiaWQiOiI1IiwiZXhwIjoxNTMyMzU5MjY0fQ.PI9U-8fx54HJLRVadMo9R5H7aL3FFb7MUPTUxoNfxWA";
        // 授权
        AuthInterface authInterface = new AuthImpl();
        JSONObject jsonObject1 = null;
        while (true) {
            try {
                jsonObject1 = authInterface.auth(Const.APP_ID, Const.SECRET, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonObject1.getString("code").equals("suc")) {
                Common.totalCount.addAndGet(1);
            } else {
                Common.failCount.addAndGet(1);
            }
        }
    }
}

class CountThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println(
                    (Common.totalCount.get() - Common.failCount.get()) + " / " + Common.totalCount.get());
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
        }
    }
}

class Common {
    public static AtomicInteger totalCount = new AtomicInteger(0);
    public static AtomicInteger failCount = new AtomicInteger(0);
}
