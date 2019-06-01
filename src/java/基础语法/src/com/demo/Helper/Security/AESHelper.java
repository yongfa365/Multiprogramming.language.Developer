package com.demo.Helper.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESHelper {
    public static String[] GetKeys() throws Exception {
        var keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);  //默认128，获得无政策权限后可为192或256
        var secretKey = keyGen.generateKey();
        var byteKey = secretKey.getEncoded();
        var key = Base64.getEncoder().encodeToString(byteKey);
        var iv = Base64.getEncoder().encodeToString(SecureRandom.getInstanceStrong().generateSeed(16));
        return new String[]{key, iv};
    }


    public static String AESEncrypt(String input, String key, String iv) throws Exception {
        var bytesKey = Base64.getDecoder().decode(key);
        var bytesIV = Base64.getDecoder().decode(iv);
        var inputBytes = input.getBytes(StandardCharsets.UTF_8);

        var secretKey = new SecretKeySpec(bytesKey, "AES");
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(bytesIV));
        var cipherByte = cipher.doFinal(inputBytes);

        var result = Base64.getEncoder().encodeToString(cipherByte);
        return result;
    }


    public static String AESDecrypt(String input, String key, String iv) throws Exception {
        var bytesKey = Base64.getDecoder().decode(key);
        var bytesIV = Base64.getDecoder().decode(iv);
        var inputBytes = Base64.getDecoder().decode(input);

        var secretKey = new SecretKeySpec(bytesKey, "AES");
        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(bytesIV));
        var cipherByte = cipher.doFinal(inputBytes);

        var result = new String(cipherByte, StandardCharsets.UTF_8);
        return result;
    }


}

