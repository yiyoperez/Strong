package com.strixmc.strong.bukkit.commands;

import com.google.inject.Inject;
import com.strixmc.common.cache.Cache;
import com.strixmc.strong.bukkit.lang.LangUtility;
import com.strixmc.strong.bukkit.utils.Utils;
import com.strixmc.strong.bukkit.utils.settings.Settings;
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

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;
  @Inject private Utils utils;
  @Inject private Settings settings;

  @Command(names = {"livestream", "live", "streaming", "stream", "directo"})
  public boolean command(@Sender Player player, @OptArg String link, @OptArg @Text String message, CommandContext context) {
    if (!player.hasPermission("strong.command.streaming")) {
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
      if (!utils.matchPattern(patternString, link)) {
        player.sendMessage(lang.getValidURL());
        return true;
      }

      TextComponent textComponent = new TextComponent(settings.isCenteredMessage() ? utils.centerMessage(lang.getClickHere()) : lang.getClickHere());
      textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getClickMessage(player.getName())).create()));
      textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

      Bukkit.getOnlinePlayers().forEach(online -> {
        lang.getHoverMessage(player.getName(), online.getName()).forEach(s -> player.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(s) : s));
        if (!message.trim().isEmpty()) {
          if (settings.isCustomMessage()) {
            player.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(message) : message);
          }
        }
        online.spigot().sendMessage(textComponent);
      });

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
