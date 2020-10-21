package com.strixmc.strong.bukkit.commands;

import com.google.inject.Inject;
import com.strixmc.strong.bukkit.lang.LangUtility;
import com.strixmc.strong.bukkit.Strong;
import com.strixmc.strong.bukkit.utils.ConfigUpdater;
import com.strixmc.strong.bukkit.utils.settings.Settings;
import lombok.SneakyThrows;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;

@Command(names = "strong", permission = "strong.command.reload")
public class StrongCommand implements CommandClass {

  @Inject private Strong main;
  @Inject private LangUtility lang;
  @Inject private Settings settings;

  @SneakyThrows
  @Command(names = "reload")
  public boolean command(@Sender Player player) {
    lang.updateMessages();
    main.reloadConfig();
    main.saveConfig();
    ConfigUpdater.update(main, "config.yml", new File(main.getDataFolder().getAbsolutePath(), "config.yml"), Arrays.asList("ALLOWED_URLS"));
    main.reloadConfig();
    settings.updateSettings();

    player.sendMessage(lang.getSuccessfullyReload());
    return true;
  }
}
