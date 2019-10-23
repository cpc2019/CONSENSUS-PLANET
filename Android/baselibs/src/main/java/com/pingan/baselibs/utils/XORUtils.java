package com.pingan.baselibs.utils;

/**
 * 异或工具
 *
 * Created by yelongfei490 on 2017/12/21.
 */
public class XORUtils {

    public static String xor(String content, String keyStr) {
        if (content == null || content.equals("")) return null;
        if (keyStr == null || keyStr.equals("")) return null;
        byte[] chs = content.getBytes();
        char[] keys = keyStr.toCharArray();
        int len = keys.length;
        int key;
        for (int i = 0; i < chs.length; i++) {
            key = keys[i % len];
            chs[i] = (byte) (chs[i] ^ key);
        }
        //return Base64.encodeToString(chs, Base64.DEFAULT);
        return new String(chs);
    }

    public static byte[] xor(byte[] content, String keyStr) {
        if (content == null || content.length == 0) return null;
        if (keyStr == null || keyStr.equals("")) return null;
        byte[] chs = content;
        char[] keys = keyStr.toCharArray();
        int len = keys.length;
        int key;
        for (int i = 0; i < chs.length; i++) {
            key = keys[i % len];
            chs[i] = (byte) (chs[i] ^ key);
        }
        return chs;
    }

}
