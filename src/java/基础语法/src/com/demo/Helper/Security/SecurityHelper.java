package com.demo.Helper.Security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class SecurityHelper {

    public static String toBase64(String input) {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        var result = Base64.getEncoder().encodeToString(inputBytes);
        return result;
    }

    public static String fromBase64(String input) {
        var inputBytes = Base64.getDecoder().decode(input);
        var result = new String(inputBytes, StandardCharsets.UTF_8);
        return result;
    }


    public static String toSHA1(String input) throws Exception {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        var provider = MessageDigest.getInstance("SHA-1");
        provider.update(inputBytes);

        var result = String.format("%01x", new BigInteger(1, provider.digest()));
        return result;
    }


    public static String toSHA512(String input) throws Exception {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        var provider = MessageDigest.getInstance("SHA-512");
        provider.update(inputBytes);

        var result = String.format("%01x", new BigInteger(1, provider.digest()));
        return result;
    }


    public static String to16BitMD5(String input) throws Exception {
        var full = to32BitMD5(input);
        var result = full.substring(8, 24);
        return result;
    }


    public static String to32BitMD5(String input) throws Exception {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        var provider = MessageDigest.getInstance("MD5");
        provider.update(inputBytes);
        //%032x=%0第一个参数，32是最终返回的最小长度，x是以16进制返回
        var result = String.format("%032x", new BigInteger(1, provider.digest()));
        return result;
    }

}
