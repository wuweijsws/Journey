package com.journey.murmurhash;

import com.google.common.hash.Hashing;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 哈希算法测试
 * @Author: java
 * @Date: 2019-11-21 23:51
 */
@SuppressWarnings("all")
public class MurmurHashTest {

    public static void main(String[] args) {
        //定义两个字符串
        String str1 = "abcd";
        String str2 = "abce";
        System.out.println("java的hash");
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());

        System.out.println("Murmurhash");
        System.out.println(Hashing.murmur3_32().hashString(str1, StandardCharsets.UTF_8).asInt());
        System.out.println(Hashing.murmur3_32().hashString(str2, StandardCharsets.UTF_8).asInt());
    }


    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 使用Spring框架自带的Md5加密方法
     * @param str 要加密的字符串
     * @return md5Str
     */
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 使用guava的murmur3_32
     * @param str 要加密的字符串
     * @return murStr
     */
    public static String murmurhash3_32(String str) {
        return Hashing.murmur3_32().hashString(str, StandardCharsets.UTF_8).toString();
    }



}
