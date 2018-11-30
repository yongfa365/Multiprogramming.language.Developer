package com.demo.Helper;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class AESHelper {
    public static byte[] GetKeys() throws Exception {
        var keyGen = KeyGenerator.getInstance("AES");//密钥生成器
        keyGen.init(128);  //默认128，获得无政策权限后可为192或256
        var secretKey = keyGen.generateKey();//生成密钥
        var key = secretKey.getEncoded();//密钥字节数组
        var key2 = secretKey.getFormat();//密钥字节数组
        return  key;
    }

/*

    public static string AESEncrypt(this string input, string key, string iv)
    {
    SecretKey secretKey = new SecretKeySpec(key, "AES");//恢复密钥
    Cipher cipher = Cipher.getInstance("AES");//Cipher完成加密或解密工作类
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);//对Cipher初始化，解密模式
    byte[] cipherByte = cipher.doFinal(data);//加密data
    }





    public static string AESDecrypt(this string input, string key, string iv)
    {
      SecretKey secretKey = new SecretKeySpec(key, "AES");//恢复密钥
Cipher cipher = Cipher.getInstance("AES");//Cipher完成加密或解密工作类
cipher.init(Cipher.DECRYPT_MODE, secretKey);//对Cipher初始化，解密模式
byte[] cipherByte = cipher.doFinal(data);//解密data
    }
*/


}
