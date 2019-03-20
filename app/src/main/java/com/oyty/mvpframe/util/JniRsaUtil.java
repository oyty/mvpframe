package com.oyty.mvpframe.util;

import java.security.PrivateKey;

public class JniRsaUtil {
    /**
     * 用jni获取私钥对信息生成数字签名
     *
     * @param data 加密数据
     */
    public static String jniSign(byte[] data) {
        String sign = "";
        String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIHfV1R7igfDm92y+RYzNxoekJpWU9aK02/79vjskrxGZz9iUMDQUanbVcpU8eJ31PgCUyF7N6wu6L3R99hNU4ZDI2A9FDqRkrvk/g7qVPVMx1N874QdkGlOw4ol82pXABSgz625P6NLmsbVmpDhM6R9hkuYbsxtpZSTpbb/hPdjAgMBAAECgYEAgNBwgzghmT/YWlYJH47e6YZFcP+NGnAJ9bnZpd6oUBwYjlLKu+QkC7CkcBI+9Jxc3T0ZDU1009CXQqd/B8jyvP6Q/XvvY/ou1Ozt9yu2A8+2wH7ZxmsR4no109Q27rDKBYzaBUC3hlAaV1x3oJy9X8FEh9Kap1rJe7qTfXrjI2ECQQCmDZyaWhblSvU7Ia536eIaEoyrSR8Nro5We8kvd7kocWAuj1TKFFFuSpQLMdSUSkHDlNiDX2YEcvhv//ft1q2zAkEAyDiXGBCQs7Vu3UyU9hQCZwIbnZi/YRScGEcRfx1m9k7hOJm+9dp1etaD+qMo3JKBWAxX/ILPvg91cAKEKaiXkQJAc7aGi5T94XiznSoibxN/aNXGStIgnqMmMyP9lWNjlsgy0x80YcF44Vd9BLQLw+5fF5CQtDN1XuJTN5Dyzz1LOwJAYy7tYkVqeSuKhOPX8uLModyAc5xkSaIdBUXHXBHjnGZM2W4IE+ApTDWR4YoLxuBoZIaQ24ytfnhH87m7YKuckQJBAJk36btiqVyGeYAzcjfXw1pyIZNkYg9uZIRsuvf2lv0EcjTojRDXdSCciKJXdUmDT94iDJDKCpSW5Y1ERBTmTjw=";
//        String PRIVATE_KEY = NdkJniUtil.get();
        try {
//          从字符串中得到私钥
            PrivateKey privateKey = RSAUtil.loadPrivateKey(PRIVATE_KEY);
            //用私钥签名
            sign = RSAUtil.sign(data, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }
}
