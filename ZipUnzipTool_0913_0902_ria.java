// 代码生成时间: 2025-09-13 09:02:19
package com.example.ziptool;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides functionality to unzip files.
 */
@QuarkusMain
public class ZipUnzipTool implements QuarkusApplication {

    @Inject
    // The file path to the zip file to be unzipped.
    private String zipFilePath;

    @Inject
    // The directory path where the files will be unzipped.
    private String outputDirectory;

    /**
     * Unzips the file at zipFilePath to the directory specified by outputDirectory.
     * @return The result of the unzip operation.
     */
    public int run(String... args) {
        try {
            // Check if the zip file exists.
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) {
                throw new IOException("The zip file does not exist: " + zipFilePath);
            }
            // Create the output directory if it does not exist.
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            // Open the zip file.
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry zipEntry = zis.getNextEntry();
                // Process each entry.
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File newFile = new File(outputDir, fileName);
                    // Create directories for sub directories in zip.
                    if (zipEntry.isDirectory()) {
                        newFile.mkdirs();
                    } else {
                        // Create all non-existent folders.
                        File parent = newFile.getParentFile();
                        if (parent != null && !parent.exists()) {
                            parent.mkdirs();
                        }
                        // Write file content.
                        try (OutputStream fos = new FileOutputStream(newFile)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new IOException("Failed to unzip the file: " + zipFilePath, e);
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    // Main method for testing purposes.
    public static void main(String[] args) {
        ZipUnzipTool zipUnzipTool = new ZipUnzipTool();
        // For demonstration purposes, replace with actual file path and directory.
        zipUnzipTool.zipFilePath = "path/to/zipfile.zip";
        zipUnzipTool.outputDirectory = "path/to/output/directory";
        zipUnzipTool.run();
    }
}
