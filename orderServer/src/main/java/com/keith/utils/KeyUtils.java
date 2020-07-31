package com.keith.utils;

import java.util.Random;

public class KeyUtils {
    //生成唯一的主键
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900 * 1000) + 100 * 1000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
