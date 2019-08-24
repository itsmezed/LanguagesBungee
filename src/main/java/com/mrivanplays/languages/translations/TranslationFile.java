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

import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class TranslationFile {

    private final JsonObject object;

    public TranslationFile(JsonObject object) {
        this.object = object;
    }

    public BaseComponent[] getTranslation(String path) {
        return toComponent(object.get(path).getAsString());
    }

    public BaseComponent[] getTranslation(
            String path,
            String... replacements
    ) {
        String message = object.get(path).getAsString();
        for (int i = 0; i < replacements.length; i++) {
            String toReplace = replacements[i];
            if (i + 1 > replacements.length) {
                continue;
            }
            String replacement = replacements[i + 1];
            message = message.replace(toReplace, replacement);
        }
        return toComponent(message);
    }

    private BaseComponent[] toComponent(String message) {
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes(
                '&',
                message
        ));
    }
}
