package readers;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader;

public class JsonReader {
    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);
    private static final String test_data_path = "src/test/java/testData/";
    private String jsonReader;

    public JsonReader(String jsonFileName) {
        try {
            JSONObject data = (JSONObject)new JSONParser().parse(new FileReader(test_data_path + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            Log.error("Exception in JsonReader constructor " ,jsonFileName, e.getMessage());
            jsonReader = "{}";
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            Log.error("Exception in JsonReader getJsonData " ,jsonPath, e.getMessage());
            return "";
        }
    }
}