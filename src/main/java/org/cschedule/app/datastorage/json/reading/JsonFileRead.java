package org.cschedule.app.datastorage.json.reading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cschedule.app.datastorage.json.Json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mac on 11.02.17.
 */
public class JsonFileRead implements Json {
    private String jsonFileName;
    private final Logger LL = LoggerFactory.getLogger(JsonFileRead.class);

    public JsonFileRead(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public String readJson() {
        Path courseFile = Paths.get("src/main/resources/", jsonFileName + ".json");
        String json = "";
        try {
            json = new String(Files.readAllBytes(courseFile));
        } catch (IOException e) {
            LL.error("Could not read file", e);
            e.printStackTrace();
        }
        return json;
    }
}
