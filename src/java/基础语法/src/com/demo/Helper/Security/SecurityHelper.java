package com.demo.Helper.Security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

public class SecurityHelper {

    public static String ToBase64(String input) throws Exception {
        var inputBytes = input.getBytes("utf-8");
        var result = Base64.getEncoder().encodeToString(inputBytes);
        return result;
    }

    public static String FromBase64(String input) throws Exception {
        var inputBytes = Base64.getDecoder().decode(input);
        var result = new String(inputBytes, "utf-8");
        return result;
    }


    public static String ToSHA1(String input) throws Exception {
        var inputBytes = input.getBytes("utf-8");
        var provider = MessageDigest.getInstance("SHA-1");
        provider.update(inputBytes);

        var result = String.format("%01x", new BigInteger(1, provider.digest()));
        return result;
    }


    public static String ToSHA512(String input) throws Exception {
        var inputBytes = input.getBytes("utf-8");
        var provider = MessageDigest.getInstance("SHA-512");
        provider.update(inputBytes);

        var result = String.format("%01x", new BigInteger(1, provider.digest()));
        return result;
    }


    public static String To16bitMD5(String input) throws Exception {
        var full = To32bitMD5(input);
        var result = full.substring(8, 24);
        return result;
    }


    public static String To32bitMD5(String input) throws Exception {
        var inputBytes = input.getBytes("utf-8");
        var provider = MessageDigest.getInstance("MD5");
        provider.update(inputBytes);
        //%032x=%0第一个参数，32是最终返回的最小长度，x是以16进制返回
        var result = String.format("%032x", new BigInteger(1, provider.digest()));
        return result;
    }

}
