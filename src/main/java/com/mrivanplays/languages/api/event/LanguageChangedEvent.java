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
package com.mrivanplays.languages.api.event;

import java.util.Locale;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Represents a event, called when the player has changed language
 */
public final class LanguageChangedEvent extends Event {

    private final Locale oldLanguage;
    private final Locale newLanguage;
    private final ProxiedPlayer player;
    private final Reason reason;

    public LanguageChangedEvent(
            Locale oldLanguage,
            Locale newLanguage,
            ProxiedPlayer player,
            Reason reason
    ) {
        this.oldLanguage = oldLanguage;
        this.newLanguage = newLanguage;
        this.player = player;
        this.reason = reason;
    }

    /**
     * Returns the language, which was the player having before the change.
     *
     * @return old language
     */
    public Locale getOldLanguage() {
        return oldLanguage;
    }

    /**
     * Returns the language, which the player have changed to.
     *
     * @return new language
     */
    public Locale getNewLanguage() {
        return newLanguage;
    }

    /**
     * Returns the player, which have changed his language.
     *
     * @return player
     */
    public ProxiedPlayer getPlayer() {
        return player;
    }

    /**
     * Returns the reason of why the player have switched languages.
     *
     * @return reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * Represents a reason of why the player have changed his language
     */
    public static enum Reason {

        /**
         * A plugin have set the player's language to a new one
         */
        PLUGIN,

        /**
         * The specified player have ran a command which changed his language.
         */
        COMMAND
    }
}
