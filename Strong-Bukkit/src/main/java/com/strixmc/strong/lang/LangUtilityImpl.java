package com.strixmc.strong.lang;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.Strong;
import com.strixmc.strong.utils.ConfigUpdater;
import com.strixmc.strong.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class LangUtilityImpl implements LangUtility {

  @Getter String noPermissions;
  @Getter String usage;
  @Getter String cooldownActive;
  @Getter String clickMessage;
  @Getter String clickHere;
  @Getter List<String> hoverMessage;
  @Getter String successfully;
  @Getter String validURL;

  @Getter String successfullyReload;

  @Getter String year;
  @Getter String years;
  @Getter String month;
  @Getter String months;
  @Getter String week;
  @Getter String weeks;
  @Getter String day;
  @Getter String days;
  @Getter String hour;
  @Getter String hours;
  @Getter String minute;
  @Getter String minutes;
  @Getter String second;
  @Getter String seconds;
  @Getter String now;

  @Inject private Strong main;
  @Inject private Utils utils;
  private File langFile;
  private FileConfiguration lang;

  @SneakyThrows
  @Override
  public void createLang() {
    langFile = new File(main.getDataFolder().getAbsolutePath(), "lang.yml");
    lang = YamlConfiguration.loadConfiguration(langFile);

    if (!langFile.exists()) {
      main.saveResource("lang.yml", false);
    }
    ConfigUpdater.update(main, langFile.getName(), langFile, Arrays.asList("NOTHING", "YET"));
    lang.load(langFile);
  }

  @Override
  public void updateMessages() {

    try {
      lang.load(langFile);
      lang.save(langFile);
      ConfigUpdater.update(main, langFile.getName(), langFile, Arrays.asList("NOTHING", "YET"));
      lang.load(langFile);
    } catch (IOException | InvalidConfigurationException ex) {
      ex.printStackTrace();
    }

    this.usage = utils.translate(lang.getString("USAGE"));
    this.validURL = utils.translate(lang.getString("VALID_URL"));
    this.noPermissions = utils.translate(lang.getString("NO_PERMISSIONS"));
    this.cooldownActive = utils.translate(lang.getString("COOLDOWN_ACTIVE"));
    this.clickMessage = utils.translate(lang.getString("CLICK_MESSAGE"));
    this.clickHere = utils.translate(lang.getString("CLICK_HERE"));
    this.hoverMessage = utils.translateList(lang.getStringList("HOVER_MESSAGE"));
    this.successfully = utils.translate(lang.getString("SUCCESSFULLY"));
    this.successfullyReload = utils.translate(lang.getString("SUCCESSFULLY_RELOAD"));

    this.year = lang.getString("YEAR");
    this.years = lang.getString("YEARS");
    this.month = lang.getString("MONTH");
    this.months = lang.getString("MONTHS");
    this.week = lang.getString("WEEK");
    this.weeks = lang.getString("WEEKS");
    this.day = lang.getString("DAY");
    this.days = lang.getString("DAYS");
    this.hour = lang.getString("HOUR");
    this.hours = lang.getString("HOURS");
    this.minute = lang.getString("MINUTE");
    this.minutes = lang.getString("MINUTES");
    this.second = lang.getString("SECOND");
    this.seconds = lang.getString("SECONDS");
    this.now = lang.getString("NOW");

  }

  @Override
  public String getCooldownActive(String value) {
    return this.cooldownActive.replace("$INTERVAL", value);
  }

  @Override
  public String getClickMessage(String playerName) {
    return this.clickMessage.replace("$PLAYER", playerName);
  }

  @Override
  public List<String> getHoverMessage(String playerName, String targetName) {
    List<String> toReturn = new ArrayList<>();

    hoverMessage.forEach(s -> {
      s = s.replace("$PLAYER", playerName);
      s = s.replace("$TARGET", targetName);
      toReturn.add(s);
    });

    return toReturn;
  }
}
