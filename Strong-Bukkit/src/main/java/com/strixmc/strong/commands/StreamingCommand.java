package com.strixmc.strong.commands;

import com.google.inject.Inject;
import com.strixmc.common.cache.Cache;
import com.strixmc.strong.lang.LangUtility;
import com.strixmc.strong.utils.Utils;
import com.strixmc.strong.utils.settings.Settings;
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

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;
  @Inject private Utils utils;
  @Inject private Settings settings;

  @Command(names = {"livestream", "live", "streaming", "stream", "directo"})
  public boolean command(@Sender Player player, @OptArg String link, @OptArg @Text String message, CommandContext context) {

    if (!player.hasPermission("string.command.streaming")) {
      player.sendMessage(lang.getNoPermissions());
      return true;
    }

    if (cooldownCache.find(player.getUniqueId()).isPresent() && cooldownCache.find(player.getUniqueId()).get() >= System.currentTimeMillis()) {
      player.sendMessage(lang.getCooldownActive(utils.millisToRoundedTime(cooldownCache.find(player.getUniqueId()).get() - System.currentTimeMillis())));
      return true;
    }

    if (link != null) {

      String patternString = "";
      for (String s : settings.getAllowedURLS()) {
        patternString = String.format("%s(%s)|", patternString, s);
      }

      final Pattern pattern = Pattern.compile("(?i)(" + patternString + "(?!x)x)");
      final Matcher matcher = pattern.matcher(link);

      if (!matcher.find()) {
        player.sendMessage(lang.getValidURL());
        return true;
      }

      TextComponent textComponent = new TextComponent(settings.isCenteredMessage() ? utils.centerMessage(lang.getClickHere()) : lang.getClickHere());
      textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getClickMessage(player.getName())).create()));
      textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

      if (!message.trim().isEmpty()) {
        Bukkit.getOnlinePlayers().forEach(online -> {
          lang.getHoverMessage(player.getName(), online.getName()).forEach(s -> {
            if (settings.isCenteredMessage()) {
              utils.sendCenteredMessage(online, s);
            } else {
              player.sendMessage(s);
            }
          });
          if (settings.isCenteredMessage()) {
            utils.sendCenteredMessage(online, message);
          } else {
            player.sendMessage(message);
          }
          online.spigot().sendMessage(textComponent);
        });
      } else {
        Bukkit.getOnlinePlayers().forEach(online -> {
          lang.getHoverMessage(player.getName(), online.getName()).forEach(s -> {
            if (settings.isCenteredMessage()) {
              utils.sendCenteredMessage(online, s);
            } else {
              player.sendMessage(s);
            }
          });
          online.spigot().sendMessage(textComponent);
        });
      }

      if (!player.hasPermission("strong.bypass.cooldown")) {
        cooldownCache.add(player.getUniqueId(), System.currentTimeMillis() + settings.getCooldownInterval() * 1000L);
      }
      player.sendMessage(lang.getSuccessfully());
      return true;
    }

    String label = String.join(" ", context.getLabels());
    player.sendMessage(lang.getUsage().replace("<command>", label));
    return true;
  }
}
