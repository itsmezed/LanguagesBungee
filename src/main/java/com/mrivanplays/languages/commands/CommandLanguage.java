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
package com.mrivanplays.languages.commands;

import com.mrivanplays.languages.GlobalLanguageApi;
import com.mrivanplays.languages.LanguagesPlugin;
import com.mrivanplays.languages.api.Languages;
import com.mrivanplays.languages.api.event.LanguageChangedEvent;
import com.mrivanplays.languages.translations.TranslationFile;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandLanguage extends Command implements TabExecutor {

    private final LanguagesPlugin plugin;

    public CommandLanguage(LanguagesPlugin plugin) {
        super("language");
        this.plugin = plugin;
    }

    @Override
    public void execute(
            CommandSender sender,
            String[] args
    ) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatColor.YELLOW + "Console may not change its default language (en_US)");
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        Locale language = plugin.getApi().getLanguage(player);
        TranslationFile translationProvider = plugin.getTranslationBundle(language.getLanguage());
        if (translationProvider == null) {
            // we still don't have translations to all languages!
            translationProvider = plugin.getTranslationBundle("en");
        }
        GlobalLanguageApi apiImpl = (GlobalLanguageApi) plugin.getApi();
        if (args.length == 0) {
            player.sendMessage(translationProvider.getTranslation(
                    "player.currentLanguage",
                    "{language}",
                    language.toString()
            ));
            return;
        }
        if (args.length == 1) {
            Optional<Locale> changeTo = apiImpl.getLocale(args[0]);
            if (!changeTo.isPresent()) {
                player.sendMessage(translationProvider.getTranslation("invalidLanguage"));
                return;
            }
            Locale newLanguage = changeTo.get();
            LanguageChangedEvent event = new LanguageChangedEvent(
                    language,
                    newLanguage,
                    player,
                    LanguageChangedEvent.Reason.COMMAND
            );
            apiImpl.setLanguage0(player, newLanguage);
            plugin.getProxy().getPluginManager().callEvent(event);
            player.sendMessage(translationProvider.getTranslation("player.changedLanguageTo"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(
            CommandSender sender,
            String[] args
    ) {
        if (args.length == 1) {
            return Languages.getKnownLanguages().stream()
                    .map(Locale::toString)
                    .filter(locale -> locale.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
