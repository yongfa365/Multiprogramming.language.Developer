package com.demo.Helper.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESHelper {
    public static String[] GetFixedKeys() throws Exception {
        return new String[]{"ll7uz0DREVGBA9IJxmnwoEsJoQtgpGPqXQOzmYgaS6o=", "yuntM97GbF5ISjSsx0qKqA=="};
    }

    public static String[] GetKeys() throws Exception {
        var keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        var secretKey = keyGen.generateKey();
        var byteKey = secretKey.getEncoded();
        var key = Base64.getEncoder().encodeToString(byteKey);
        var iv = Base64.getEncoder().encodeToString(SecureRandom.getInstanceStrong().generateSeed(16));
        return new String[]{key, iv};
    }

    /**
     * 原文String-->使用utf8编码成byte[]-->加密成byte[]-->使用Base64转码成String-->密文String
     *
     * @param input 原文
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String AESEncrypt(String input, String key, String iv) throws Exception {
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(key), getIvSpec(iv));
        var cipherByte = cipher.doFinal(inputBytes);

        var result = Base64.getEncoder().encodeToString(cipherByte);
        return result;
    }

    /**
     * 密文String-->使用Base64转码成byte[]-->解密成byte[]-->使用utf8解码成String-->原文String
     * @param input 密文
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String AESDecrypt(String input, String key, String iv) throws Exception {
        var inputBytes = Base64.getDecoder().decode(input);

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(key), getIvSpec(iv));
        var cipherByte = cipher.doFinal(inputBytes);

        var result = new String(cipherByte, StandardCharsets.UTF_8);
        return result;
    }

    private static SecretKeySpec getKeySpec(String key) {
        var bytesKey = Base64.getDecoder().decode(key);
        var keySpec = new SecretKeySpec(bytesKey, "AES");
        return keySpec;
    }

    private static IvParameterSpec getIvSpec(String iv) {
        var bytesIV = Base64.getDecoder().decode(iv);
        var ivSpec = new IvParameterSpec(bytesIV);
        return ivSpec;
    }


}

