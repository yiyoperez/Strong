package com.strixmc.strong.commands;

import com.google.inject.Inject;
import com.strixmc.common.cache.Cache;
import com.strixmc.strong.lang.LangUtility;
import com.strixmc.strong.utils.Utils;
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

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;
  @Inject private Utils utils;

  @Command(names = {"livestream", "live", "streaming", "stream", "directo"})
  public boolean command(@Sender Player player, @OptArg String link, @OptArg @Text String message, CommandContext context) {

    if (!player.hasPermission("string.command.streaming")) {
      player.sendMessage(lang.getNoPermissions());
      return true;
    }

    if (cooldownCache.find(player.getUniqueId()).isPresent() && cooldownCache.find(player.getUniqueId()).get() / 1000L >= System.currentTimeMillis()) {
      //TODO: format cooldown to string.
      player.sendMessage(lang.getCooldownActive(cooldownCache.find(player.getUniqueId()).get().toString()));
      return true;
    }

    if (link != null) {

      String patternString = "";
      //TODO ADD THEM FROM CONFIG.
      for (String s : Arrays.asList("youtube.com", "youtu.be", "twitch.tv")) {
        patternString = String.format("%s(%s)|", patternString, s);
      }

      final Pattern pattern = Pattern.compile("(?i)(" + patternString + "(?!x)x)");
      final Matcher matcher = pattern.matcher(link);

      if (!matcher.find()) {
        player.sendMessage(lang.getValidURL());
        return true;
      }

      TextComponent textComponent = new TextComponent(utils.centerMessage(lang.getHoverMessage(player.getName())));
      textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getClickMessage(player.getName())).create()));
      textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

      if (!message.trim().isEmpty()) {
        Bukkit.getOnlinePlayers().forEach(online -> {
          online.spigot().sendMessage(textComponent);
          utils.sendCenteredMessage(online, message);
        });
      } else {
        Bukkit.getOnlinePlayers().forEach(online -> online.spigot().sendMessage(textComponent));
        player.sendMessage(lang.getSuccessfully());
      }

      if (!player.hasPermission("strong.bypass.cooldown")) {
        cooldownCache.add(player.getUniqueId(), System.currentTimeMillis() + 5 * 1000L);
      }
      return true;
    }

    player.sendMessage(lang.getUsage().replace("<command>", context.getCommand().getName()));
    return true;
  }
}
