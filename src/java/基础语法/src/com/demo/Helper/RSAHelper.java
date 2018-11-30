package com.demo.Helper;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAHelper {

    public  static void GetKeys() throws NoSuchAlgorithmException {

        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA"); //初始化密钥对生成器，密钥大小为1024位
        keyPairGen.initialize(1024); //生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair(); //得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate(); //得到公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

    }

    public static byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes) throws Exception {
        if (publicKey != null) { //Cipher负责完成加密或解密工作，基于RSA
            var cipher = Cipher.getInstance("RSA"); //根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            var resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }


    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes) throws Exception {
        if (privateKey != null) { //Cipher负责完成加密或解密工作，基于RSA
            var cipher = Cipher.getInstance("RSA"); //根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            var resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }

}
