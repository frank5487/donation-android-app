package edu.upenn.cis573.project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * Convert readable password to MD5 password
     * @param password
     * @return
     * @throws Exception
     */
    public static String encodeByMd5(String password) {
        // There are SHA algorithm and MD5 algorithm in MessageDigest class in Java.
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // use MD5 algorithm, return the 16 byte value
        byte[] byteArray = md5.digest(password.getBytes());
        // MessageDigest is only able to convert String into byte[]
        return byteArrayToHexString(byteArray);
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();

        for (byte b : byteArray) {
            // convert class type, byte into String type
            String hex = byteToHexString(b);
            sb.append(hex);
        }

        return sb.toString();
    }

    private static String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public static String byteToHexString(byte b) {
        // -31 => e1, 10=>0a
        int n = b;
        if (n < 0) {
            // convert it to positive
            n = 256 + n;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1] + hex[d2];
    }

}
