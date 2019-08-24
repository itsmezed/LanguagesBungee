/*
 * Copyright 2019 Ivan Pekov (MrIvanPlays)
 * Copyright 2019 contributors

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package com.mrivanplays.languages.storage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LanguageStorage {

    private final Gson gson;
    private final File file;

    public LanguageStorage(File dataFolder) {
        file = new File(dataFolder + File.separator, "languages.json");
        createFile();
        gson = new Gson();
    }

    public String getLanguage(UUID uuid) {
        return deserialize().computeIfAbsent(uuid, uuid1 -> "en_US");
    }

    public void setLanguage(
            UUID uuid,
            String language
    ) {
        Map<UUID, String> map = new HashMap<>(deserialize());
        if (map.containsKey(uuid)) {
            map.remove(uuid);
        }
        map.put(uuid, language);
        serialize(map);
    }

    private void serialize(Map<UUID, String> map) {
        file.delete();
        createFile();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            JsonArray array = new JsonArray();
            for (Map.Entry<UUID, String> entry : map.entrySet()) {
                JsonObject object = new JsonObject();
                object.addProperty("uuid", entry.getKey().toString());
                object.addProperty("language", entry.getValue());
                array.add(object);
            }
            gson.toJson(array, writer);
        } catch (IOException ignored) {
        }
    }

    private Map<UUID, String> deserialize() {
        Map<UUID, String> map = new HashMap<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            if (array == null || array.size() == 0) {
                return map;
            }
            for (JsonElement element : array) {
                if (!element.isJsonObject()) {
                    continue;
                }
                JsonObject object = new JsonObject();
                map.put(
                        UUID.fromString(object.get("uuid").getAsString()),
                        object.get("language").getAsString()
                );
            }
        } catch (IOException ignored) {
        }
        return map;
    }

    private void createFile() {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }
}
