package com.strixmc.strong.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.lang.LangUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
public class Utils {

  private final static int CENTER_PX = 154;
  @Inject private LangUtility lang;

  public String translate(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public List<String> translateList(List<String> list) {
    list.replaceAll(this::translate);
    return list;
  }

  public String millisToRoundedTime(long millis) {
    millis += 1L;

    long seconds = millis / 1000L;
    long minutes = seconds / 60L;
    long hours = minutes / 60L;
    long days = hours / 24L;
    long weeks = days / 7L;
    long months = weeks / 4L;
    long years = months / 12L;

    if (years > 0) {
      return years + (years == 1 ? lang.getYear() : lang.getYears());
    } else if (months > 0) {
      return months + (months == 1 ? lang.getMonth() : lang.getMonths());
    } else if (weeks > 0) {
      return weeks + (weeks == 1 ? lang.getWeek() : lang.getWeeks());
    } else if (days > 0) {
      return days + (days == 1 ? lang.getDay() : lang.getDays());
    } else if (hours > 0) {
      return hours + (hours == 1 ? lang.getHour() : lang.getHours());
    } else if (minutes > 0) {
      return minutes + (minutes == 1 ? lang.getMinute() : lang.getMinutes());
    } else if (seconds > 0) {
      return seconds + (seconds == 1 ? lang.getSecond() : lang.getSeconds());
    } else {
      return lang.getNow();
    }
  }

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
