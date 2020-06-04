package cn.shaikuba.mock.common.security;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class RSA {
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final int LINE_SIZE = 65;
    private static final String NEW_LINE_STR = System.getProperty("line.separator");
    private static final byte[] NEW_LINE_BYTES;
    private static final byte[] START_PUBLIC_KEY_BYTES;
    private static final byte[] END_PUBLIC_KEY_BYTES;
    private static final byte[] START_PRIVATE_KEY_BYTES;
    private static final byte[] END_PRIVATE_KEY_BYTES;

    static {
        NEW_LINE_BYTES = NEW_LINE_STR.getBytes();
        START_PUBLIC_KEY_BYTES = "##############start public key##############".getBytes();
        END_PUBLIC_KEY_BYTES = "##############end public key##############".getBytes();
        START_PRIVATE_KEY_BYTES = "##############start private key##############".getBytes();
        END_PRIVATE_KEY_BYTES = "##############end private key##############".getBytes();
    }

    /***********************************
     * Generate keypair
     ***********************************/
    public static KeyPair generateKeyPair() throws Exception {
        return generateKeyPair(1024);
    }

    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        return keyPairGenerator.generateKeyPair();
    }

    /***********************************
     * Save keys
     ***********************************/
    public static void generateRsa(OutputStream privateKey, OutputStream publicKey) throws Exception {
        generateRsa(privateKey, publicKey, null);
    }

    public static void generateRsa(OutputStream priKeyOutputStream, OutputStream pubKeyOutputStream, String password) throws Exception {
        saveKeys(priKeyOutputStream, pubKeyOutputStream, generateKeyPair(), password);
    }

    public static void genRsaWithoutNewline(int keySize, OutputStream priKeyOutputStream, OutputStream pubKeyOutputStream) throws Exception {
        genRsaWithoutNewline(keySize, priKeyOutputStream, pubKeyOutputStream, null);
    }

    public static void genRsaWithoutNewline(int keySize, OutputStream priKeyOutputStream, OutputStream pubKeyOutputStream, String password) throws Exception {
        KeyPair kp = generateKeyPair(keySize);
        savePrivateKeyWithoutNewline(priKeyOutputStream, kp.getPrivate().getEncoded(), password);
        savePublicKeyWithoutNewline(pubKeyOutputStream, kp.getPublic().getEncoded());
    }

    protected static void saveKeys(OutputStream priKeyOutputStream, OutputStream pubKeyOutputStream, KeyPair kp) throws Exception {
        saveKeys(priKeyOutputStream, pubKeyOutputStream, kp, (String)null);
    }

    protected static void saveKeys(OutputStream priKeyOutputStream, OutputStream pubKeyOutputStream, KeyPair kp, String password) throws Exception {
        savePrivateKey(priKeyOutputStream, kp.getPrivate().getEncoded(), password);
        savePublicKey(pubKeyOutputStream, kp.getPublic().getEncoded());
    }

    protected static void savePublicKey(OutputStream pubKeyOutputStream, byte[] pubKeyBytes) throws Exception {
        try {
            pubKeyOutputStream.write(getTitle().getBytes("UTF-8"));
            pubKeyOutputStream.write(NEW_LINE_BYTES);
            pubKeyOutputStream.write(START_PUBLIC_KEY_BYTES);
            pubKeyOutputStream.write(NEW_LINE_BYTES);
            saveBase64KeyToStream(Base64.getEncoder().encode(pubKeyBytes), pubKeyOutputStream);
            pubKeyOutputStream.write(END_PUBLIC_KEY_BYTES);
        } finally {
            pubKeyOutputStream.flush();
            pubKeyOutputStream.close();
        }

    }

    protected static void savePrivateKey(OutputStream priKeyOutputStream, byte[] priKeyBytes, String password) throws Exception {
        try {
            if (password != null) {
                priKeyBytes = AES.aesEncrypt(priKeyBytes, password);
            }

            priKeyOutputStream.write(getTitle().getBytes("UTF-8"));
            priKeyOutputStream.write(NEW_LINE_BYTES);
            priKeyOutputStream.write(START_PRIVATE_KEY_BYTES);
            priKeyOutputStream.write(NEW_LINE_BYTES);
            saveBase64KeyToStream(Base64.getEncoder().encode(priKeyBytes), priKeyOutputStream);
            priKeyOutputStream.write(END_PRIVATE_KEY_BYTES);
        } finally {
            priKeyOutputStream.flush();
            priKeyOutputStream.close();
        }

    }

    protected static void savePublicKeyWithoutNewline(OutputStream pubKeyOutputStream, byte[] pubKeyBytes) throws Exception {
        try {
            pubKeyOutputStream.write(Base64.getEncoder().encode(pubKeyBytes));
        } finally {
            pubKeyOutputStream.flush();
            pubKeyOutputStream.close();
        }

    }

    protected static void savePrivateKeyWithoutNewline(OutputStream priKeyOutputStream, byte[] priKeyBytes, String password) throws Exception {
        try {
            if (password != null) {
                priKeyBytes = AES.aesEncrypt(priKeyBytes, password);
            }
            priKeyOutputStream.write(Base64.getEncoder().encode(priKeyBytes));
        } finally {
            priKeyOutputStream.flush();
            priKeyOutputStream.close();
        }

    }

    private static void saveBase64KeyToStream(byte[] base64Bytes, OutputStream out) throws IOException {
        int start = 0;

        int len;
        for(; start < base64Bytes.length; start += len) {
            len = Math.min(65, base64Bytes.length - start);
            out.write(base64Bytes, start, len);
            out.write(NEW_LINE_BYTES);
        }

    }

    /***********************************
     * Load public & private key
     ***********************************/
    public static byte[] loadPublicKey(String filePath) throws Exception {
        return loadPublicKey(new FileInputStream(filePath));
    }

    public static byte[] loadPublicKey(InputStream in) throws Exception {
        return loadKeyFromStream(in, RSA.KeyType.PUBLIC_KEY);
    }

    public static byte[] loadPrivateKey(InputStream in, String password) throws Exception {
        return AES.aesDecrypt(loadPrivateKey(in), password);
    }

    public static byte[] loadPrivateKey(InputStream in) throws Exception {
        return loadKeyFromStream(in, RSA.KeyType.PRIVATE_KEY);
    }

    private static byte[] loadKeyFromStream(InputStream in, RSA.KeyType keyType) throws Exception {
        return Base64.getDecoder().decode(loadApiKeyFromStream(in, keyType).trim());
    }

    private static String loadApiKeyFromStream(InputStream in, RSA.KeyType keyType) throws Exception {
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader rb = new BufferedReader(isr);
        StringBuffer privateKey = new StringBuffer();
        String line = null;
        boolean firstLine = true;
        boolean containsDesc = false;
        boolean start = false;

        while((line = rb.readLine()) != null) {
            if (firstLine) {
                if (line.startsWith("key generate algorithm: ")) {
                    containsDesc = true;
                }

                firstLine = false;
            }

            if (containsDesc && !start) {
                if (keyType.equals(RSA.KeyType.PRIVATE_KEY) && line.equals(new String(START_PRIVATE_KEY_BYTES))) {
                    start = true;
                } else if (keyType.equals(RSA.KeyType.PUBLIC_KEY) && line.equals(new String(START_PUBLIC_KEY_BYTES))) {
                    start = true;
                }
            } else {
                if (start && (keyType.equals(RSA.KeyType.PRIVATE_KEY) && line.equals(new String(END_PRIVATE_KEY_BYTES)) || keyType.equals(RSA.KeyType.PUBLIC_KEY) && line.equals(new String(END_PUBLIC_KEY_BYTES)))) {
                    break;
                }

                privateKey.append(line);
            }
        }

        return privateKey.toString().trim();
    }


    protected static String getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append("key generate algorithm: ");
        sb.append("RSA");
        sb.append(NEW_LINE_STR);
        sb.append("key size: ");
        sb.append(1024);
        return sb.toString();
    }

    public enum KeyType {
        PUBLIC_KEY("public key"),
        PRIVATE_KEY("private key");

        String type;

        KeyType(String type) {
            this.type = type;
        }
    }
}

