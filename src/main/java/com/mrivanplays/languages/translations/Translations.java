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
package com.mrivanplays.languages.translations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Translations {

    private final List<String> supportedTranslations = Arrays.asList("en", "bg");
    private final Map<String, TranslationFile> filecache = new ConcurrentHashMap<>();

    public Translations(File dataFolder) {
        Map<String, File> files = createFiles(dataFolder);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (Map.Entry<String, File> entry : files.entrySet()) {
            try (Reader reader = new InputStreamReader(new FileInputStream(entry.getValue()))) {
                filecache.put(entry.getKey(), new TranslationFile(gson.fromJson(reader, JsonObject.class)));
            } catch (IOException ignored) {
            }
        }
        files.clear();
    }


    public TranslationFile getTranslationFile(String language) {
        return filecache.get(language);
    }

    private Map<String, File> createFiles(File dataFolder) {
        Map<String, File> map = new HashMap<>();
        for (String language : supportedTranslations) {
            File file = new File(dataFolder + File.separator, "translations_" + language + ".json");
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try (
                        InputStream in =
                                getClass().getClassLoader().getResourceAsStream("translations_" + language + ".json")
                ) {
                    Files.copy(in, file.getAbsoluteFile().toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.put(language, file);
        }
        return map;
    }
}
