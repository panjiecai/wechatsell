package com.wechatsell.wechatsell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 随机生成唯一主键
     * 格式：时间+随机数
     * synchronize多线程限制
     * @return
     */
    public static synchronized String genUniqueKey(){

        Random random = new Random();

        Integer number = random.nextInt(90000) + 10000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
