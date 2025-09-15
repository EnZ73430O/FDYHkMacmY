// 代码生成时间: 2025-09-15 14:53:16
package com.example.security;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A utility class to encrypt and decrypt passwords using AES algorithm.
 * It follows Java best practices and is designed to be easily maintainable and extensible.
 */
@RegisterForReflection
public class PasswordEncryptionDecryption {

    // AES Algorithm
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * Generates a new AES key for encryption and decryption.
     *
     * @return A new AES key.
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128, new SecureRandom()); // 128 bit key size
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts a plain text password using the provided AES key.
     *
     * @param plainText The password to encrypt.
     * @param key The AES key used for encryption.
     * @return The encrypted password as a Base64 encoded string.
     * @throws Exception If encryption fails.
     */
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts an encrypted password using the provided AES key.
     *
     * @param encryptedText The Base64 encoded encrypted password to decrypt.
     * @param key The AES key used for decryption.
     * @return The decrypted plain text password.
     * @throws Exception If decryption fails.
     */
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            // Generate a new AES key
            SecretKey key = generateKey();

            // Encrypt a password
            String password = "password123";
            String encryptedPassword = encrypt(password, key);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = decrypt(encryptedPassword, key);
            System.out.println("Decrypted Password: " + decryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
