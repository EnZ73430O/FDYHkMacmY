// 代码生成时间: 2025-09-10 17:21:06
package com.example.hashcalculator;

import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Singleton
public class HashCalculatorService {

    /**
     * Calculates the hash value of the given input string using the specified algorithm.
     *
     * @param input The input string to calculate the hash for.
     * @param algorithm The algorithm to use for hashing (e.g., "SHA-256", "SHA-512").
     * @return The base64 encoded hash value.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     */
    public String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        // Create a MessageDigest instance with the specified algorithm
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Update the digest using the input bytes
        digest.update(input.getBytes());

        // Calculate the hash value
        byte[] hashBytes = digest.digest();

        // Encode the hash bytes to a base64 string
        String hashValue = Base64.getEncoder().encodeToString(hashBytes);

        return hashValue;
    }
}
