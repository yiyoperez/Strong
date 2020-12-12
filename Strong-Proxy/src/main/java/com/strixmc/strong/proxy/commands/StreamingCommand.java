package com.strixmc.strong.proxy.commands;

import com.strixmc.common.cache.Cache;
import com.strixmc.strong.proxy.lang.LangUtility;
import com.strixmc.strong.proxy.utils.Utils;
import com.strixmc.strong.proxy.utils.settings.Settings;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bungee.annotation.Sender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;
import java.util.UUID;

public class StreamingCommand implements CommandClass {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;
  @Inject private Utils utils;
  @Inject private Settings settings;

  @Command(names = {"livestreaming", "livestream", "streaming", "stream", "live", "directo"})
  public boolean command(@Sender ProxiedPlayer player, @OptArg String link, @OptArg @Text String message) {
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

      ProxyServer.getInstance().getServers().values().
              forEach(serverInfo -> serverInfo.getPlayers().
                      forEach(online -> lang.getHoverMessage(player.getName(), online.getName()).forEach(s -> {
                        if (s.contains("$CLICKABLE")) {
                          online.sendMessage(textComponent);
                        } else if (s.contains("$MESSAGE")) {
                          if (message != null) {
                            if (settings.isCustomMessage()) {
                              online.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(message) : message);
                            }
                          }
                        } else {
                          online.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(s) : s);
                        }
                      })));

      if (!player.hasPermission("strong.bypass.cooldown")) {
        cooldownCache.add(player.getUniqueId(), System.currentTimeMillis() + settings.getCooldownInterval() * 1000L);
      }
      player.sendMessage(lang.getSuccessfully());
      return true;
    }

    player.sendMessage(lang.getUsage());
    return true;
  }
}
