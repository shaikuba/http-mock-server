package cn.shaikuba.mock.common.security;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * @author Ray.Xu
 */
@Slf4j
public class KeystoreManager {

    private InputStream keystoreFile;
    private String keyStoreType;
    private char[] password;
    private String alias;

    private static final Base64.Encoder encoder = Base64.getEncoder();

    public PublicKey getPublicKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        keystore.load(keystoreFile, password);
        KeyPair keyPair = getKeyPair(keystore, alias, password);
        PublicKey publicKey = keyPair.getPublic();

        return publicKey;
    }

    public PrivateKey getPrivateKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        keystore.load(keystoreFile, password);
        KeyPair keyPair = getKeyPair(keystore, alias, password);
        PrivateKey privateKey = keyPair.getPrivate();

        return privateKey;
    }

    private KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) {
        try {
            Key privateKey = keystore.getKey(alias, password);

            Certificate cert = keystore.getCertificate(alias);
            PublicKey publicKey = cert.getPublicKey();

            if (privateKey instanceof PrivateKey) {
                return new KeyPair(publicKey, (PrivateKey) privateKey);
            }
        } catch (UnrecoverableKeyException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String exportPublicKey() throws Exception {
        return encoder.encodeToString(getPublicKey().getEncoded());
    }

    public String exportPrivateKey() throws Exception {
        return encoder.encodeToString(getPrivateKey().getEncoded());

    }


    public static void main(String args[]) throws Exception {
        KeystoreManager keystoreManager = new KeystoreManager();
        keystoreManager.keystoreFile = keystoreManager.getClass().getClassLoader().getResourceAsStream("securities/server-keystore.p12");
        keystoreManager.keyStoreType = "pkcs12";
        keystoreManager.password = "123456".toCharArray();
        keystoreManager.alias = "serverkey";
        keystoreManager.exportPublicKey();
    }
}

