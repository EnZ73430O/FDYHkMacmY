// 代码生成时间: 2025-08-19 23:55:26
package com.yourdomain.testdata;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Random;
import java.util.UUID;

/**
 * Main application class for the test data generator.
 */
@QuarkusMain
public class TestDataGenerator implements QuarkusApplication {

    private static final Random random = new Random();

    /**
     * Main method to start the Quarkus application.
     * @param args command line arguments
     */
    public static void main(String... args) {
        Quarkus.run(TestDataGenerator.class, args);
    }

    /**
     * Generates test data based on the given parameters.
     * @param numberOfRecords The number of records to generate.
     * @return A list of generated test data.
     */
    @Override
    public int run(String... args) {
        try {
            // Check if the number of records is provided as an argument
            if (args.length == 0) {
                System.out.println("Please provide the number of records to generate.");
                return 1;
            }

            int numberOfRecords = Integer.parseInt(args[0]);

            // Generate and print test data
            for (int i = 0; i < numberOfRecords; i++) {
                String name = generateRandomName();
                String email = generateRandomEmail();
                int age = generateRandomAge();

                System.out.println("Name: " + name + ", Email: " + email + ", Age: " + age);
            }

            return 0; // Success
        } catch (Exception e) {
            System.err.println("Error generating test data: " + e.getMessage());
            return 1; // Failure
        }
    }

    /**
     * Generates a random name.
     * @return A randomly generated name.
     */
    private String generateRandomName() {
        String[] names = {"John", "Jane", "Bob", "Alice", "Mike", "Sarah"};
        return names[random.nextInt(names.length)];
    }

    /**
     * Generates a random email.
     * @return A randomly generated email address.
     */
    private String generateRandomEmail() {
        String domain = "@example.com";
        String prefix = UUID.randomUUID().toString().replace("-", "");
        return prefix.substring(0, 8) + domain;
    }

    /**
     * Generates a random age between 18 and 70.
     * @return A randomly generated age.
     */
    private int generateRandomAge() {
        return 18 + random.nextInt(53);
    }
}
