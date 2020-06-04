package cn.shaikuba.mock.common.security;

import cn.shaikuba.mock.common.exception.MockSecurityApiException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);
    private static final String CHARSET_UTF8 = "UTF-8";

    public static String aesEncrypt(String content, String aesKey) throws MockSecurityApiException {
        return aesEncrypt(content, aesKey, CHARSET_UTF8);
    }

    public static String aesEncrypt(String content, String aesKey, String charset) throws MockSecurityApiException {
        try {
            return new String(aesEncrypt(content.getBytes(charset), aesKey));
        } catch (Exception var4) {
            throw new MockSecurityApiException("AES加密失败：AES content = " + content + "; charset = " + charset, var4);
        }
    }

    public static byte[] aesEncrypt(byte[] content, String aesKey) throws MockSecurityApiException {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(1, new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(content);
            return Base64.getEncoder().encode(encryptBytes);
        } catch (Exception var5) {
            throw new MockSecurityApiException("AES加密失败：AES content = " + content, var5);
        }
    }

    public static String aesDecrypt(String content, String aesKey) throws MockSecurityApiException {
        return aesDecrypt(content, aesKey, "UTF-8");
    }

    public static byte[] aesDecrypt(byte[] content, String aesKey) throws MockSecurityApiException {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES"), iv);
            return cipher.doFinal(Base64.getDecoder().decode(content));
        } catch (Exception var4) {
            throw new MockSecurityApiException("AES解密失败：AES content = " + content, var4);
        }
    }

    public static String aesDecrypt(String content, String aesKey, String charset) throws MockSecurityApiException {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(cleanBytes, charset);
        } catch (Exception var6) {
            throw new MockSecurityApiException("AES解密失败：AES content = " + content + "; charset = " + charset, var6);
        }
    }

    private static byte[] initIv(String fullAlg) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception var5) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }
}

