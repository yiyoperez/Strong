package com.strixmc.strong.proxy.commands;

import com.strixmc.strong.proxy.Locale;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import java.util.HashMap;
import java.util.Map;

public class CommandTranslation implements TranslationProvider {

  protected Map<String, String> translationsMap;

  public CommandTranslation() {
    this.translationsMap = new HashMap<>();
    translationsMap.put("argument.no-more", "No more arguments were found, size: %s position: %s");
    translationsMap.put("sender.only-player", "Only players can use this command.");
    translationsMap.put("sender.unknown", "Command sender is unknown.");
    translationsMap.put("command.subcommand.invalid", "Invalid sub command at %s ");
  }

  @Override
  public String getTranslation(Namespace namespace, String key) {
    if (key.equals("command.no-permission")) {
      return Locale.NO_PERMISSION.format();
    }
    return translationsMap.get(key);
  }
}
