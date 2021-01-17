package com.strixmc.strong.bukkit.commands;

import com.strixmc.common.cache.Cache;
import com.strixmc.common.utils.Cooldown;
import com.strixmc.strong.bukkit.Locale;
import com.strixmc.strong.bukkit.utils.FileCreator;
import com.strixmc.strong.bukkit.utils.Utils;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Cooldown> cooldownCache;
  @Inject @Named("Config") private FileCreator config;
  @Inject @Named("Lang") private FileCreator lang;
  @Inject private Utils utils;

  @Command(names = {"livestream", "live", "streaming", "stream", "directo"}, permission = "strong.command.streaming")
  public boolean command(@Sender Player player, @OptArg String link, @OptArg @Text String message, CommandContext context) {
    Cooldown cooldown = cooldownCache.getIfPresent(player.getUniqueId());
    if (cooldown != null && !cooldown.hasExpired()) {
      player.sendMessage(Locale.ON_COOLDOWN.format(cooldown.getTimeLeft(), cooldown.getTimerLeft()));
      return true;
    }

    if (link != null) {
      String patternString = "";
      for (String s : config.getStringList("ALLOWED_URLS")) {
        patternString = String.format("%s(%s)|", patternString, s);
      }
      if (!utils.matchPattern(patternString, link)) {
        player.sendMessage(Locale.VALID_URL.format());
        return true;
      }

      final boolean centeredMessage = config.getBoolean("CENTER_MESSAGES");

      TextComponent textComponent = new TextComponent(centeredMessage ? utils.centerMessage(Locale.CLICK_HERE.format()) : Locale.CLICK_HERE.format());
      textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Locale.CLICK_MESSAGE.format(player.getName())).create()));
      textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

      Bukkit.getOnlinePlayers().forEach(online -> Locale.HOVER_MESSAGE.formatLines(player.getName(), online.getName()).forEach(s -> {
        switch (s) {
          case "$CLICKABLE": {
            online.spigot().sendMessage(textComponent);
            break;
          }
          case "$MESSAGE": {
            if (message != null) {
              if (config.getBoolean("ALLOW_CUSTOM_MESSAGE")) {
                online.sendMessage(centeredMessage ? utils.centerMessage(message) : message);
              }
            }
            break;
          }
          default: {
            online.sendMessage(centeredMessage ? utils.centerMessage(s) : s);
            break;
          }
        }
      }));

      if (!player.hasPermission("strong.bypass.cooldown")) {
        cooldownCache.add(player.getUniqueId(), new Cooldown(config.getInt("COMMAND_COOLDOWN") * 1000L));
      }
      player.sendMessage(Locale.SUCCESS.format());
      return true;
    }

    Locale.USAGE.formatLines(context.getLabels().toArray()[0]).forEach(player::sendMessage);
    return true;
  }
}
