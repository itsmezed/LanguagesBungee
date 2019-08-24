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
package com.mrivanplays.languages;

import com.mrivanplays.languages.api.LanguagesApi;
import com.mrivanplays.languages.commands.CommandLanguage;
import com.mrivanplays.languages.storage.LanguageStorage;
import com.mrivanplays.languages.translations.TranslationFile;
import com.mrivanplays.languages.translations.Translations;
import java.io.File;
import net.md_5.bungee.api.plugin.Plugin;

public class LanguagesPlugin extends Plugin {

    private LanguagesApi api;
    private Translations translations;

    @Override
    public void onLoad() {
        LanguageStorage languageStorage = new LanguageStorage(getDataFolder());
        api = new GlobalLanguageApi(languageStorage);
    }

    @Override
    public void onEnable() {
        translations = new Translations(new File(getDataFolder() + File.separator, "translations"));
        getProxy().getPluginManager().registerCommand(this, new CommandLanguage(this));
    }

    public TranslationFile getTranslationBundle(String language) {
        return translations.getTranslationFile(language);
    }

    public LanguagesApi getApi() {
        return api;
    }
}
