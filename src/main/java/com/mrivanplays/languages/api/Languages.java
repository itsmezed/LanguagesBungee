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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a helper class for a languages, which are
 * not present into the java {@link Locale} class.
 */
public final class Languages {

    public static Locale BULGARIAN = create("bg", "BGR");
    public static Locale SERBIAN = create("sr", "RS");
    public static Locale RUSSIAN = create("ru", "RU");
    public static Locale POLISH = create("pl", "PL");
    public static Locale GREEK = create("el", "GR");
    public static Locale TURKISH = create("tr", "TR"); // (remove kebab)
    public static Locale ROMANIAN = create("ro", "RO");
    public static Locale ESTONIAN = create("et", "EE");
    public static Locale LATVIAN = create("lv", "LV");
    public static Locale LITHUANIAN = create("lt", "LT");
    public static Locale ALBANIAN = create("sq", "AL");

    /**
     * Returns all known languages
     *
     * @return languages
     */
    public static Set<Locale> getKnownLanguages() {
        Set<Locale> set = new HashSet<>(Arrays.asList(
                BULGARIAN,
                SERBIAN,
                RUSSIAN,
                POLISH,
                GREEK,
                TURKISH,
                ROMANIAN,
                ESTONIAN,
                LATVIAN,
                LITHUANIAN,
                ALBANIAN
        ));
        set.addAll(Arrays.asList(Locale.getAvailableLocales()));
        return set;
    }

    // helper method for creating languages
    private static Locale create(
            String language,
            String country
    ) {
        return new Locale.Builder().setRegion(country).setLanguage(language).build();
    }
}
