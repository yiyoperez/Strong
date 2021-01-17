package com.strixmc.strong.proxy.commands;

import com.strixmc.strong.proxy.Locale;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.utils.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import net.md_5.bungee.api.CommandSender;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = "strong", permission = "strong.command")
public class StrongCommand implements CommandClass {

  @Inject private Strong main;
  @Inject @Named("Config") private FileManager config;
  @Inject @Named("Lang") private FileManager lang;


  @Command(names = "reload", permission = "strong.command.reload")
  public boolean execute(CommandSender sender) {
    lang.reloadFile();
    lang.saveFile();
    config.reloadFile();
    config.saveFile();

    sender.sendMessage(Locale.SUCCESS_RELOAD.format());
    return true;
  }
}
