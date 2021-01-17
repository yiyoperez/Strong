package com.strixmc.strong.bukkit.utils;

import com.strixmc.common.utils.DefaultFontInfo;
import org.bukkit.ChatColor;

import javax.inject.Singleton;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class Utils {

  private final static int CENTER_PX = 154;

  public boolean matchPattern(String patternString, String link) {
    final Pattern pattern = Pattern.compile("(?i)(" + patternString + "(?!x)x)");
    final Matcher matcher = pattern.matcher(link);
    return matcher.find();
  }

  public String translate(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public List<String> translateList(List<String> list) {
    list.replaceAll(this::translate);
    return list;
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
      } else if (previousCode) {
        previousCode = false;
        isBold = c == 'l' || c == 'L';
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

}
