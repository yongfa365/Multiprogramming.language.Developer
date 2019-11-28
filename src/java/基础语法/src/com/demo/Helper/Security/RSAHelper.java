package com.demo.Helper.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAHelper {

    //region 各种方法 生成公钥与私钥

    /**
     * 使用KeyPairGenerator生成PublicKey,PrivateKey
     * 建议用这个工具生成：<a href="https://github.com/yongfa365/RsaKeyGen">生成C#,Java等可通用的RSA密码对</a>
     * 实现方法参考自：https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
     */
    public static String[] GenPublicKeyAndPrivateKey() {
        try {
            var keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);//初始化密钥对生成器，密钥大小为1024位
            var keyPair = keyGen.generateKeyPair();
            var publickey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            var privatekey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            return new String[]{publickey, privatekey};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PublicKey GetPublicKey(String publicKey) {
        try {
            var keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
            var spec = new X509EncodedKeySpec(keyBytes);
            var keyGen = KeyFactory.getInstance("RSA");
            var key = keyGen.generatePublic(spec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey GetPrivateKey(String privateKey) {
        try {
            var keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
            var spec = new PKCS8EncodedKeySpec(keyBytes);
            var keyGen = KeyFactory.getInstance("RSA");
            var key = keyGen.generatePrivate(spec);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 证明可以用PrivateKey生成PublicKey
     */
    public static String GetPublicKeyByPrivateKey(String privateKey) {
        try {
            var privateKeyBytes = GetPrivateKey(privateKey);
            var privk = (RSAPrivateCrtKey) privateKeyBytes;

            var spec = new RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());
            var keyGen = KeyFactory.getInstance("RSA");
            var publicKey = keyGen.generatePublic(spec);
            var publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            return publicKeyBase64;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //endregion


    //region RSA加解密

    public static String RSAEncrypt(String input, String publicKey) {
        var key = GetPublicKey(publicKey);
        var result = Encrypt(input, key);
        return result;
    }

    public static String RSADecrypt(String input, String privateKey) {
        var key = GetPrivateKey(privateKey);
        var result = Decrypt(input, key);
        return result;
    }


    public static String RSAEncryptByPrivateKey(String input, String privateKey) {
        var key = GetPrivateKey(privateKey);
        var result = Encrypt(input, key);
        return result;
    }

    public static String RSADecryptByPublicKey(String input, String publicKey) {
        var key = GetPublicKey(publicKey);
        var result = Decrypt(input, key);
        return result;
    }
    //endregion


    //region 数字签名
    public static String RSASingnature(String input, String privateKey) {
        try {
            var key = GetPrivateKey(privateKey);
            var inputBytes = Base64.getDecoder().decode(input);

            var signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(key);
            signature.update(inputBytes);
            var resultBytes = signature.sign();
            var result = Base64.getEncoder().encodeToString(resultBytes);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean RSASingnatureCheck(String input, String publicKey, String sign) {
        try {
            var key = GetPublicKey(publicKey);
            var inputBytes = Base64.getDecoder().decode(input);
            var signBytes = Base64.getDecoder().decode(sign);

            var signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(key);
            signature.update(inputBytes);
            var result = signature.verify(signBytes);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //endregion


    //region Core
    private static String Encrypt(String input, Key key) {
        try {
            var inputBytes = input.getBytes(StandardCharsets.UTF_8);
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            var resultBytes = cipher.doFinal(inputBytes);
            var result = Base64.getEncoder().encodeToString(resultBytes);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String Decrypt(String input, Key key) {
        try {
            var inputBytes = Base64.getDecoder().decode(input);
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            var resultBytes = cipher.doFinal(inputBytes);
            var result = new String(resultBytes, StandardCharsets.UTF_8);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

}
