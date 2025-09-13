// 代码生成时间: 2025-09-14 03:35:22
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

@QuarkusMain
public class LogParser implements QuarkusApplication {

    private static final Logger LOGGER = Logger.getLogger(LogParser.class.getName());
    private static final String LOG_PATTERN = "\[(.*?)\] (.*?): (.*)"; // 日志文件的正则表达式模式
    private static final Pattern PATTERN = Pattern.compile(LOG_PATTERN);

    @Override
    public int run(String... args) {
        if (args.length < 1) {
            LOGGER.severe("No log file path provided.");
            return 1;
        }

        String logFilePath = args[0];
        parseLogFile(logFilePath);
        return 0;
    }

    private void parseLogFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    String timestamp = matcher.group(1);
                    String level = matcher.group(2);
                    String message = matcher.group(3);
                    LOGGER.info("Timestamp: " + timestamp + ", Level: " + level + ", Message: " + message);
                } else {
                    LOGGER.warning("Failed to parse line: " + line);
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error occurred while reading log file: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        LogParser parser = new LogParser();
        parser.run(args);
    }

}
