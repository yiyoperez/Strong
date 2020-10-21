package com.strixmc.proxystrong.lang;

import java.util.List;

public interface LangUtility {

  void createLang();

  void updateMessages();

  String getNoPermissions();

  String getUsage();

  String getValidURL();

  String getCooldownActive(String value);

  String getClickMessage(String playerName);

  String getClickHere();

  List<String> getHoverMessage(String playerName, String targetName);

  String getSuccessfully();

  String getSuccessfullyReload();

  String getYear();
  String getYears();
  String getMonth();
  String getMonths();
  String getWeek();
  String getWeeks();
  String getDay();
  String getDays();
  String getHour();
  String getHours();
  String getMinute();
  String getMinutes();
  String getSecond();
  String getSeconds();
  String getNow();

}
