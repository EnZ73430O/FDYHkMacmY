// 代码生成时间: 2025-08-21 06:48:57
package com.example.imageresizer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageResizer implements QuarkusApplication {

    private static final String INPUT_DIR = "/input";
    private static final String OUTPUT_DIR = "/output";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String... args) {
        Quarkus.run(ImageResizer.class, args);
    }

    @Override
    public int run(String... strings) {
        resizeImages();
        return 0;
    }

    private void resizeImages() {
        try {
            File inputDir = new File(INPUT_DIR);
            File outputDir = new File(OUTPUT_DIR);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            List<File> files = listFilesInDirectory(inputDir.toPath());
            for (File file : files) {
                resizeImage(file, outputDir);
            }
        } catch (IOException e) {
            System.err.println("Error resizing images: " + e.getMessage());
        }
    }

    private List<File> listFilesInDirectory(Path path) throws IOException {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        }
    }

    private void resizeImage(File inputFile, File outputDir) {
        try {
            BufferedImage image = ImageIO.read(inputFile);
            if (image == null) {
                throw new IOException("Unsupported file format for file: " + inputFile.getName());
            }

            BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            resizedImage.getGraphics().drawImage(image.getScaledInstance(WIDTH, HEIGHT, BufferedImage.SCALE_SMOOTH), 0, 0, null);

            File outputFile = new File(outputDir, inputFile.getName());
            ImageIO.write(resizedImage, "jpg", outputFile);
        } catch (IOException e) {
            System.err.println("Error resizing image: " + inputFile.getName() + " - " + e.getMessage());
        }
    }
}