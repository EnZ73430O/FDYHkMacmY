// 代码生成时间: 2025-08-02 02:41:12
package com.example.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionDecryption {

    // Generate a secret key for encryption and decryption
    private static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 256 bits for AES Encryption
        return keyGenerator.generateKey();
    }

    // Encrypt the password using the secret key
    public static String encrypt(String password) throws Exception {
        SecretKey secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the password using the secret key
    public static String decrypt(String encryptedPassword) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        SecretKey secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            // Example usage
            String originalPassword = "mySecretPassword123";
            String encryptedPassword = encrypt(originalPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);

            String decryptedPassword = decrypt(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);

            // Check if the decrypted password matches the original password
            if (originalPassword.equals(decryptedPassword)) {
                System.out.println("Password encryption and decryption are successful.");
            } else {
                System.out.println("Password encryption and decryption failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
