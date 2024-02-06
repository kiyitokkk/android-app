package com.example.job.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Rc4 {
    public static byte[] encrypt(byte[] key, byte[] data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "RC4");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] key, byte[] data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "RC4");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
