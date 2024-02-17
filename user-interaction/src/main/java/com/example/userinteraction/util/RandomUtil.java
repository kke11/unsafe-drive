package com.example.userinteraction.util;

import java.util.Random;

public class RandomUtil {
    public static Random random = new Random();

    /**
     *@Author keith
     *@Date 2024/2/16 12:44
     *@Description 生成盐x位小写字母随机盐
     */
    public static String saltGeneration(int length){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // 生成随机的小写字母
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     *@Author keith
     *@Date 2024/2/16 12:44
     *@Description 生成x位0-9的数字
     */
    public static String numberGeneration(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10); // 生成0-9的随机数字
            stringBuilder.append(randomNumber); // 将随机数字拼接到字符串中
        }
        return stringBuilder.toString(); // 将 StringBuilder 转换为字符串
    }
}
