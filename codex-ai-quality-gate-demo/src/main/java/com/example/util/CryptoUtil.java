package com.example.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtil {
    // INTENTIONAL SECURITY BUG: hardcoded key + ECB mode
    private static final byte[] KEY = "1234567890abcdef".getBytes();

    public static String encrypt(String input) throws Exception {
        SecretKeySpec ks = new SecretKeySpec(KEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, ks);
        byte[] encrypted = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
