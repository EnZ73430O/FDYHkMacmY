// 代码生成时间: 2025-08-09 19:05:14
import io.quarkus.runtime.Quarkus;
    import io.quarkus.runtime.QuarkusApplication;
    import io.quarkus.runtime.annotations.QuarkusMain;
    import picocli.CommandLine;
    import picocli.CommandLine.Command;
    import picocli.CommandLine.Option;
    import picocli.CommandLine.Parameters;
    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;
    import java.util.stream.Stream;
    
    /**
     * A Quarkus application that provides a command-line interface for bulk file renaming.
     */
    @QuarkusMain
    @Command(name = 