package com.example.unsafedrive.util;

import java.util.Random;

public class RandomUtil {
    public static Random random = new Random();

    /**
     *@Author keith
     *@Date 2024/2/15 12:57
     *@Description 生成随机x位数字
     */
    public static String generationVerificationCode(int length){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);  // 生成 0-9 之间的随机整数
            String randomChar = String.valueOf(randomNumber);  // 将随机整数转换为字符
            stringBuilder.append(randomChar);  // 将字符添加到字符串中
        }
        String result = stringBuilder.toString();  // 最终生成的字符串
        return result;
    }

    /**
     *@Author keith
     *@Date 2024/2/15 12:59
     *@Description 生成盐->x位小写字母
     */
    public static String generationPasswordSalt(int length){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // 生成随机的小写字母
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
