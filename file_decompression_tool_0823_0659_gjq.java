// 代码生成时间: 2025-08-23 06:59:02
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

// FileDecompressionTool is a utility class for decompressing files using Quarkus
public class FileDecompressionTool {

    // Method to decompress a ZIP file to a specified directory
    public void decompressFile(String zipFilePath, String destDirectory) throws IOException {
        // Check if the input file path is valid
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new IOException("The specified zip file does not exist: " + zipFilePath);
        }

        // Ensure the destination directory exists, create it if not
        File destinationDir = new File(destDirectory);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        // Create a new ZipInputStream to read the ZIP file
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipIn.getNextEntry();
            // Iterate through the ZIP entries
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, create it
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    // Helper method to extract a file from the ZIP input stream
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            // Read from the ZIP input stream and write to the file output stream
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    public static void main(String[] args) {
        // Example usage of the FileDecompressionTool
        try {
            FileDecompressionTool tool = new FileDecompressionTool();
            tool.decompressFile("path/to/input.zip", "path/to/destination/directory");
            System.out.println("Decompression complete.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during decompression: " + e.getMessage());
        }
    }
}
