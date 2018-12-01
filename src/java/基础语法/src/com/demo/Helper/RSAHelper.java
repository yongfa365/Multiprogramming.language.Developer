package com.demo.Helper;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAHelper {

    public static String[] GetKeys() throws Exception {
        //https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		//建议用这个工具生成：https://github.com/yongfa365/RsaKeyGen
        var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);//初始化密钥对生成器，密钥大小为1024位
        var keyPair = keyGen.generateKeyPair();
        var publickey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        var privatekey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        return new String[]{publickey, privatekey};
    }

    public static String RSAEncrypt(String input, String publicKey) throws Exception {
        var keyBytes = Base64.getDecoder().decode((publicKey.getBytes()));
        var spec = new X509EncodedKeySpec(keyBytes);
        var keyGen = KeyFactory.getInstance("RSA");
        var key = keyGen.generatePublic(spec);

        var inputBytes = input.getBytes("utf-8");
        var cipher = Cipher.getInstance("RSA"); //根据公钥，对Cipher对象进行初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        var resultBytes = cipher.doFinal(inputBytes);
        var result = Base64.getEncoder().encodeToString(resultBytes);
        return result;
    }

    public static String RSADecrypt(String input, String privateKey) throws Exception {
        var keyBytes = Base64.getDecoder().decode((privateKey.getBytes()));
        var spec = new PKCS8EncodedKeySpec(keyBytes);
        var keyGen = KeyFactory.getInstance("RSA");
        var key = keyGen.generatePrivate(spec);

        var inputBytes = Base64.getDecoder().decode(input);
        var cipher = Cipher.getInstance("RSA"); //根据公钥，对Cipher对象进行初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        var resultBytes = cipher.doFinal(inputBytes);
        var result = new String(resultBytes, "utf-8");
        return result;
    }


}
