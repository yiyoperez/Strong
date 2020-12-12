package com.strixmc.strong.proxy.commands;

import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.lang.LangUtility;
import com.strixmc.strong.proxy.utils.settings.Settings;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;

@Command(names = "strong")
public class StrongCommand implements CommandClass {

  @Inject private Strong main;
  @Inject private LangUtility lang;
  @Inject private Settings settings;

  @Command(names = "")
  public boolean command(CommandSender sender, CommandContext context) {
    String label = (String) context.getLabels().toArray()[0];
    sender.sendMessage("Usage: /" + label + " reload");
    return true;
  }

  @Command(names = "reload")
  public boolean execute(CommandSender sender) {

    if (sender instanceof ProxiedPlayer) {
      ProxiedPlayer player = (ProxiedPlayer) sender;
      if (!player.hasPermission("strong.command.reload")) {
        lang.getNoPermissions();
        return true;
      }
    }

    lang.updateMessages();
    main.getConfig().reloadFile();
    main.getConfig().saveFile();
    main.getConfig().reloadFile();
    settings.updateSettings();

    sender.sendMessage(lang.getSuccessfullyReload());
    return true;
  }
}
