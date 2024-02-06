package com.example.job.util;
import android.os.Build;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


public class FingerPrint {
    static byte[] key = "videos".getBytes(); // 替换为你的密钥
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public static String getDeviceFingerprint() {
        return Build.FINGERPRINT;
    }
    
    public static String getFinger(String tim) {
        return bytesToHex(Objects.requireNonNull(Rc4.encrypt(key, (getDeviceFingerprint()).getBytes()))) + bytesToHex(Objects.requireNonNull(Rc4.encrypt(key, (tim).getBytes())));
    }
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
