package com.strixmc.proxystrong.lang;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.proxystrong.Strong;
import com.strixmc.proxystrong.utils.FileManager;
import com.strixmc.proxystrong.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LangUtilityImpl implements LangUtility {

  @Getter private String noPermissions;
  @Getter private String usage;
  @Getter private String cooldownActive;
  @Getter private String clickMessage;
  @Getter private String clickHere;
  @Getter private List<String> hoverMessage;
  @Getter private String successfully;
  @Getter private String validURL;

  @Getter private String successfullyReload;

  @Getter private String year;
  @Getter private String years;
  @Getter private String month;
  @Getter private String months;
  @Getter private String week;
  @Getter private String weeks;
  @Getter private String day;
  @Getter private String days;
  @Getter private String hour;
  @Getter private String hours;
  @Getter private String minute;
  @Getter private String minutes;
  @Getter private String second;
  @Getter private String seconds;
  @Getter private String now;

  @Inject private Strong main;
  @Inject private Utils utils;
  private FileManager fileManager;

  @SneakyThrows
  @Override
  public void createLang() {
    fileManager = new FileManager(main, "lang.yml", "lang.yml").loadDefaultFile();
  }

  @Override
  public void updateMessages() {

    fileManager.reloadFile();
    fileManager.saveFile();
    fileManager.reloadFile();

    this.usage = utils.translate(fileManager.getFile().getString("USAGE"));
    this.validURL = utils.translate(fileManager.getFile().getString("VALID_URL"));
    this.noPermissions = utils.translate(fileManager.getFile().getString("NO_PERMISSIONS"));
    this.cooldownActive = utils.translate(fileManager.getFile().getString("COOLDOWN_ACTIVE"));
    this.clickMessage = utils.translate(fileManager.getFile().getString("CLICK_MESSAGE"));
    this.clickHere = utils.translate(fileManager.getFile().getString("CLICK_HERE"));
    this.hoverMessage = utils.translateList(fileManager.getFile().getStringList("HOVER_MESSAGE"));
    this.successfully = utils.translate(fileManager.getFile().getString("SUCCESSFULLY"));
    this.successfullyReload = utils.translate(fileManager.getFile().getString("SUCCESSFULLY_RELOAD"));

    this.year = fileManager.getFile().getString("YEAR");
    this.years = fileManager.getFile().getString("YEARS");
    this.month = fileManager.getFile().getString("MONTH");
    this.months = fileManager.getFile().getString("MONTHS");
    this.week = fileManager.getFile().getString("WEEK");
    this.weeks = fileManager.getFile().getString("WEEKS");
    this.day = fileManager.getFile().getString("DAY");
    this.days = fileManager.getFile().getString("DAYS");
    this.hour = fileManager.getFile().getString("HOUR");
    this.hours = fileManager.getFile().getString("HOURS");
    this.minute = fileManager.getFile().getString("MINUTE");
    this.minutes = fileManager.getFile().getString("MINUTES");
    this.second = fileManager.getFile().getString("SECOND");
    this.seconds = fileManager.getFile().getString("SECONDS");
    this.now = fileManager.getFile().getString("NOW");

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
