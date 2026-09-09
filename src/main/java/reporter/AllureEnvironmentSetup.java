package reporter;
import readers.Log;
import readers.PropertyReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllureEnvironmentSetup {
    public static void setAllureEnvironment() {
        Map<String, String> env = new HashMap<>();
        env.put("OS", PropertyReader.getProperty("os.name"));
        env.put("Java version:", PropertyReader.getProperty("java.runtime.version"));
        env.put("Browser", PropertyReader.getProperty("browserType"));
        env.put("Execution Type", PropertyReader.getProperty("executionType"));
        env.put("URL", PropertyReader.getProperty("baseUrlWeb"));
        File envFile = new File(PropertyReader.getProperty("user.dir") + "/test-output" + "/allure-results/environment.properties");
        envFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(envFile)) {

            for (Map.Entry<String, String> entry : env.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }

            Log.info("Environment properties file created: " + envFile);
        } catch (IOException e) {
            Log.error("Environment properties file created: " + e.getMessage());
        }
    }
}