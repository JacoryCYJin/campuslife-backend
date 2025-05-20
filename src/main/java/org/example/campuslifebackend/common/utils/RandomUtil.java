package org.example.campuslifebackend.common.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机工具类
 */
public class RandomUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    /**
     * 生成指定长度的随机字符串
     * @param length 长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (length <= 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
    
    /**
     * 生成指定范围内的随机整数
     * @param min 最小值（包含）
     * @param max 最大值（不包含）
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }
}