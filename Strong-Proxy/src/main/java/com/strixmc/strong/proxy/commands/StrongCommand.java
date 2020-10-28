package com.strixmc.strong.proxy.commands;

import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.lang.LangUtility;
import com.strixmc.strong.proxy.utils.settings.Settings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import javax.inject.Inject;

public class StrongCommand extends Command {

  @Inject private Strong main;
  @Inject private LangUtility lang;
  @Inject private Settings settings;

  public StrongCommand() {
    super("strong");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) return;

    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!player.hasPermission("strong.command.reload")) {
      lang.getNoPermissions();
      return;
    }

    if (args.length != 0) {
      if (args[0].equalsIgnoreCase("reload")) {
        lang.updateMessages();
        main.getConfig().reloadFile();
        main.getConfig().saveFile();
        main.getConfig().reloadFile();
        settings.updateSettings();

        player.sendMessage(lang.getSuccessfullyReload());
      }
    } else {
      player.sendMessage("Usage: /" + getName() + " reload");
    }
  }
}
