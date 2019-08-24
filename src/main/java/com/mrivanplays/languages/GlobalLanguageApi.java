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

import com.mrivanplays.languages.api.Languages;
import com.mrivanplays.languages.api.LanguagesApi;
import com.mrivanplays.languages.api.event.LanguageChangedEvent;
import com.mrivanplays.languages.storage.LanguageStorage;
import java.util.Locale;
import java.util.Optional;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalLanguageApi implements LanguagesApi {

    private final LanguageStorage storage;

    public GlobalLanguageApi(LanguageStorage storage) {
        this.storage = storage;
    }

    @Override
    public Locale getLanguage(ProxiedPlayer player) {
        return getLocale(storage.getLanguage(player.getUniqueId())).get();
    }

    @Override
    public void setLanguage(
            ProxiedPlayer player,
            Locale language
    ) {
        LanguageChangedEvent event = new LanguageChangedEvent(
                getLanguage(player),
                language,
                player,
                LanguageChangedEvent.Reason.PLUGIN
        );
        setLanguage0(player, language);
        ProxyServer.getInstance().getPluginManager().callEvent(event);
    }

    public void setLanguage0(
            ProxiedPlayer player,
            Locale language
    ) {
        storage.setLanguage(player.getUniqueId(), language.toString());
    }

    public Optional<Locale> getLocale(String s) {
        return Languages.getKnownLanguages().stream()
                .filter(language -> language.toString().equalsIgnoreCase(s))
                .findFirst();
    }
}
