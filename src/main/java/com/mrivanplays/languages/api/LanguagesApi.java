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
package com.mrivanplays.languages.api;

import java.util.Locale;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Represents the main api of the plugin.
 */
public interface LanguagesApi {

    /**
     * Returns the language, set by the player for the specified plugin.
     *
     * @param player the player of which you want to get the language
     * @return language
     */
    Locale getLanguage(ProxiedPlayer player);

    /**
     * Returns the language, the player have set for his client.
     *
     * @param player the player of which you want to get the client language
     * @return client language
     */
    default Locale getClientLanguage(ProxiedPlayer player) {
        return player.getLocale();
    }

    /**
     * Sets a new language for the specified player
     *
     * @param player the player of which you want to change the language
     * @param language the new language of the player
     */
    void setLanguage(
            ProxiedPlayer player,
            Locale language
    );
}
