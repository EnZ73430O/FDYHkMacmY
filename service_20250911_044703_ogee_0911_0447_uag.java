// 代码生成时间: 2025-09-11 04:47:03
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FolderStructureOrganizer is a Java application using Quarkus framework to organize folder structures.
 * It provides functionality to move files from a source folder to a destination folder based on file extensions.
 */
@QuarkusMain
@Command(name = "folderStructureOrganizer", mixinStandardHelpOptions = true, version = "Folder Structure Organizer 1.0",
         description = "%nThis application organizes the folder structure by moving files based on their extensions.
%n")
public class FolderStructureOrganizer implements QuarkusApplication {

    @Parameters(index = "0", description = "The source directory path.")
    private String sourceDirectory;

    @Option(names = {"-d", "--destination"}, description = "The destination directory path.")
    private String destinationDirectory;

    @Option(names = {"-e", "--extensions"}, description = "Comma separated file extensions to be moved.")
    private String extensions;

    @Override
    public int run(String... args) throws Exception {
        // Parse the command line arguments
        new CommandLine(this).parseArgs(args);

        // Validate the source and destination directories
        File source = new File(sourceDirectory);
        File destination = new File(destinationDirectory);
        if (!source.exists() || !source.isDirectory()) {
            System.err.println("Source directory does not exist or is not a directory: " + sourceDirectory);
            return CommandLine.ExitCode.SOFTWARE;
        }
        if (!destination.exists() || !destination.isDirectory()) {
            System.err.println("Destination directory does not exist or is not a directory: " + destinationDirectory);
            return CommandLine.ExitCode.SOFTWARE;
        }

        // Move files based on the specified extensions
        try {
            moveFilesByExtensions(source, destination, extensions);
        } catch (IOException e) {
            System.err.println("Error while moving files: " + e.getMessage());
            return CommandLine.ExitCode.SOFTWARE;
        }

        return CommandLine.ExitCode.OK;
    }

    /**
     * Moves files from the source directory to the destination directory based on the provided file extensions.
     *
     * @param source The source directory.
     * @param destination The destination directory.
     * @param extensions Comma separated file extensions.
     * @throws IOException If an I/O error occurs.
     */
    private void moveFilesByExtensions(File source, File destination, String extensions) throws IOException {
        String[] extArray = extensions.split(",");
        Path sourcePath = source.toPath();
        Path destinationPath = destination.toPath();

        try (Stream<Path> paths = Files.walk(sourcePath)) {
            paths.filter(Files::isRegularFile)
                .forEach(file -> {
                    String fileName = file.getFileName().toString();
                    for (String ext : extArray) {
                        if (fileName.endsWith(ext)) {
                            Path targetPath = destinationPath.resolve(sourcePath.relativize(file));
                            try {
                                Files.move(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                System.err.println("Error moving file: " + file + " to " + targetPath + " - " + e.getMessage());
                            }
                        }
                    }
                });
        }
    }

    /**
     * Entry point for the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String... args) {
        FolderStructureOrganizer app = new FolderStructureOrganizer();
        int exitCode = new CommandLine(app).execute(args);
        System.exit(exitCode);
    }
}
