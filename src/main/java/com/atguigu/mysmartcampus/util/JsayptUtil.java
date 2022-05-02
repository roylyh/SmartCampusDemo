package com.atguigu.mysmartcampus.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

public class JsayptUtil {

    /** 加密的算法，这个算法是默认的 */
    private static final String ALGORITHM_INFO = "PBEWithMD5AndDES";
    /** 加密的密钥 */
    private static final String SECRET_KEY = "QAZWSXEDCRNGTEST";

    /**
     * 加密密码
     * @param plainText 明文字符串
     * @return
     */
    public static String getEncryptedText(String plainText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm(ALGORITHM_INFO);
        config.setPassword(SECRET_KEY);
        standardPBEStringEncryptor.setConfig(config);
        return standardPBEStringEncryptor.encrypt(plainText);
    }

    /**
     * 解密密码
     * @param encryptedText 加密串
     * @return
     */
    public static String getPlainText(String encryptedText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm(ALGORITHM_INFO);
        config.setPassword(SECRET_KEY);
        standardPBEStringEncryptor.setConfig(config);
        return standardPBEStringEncryptor.decrypt(encryptedText);
    }
}
