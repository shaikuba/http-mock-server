package cn.shaikuba.mock.common.signature;

import cn.shaikuba.mock.common.exception.MockSecurityApiException;
import cn.shaikuba.mock.common.security.AES;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class Signature {

    public static String sign(String content, String signType, String privateKey, String charset) throws MockSecurityApiException {
        return sign(content, signType, privateKey, charset, null);
    }

    public static String sign(String content, String signType, String privateKey, String charset, String password) throws MockSecurityApiException {
        try {
            byte[] contentBytes = content.getBytes(charset);
            if (signType.equals("CA")) {
                //return IcbcCa.sign(contentBytes, privateKey, password);
            } else if (signType.equals("RSA")) {
                return sign(contentBytes, Base64.getDecoder().decode(privateKey), "SHA1WithRSA");
            } else if (signType.equals("RSA2")) {
                return sign(contentBytes, Base64.getDecoder().decode(privateKey), "SHA256WithRSA");
            } else {
                log.error("not support signType.");
                throw new MockSecurityApiException("not support signType.");
            }
        } catch (UnsupportedEncodingException var6) {
            log.error("get content charset exception. content: " + content + " charset: " + charset, var6);
            throw new MockSecurityApiException("get content charset exception. content: " + content + " charset: " + charset, var6);
        } catch (Exception var7) {
            log.error("sign exception.", var7);
            throw new MockSecurityApiException("sign exception.", var7);
        }
        return null;
    }

    public static boolean verify(String content, String signType, String publicKey, String charset, String sign) throws MockSecurityApiException {
        try {
            byte[] contentBytes = content.getBytes(charset);
            if (signType.equals("CA")) {
                return true;
                //return verify(contentBytes, Base64.getDecoder().decode(publicKey), sign);
            } else if (signType.equals("RSA")) {
                return verify(contentBytes, Base64.getDecoder().decode(publicKey), sign, "SHA1WithRSA");
            } else if (signType.equals("RSA2")) {
                return verify(contentBytes, Base64.getDecoder().decode(publicKey), sign, "SHA256WithRSA");
            } else {
                log.error("not support signType.");
                throw new MockSecurityApiException("not support signType.");
            }
        } catch (UnsupportedEncodingException var6) {
            log.error("get content charset exception, content: " + content + " charset: " + charset, var6);
            throw new MockSecurityApiException("get content charset exception. content: " + content + " charset: " + charset, var6);
        } catch (Exception var7) {
            log.error("sign verify exception.", var7);
            throw new MockSecurityApiException("sign verify exception.", var7);
        }
    }

    public static String sign(byte[] data, byte[] privateKey, String password, String algorithm) throws Exception {
        return sign(data, AES.aesDecrypt(privateKey, password), algorithm);
    }

    public static String sign(byte[] data, byte[] privateKey, String algorithm) throws Exception {
        PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        PrivateKey pk = keyfactory.generatePrivate(pkcs8);
        java.security.Signature signature = java.security.Signature.getInstance(algorithm);
        signature.initSign(pk);
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static boolean verify(byte[] data, byte[] publicKey, String sign, String algorithm) throws Exception {
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        PublicKey pk = keyfactory.generatePublic(encodedKeySpec);
        java.security.Signature signature = java.security.Signature.getInstance(algorithm);
        signature.initVerify(pk);
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign));
    }
}
