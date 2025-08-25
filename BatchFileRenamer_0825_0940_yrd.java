// 代码生成时间: 2025-08-25 09:40:23
package com.example.batchrename;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * BatchFileRenamer application that can rename files in a directory.
 */
@QuarkusMain
public class BatchFileRenamer implements QuarkusApplication {

    @Inject
    BatchFileRenamerService batchFileRenamerService;

    @Override
    public int run(String... args) throws Exception {
        try {
            // Check if directory path is provided
            if (args.length != 1) {
                System.err.println("Please provide a directory path as an argument.");
                return 1;
            }

            // Get the directory path from arguments
            String directoryPath = args[0];

            // Check if directory exists
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("The provided path is not a valid directory.");
                return 1;
            }

            // Rename files in the directory
            batchFileRenamerService.renameFilesInDirectory(directoryPath);

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String... args) {
        new BatchFileRenamer().run(args);
    }
}

/**
 * Service class for batch renaming files.
 */
class BatchFileRenamerService {

    /**
     * Renames files in the specified directory by appending a numeric suffix.
     *
     * @param directoryPath Path of the directory containing files to rename.
     */
    public void renameFilesInDirectory(String directoryPath) {
        Path directory = Paths.get(directoryPath);
        try (Stream<Path> paths = Files.walk(directory)) {
            List<Path> files = paths
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

            int index = 1;
            for (Path file : files) {
                String fileName = file.getFileName().toString();
                String newFileName = "new_" + index++ + "_" + fileName;
                Path newFilePath = file.getParent().resolve(newFileName);
                Files.move(file, newFilePath);
                System.out.println("Renamed file: " + fileName + " to " + newFileName);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while renaming files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}