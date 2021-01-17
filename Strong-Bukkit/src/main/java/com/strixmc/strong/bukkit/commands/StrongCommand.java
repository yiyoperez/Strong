package com.strixmc.strong.bukkit.commands;

import com.strixmc.strong.bukkit.Locale;
import com.strixmc.strong.bukkit.utils.FileCreator;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = "strong", permission = "strong.command.reload")
public class StrongCommand implements CommandClass {

  @Inject @Named("Config") private FileCreator config;
  @Inject @Named("Lang") private FileCreator lang;

  @Command(names = "reload")
  public boolean command(CommandSender sender) {
    config.reload();
    config.save();
    lang.reload();
    lang.save();

    sender.sendMessage(Locale.SUCCESS_RELOAD.format());
    return true;
  }
}
