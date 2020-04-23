package com.sam.kadarairkopi.UtilityAttribute;

import com.sam.kadarairkopi.Login;
import com.sam.kadarairkopi.Register;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptsMD5 {
    public static String MD5(String stringMD5) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(stringMD5.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}
