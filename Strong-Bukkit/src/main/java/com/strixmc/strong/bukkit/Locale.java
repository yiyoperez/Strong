package com.strixmc.strong.bukkit;

import com.strixmc.strong.bukkit.utils.FileCreator;
import lombok.AllArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum Locale {

  VALID_URL("VALID_URL"),
  USAGE("USAGE"),
  NO_PERMISSION("NO_PERMISSIONS"),
  ON_COOLDOWN("COOLDOWN_ACTIVE"),
  CLICK_MESSAGE("CLICK_MESSAGE"),
  CLICK_HERE("CLICK_HERE"),
  HOVER_MESSAGE("HOVER_MESSAGE"),
  SUCCESS("SUCCESSFULLY"),
  SUCCESS_RELOAD("SUCCESSFULLY_RELOAD");

  private String path;

  public String format(Object... objects) {
    Strong strong = JavaPlugin.getPlugin(Strong.class);
    return new MessageFormat(strong.getUtils().translate(strong.getLang().getString(path))).format(objects);
  }

  public List<String> formatLines(Object... objects) {
    Strong strong = JavaPlugin.getPlugin(Strong.class);
    FileCreator lang = strong.getLang();
    List<String> lines = new ArrayList<>();

    if (lang.get(path) instanceof String) {
      lines.add(format(objects));
    } else {
      for (String string : lang.getStringList(path)) {
        lines.add(new MessageFormat(strong.getUtils().translate(string)).format(objects));
      }
    }

    return lines;
  }
}
