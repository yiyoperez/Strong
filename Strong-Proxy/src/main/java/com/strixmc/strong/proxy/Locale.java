package com.strixmc.strong.proxy;

import com.strixmc.strong.proxy.utils.FileManager;
import lombok.AllArgsConstructor;

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
    return new MessageFormat(Strong.instance.getUtils().translate(Strong.instance.getLang().getFile().getString(path))).format(objects);
  }

  public List<String> formatLines(Object... objects) {
    FileManager lang = Strong.instance.getLang();
    List<String> lines = new ArrayList<>();

    if (lang.getFile().get(path) instanceof String) {
      lines.add(format(objects));
    } else {
      for (String string : lang.getFile().getStringList(path)) {
        lines.add(new MessageFormat(Strong.instance.getUtils().translate(string)).format(objects));
      }
    }

    return lines;
  }

}
