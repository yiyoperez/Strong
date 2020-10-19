package com.strixmc.strong.utils;

import com.google.inject.Singleton;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Singleton
public class Utils {

  private final static int CENTER_PX = 154;

  public String centerMessage(String message) {
    if (message == null || message.equals("")) return "";
    message = ChatColor.translateAlternateColorCodes('&', message);

    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for (char c : message.toCharArray()) {
      if (c == '§') {
        previousCode = true;
        continue;
      } else if (previousCode == true) {
        previousCode = false;
        if (c == 'l' || c == 'L') {
          isBold = true;
          continue;
        } else isBold = false;
      } else {
        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }

    int halvedMessageSize = messagePxSize / 2;
    int toCompensate = CENTER_PX - halvedMessageSize;
    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while (compensated < toCompensate) {
      sb.append(" ");
      compensated += spaceLength;
    }
    return sb.toString() + message;
  }

  public void sendCenteredMessage(Player player, String message) {
    if (message == null || message.equals("")) player.sendMessage("");
    message = ChatColor.translateAlternateColorCodes('&', message);

    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for (char c : message.toCharArray()) {
      if (c == '§') {
        previousCode = true;
        continue;
      } else if (previousCode == true) {
        previousCode = false;
        if (c == 'l' || c == 'L') {
          isBold = true;
          continue;
        } else isBold = false;
      } else {
        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }

    int halvedMessageSize = messagePxSize / 2;
    int toCompensate = CENTER_PX - halvedMessageSize;
    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while (compensated < toCompensate) {
      sb.append(" ");
      compensated += spaceLength;
    }
    player.sendMessage(sb.toString() + message);
  }
}
