package com.strixmc.strong.proxy.commands;

import com.strixmc.common.cache.Cache;
import com.strixmc.strong.proxy.lang.LangUtility;
import com.strixmc.strong.proxy.utils.Utils;
import com.strixmc.strong.proxy.utils.settings.Settings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import javax.inject.Inject;
import java.util.UUID;

public class StreamingCommand extends Command {

  @Inject private Cache<UUID, Long> cooldownCache;
  @Inject private LangUtility lang;
  @Inject private Utils utils;
  @Inject private Settings settings;

  public StreamingCommand() {
    super("livestream", "", "live", "streaming", "stream", "directo");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) return;
    ProxiedPlayer player = (ProxiedPlayer) sender;

    if (!player.hasPermission("strong.command.streaming")) {
      player.sendMessage(lang.getNoPermissions());
      return;
    }

    if (args.length == 0) {
      player.sendMessage(lang.getUsage());
      return;
    }

    final String link = args[0];
    final StringBuilder sb = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      sb.append(args[i]).append(" ");
    }
    final String message = sb.toString();
    if (cooldownCache.find(player.getUniqueId()).isPresent() && cooldownCache.find(player.getUniqueId()).get() >= System.currentTimeMillis()) {
      player.sendMessage(lang.getCooldownActive(utils.millisToRoundedTime(cooldownCache.find(player.getUniqueId()).get() - System.currentTimeMillis())));
      return;
    }

    if (link != null) {
      String patternString = "";
      for (String s : settings.getAllowedURLS()) {
        patternString = String.format("%s(%s)|", patternString, s);
      }
      if (!utils.matchPattern(patternString, link)) {
        player.sendMessage(lang.getValidURL());
        return;
      }

      TextComponent textComponent = new TextComponent(settings.isCenteredMessage() ? utils.centerMessage(lang.getClickHere()) : lang.getClickHere());
      textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(lang.getClickMessage(player.getName())).create()));
      textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));

      ProxyServer.getInstance().getServers().values().forEach(serverInfo -> serverInfo.getPlayers().forEach(online -> {
        lang.getHoverMessage(player.getName(), online.getName()).forEach(s -> player.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(s) : s));
        if (!message.trim().isEmpty()) {
          if (settings.isCustomMessage()) {
            player.sendMessage(settings.isCenteredMessage() ? utils.centerMessage(message) : message);
          }
        }
        online.sendMessage(textComponent);
      }));

      if (!player.hasPermission("strong.bypass.cooldown")) {
        cooldownCache.add(player.getUniqueId(), System.currentTimeMillis() + settings.getCooldownInterval() * 1000L);
      }
      player.sendMessage(lang.getSuccessfully());
      return;
    }

    player.sendMessage(lang.getUsage().replace("<command>", this.getName()));
  }
}
