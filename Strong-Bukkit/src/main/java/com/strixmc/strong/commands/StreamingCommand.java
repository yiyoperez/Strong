package com.strixmc.strong.commands;

import com.google.inject.Inject;
import com.strixmc.common.cache.Cache;
import com.strixmc.strong.lang.LangUtility;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;

  @Command(names = {"livestream", "live", "streaming", "stream", "directo"})
  public boolean command(@Sender Player player, @OptArg @Text String message) {

    if (!player.hasPermission("string.command.streaming")) {
      player.sendMessage(lang.getNoPermissions());
      return true;
    }

    if (cooldownCache.find(player.getUniqueId()).isPresent() && cooldownCache.find(player.getUniqueId()).get() / 1000L >= System.currentTimeMillis()) {
      //TODO: format cooldown to string.
      player.sendMessage(lang.getCooldownActive(cooldownCache.find(player.getUniqueId()).get().toString()));
      return true;
    }

    if (!message.trim().isEmpty()) {
      Bukkit.getOnlinePlayers().forEach(online -> {
        TextComponent textComponent = new TextComponent(lang.getHoverMessage(player.getName()));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getClickMessage(player.getName())).create()));
        player.spigot().sendMessage(textComponent);
        online.sendMessage(message);
      });
    } else {
      Bukkit.getOnlinePlayers().forEach(online -> online.sendMessage(message));
      player.sendMessage(lang.getSuccessfully());
    }

    if (!player.hasPermission("strong.bypass.cooldown")) {
      cooldownCache.add(player.getUniqueId(), System.currentTimeMillis() + 5 * 1000L);
    }
    return true;
  }
}
