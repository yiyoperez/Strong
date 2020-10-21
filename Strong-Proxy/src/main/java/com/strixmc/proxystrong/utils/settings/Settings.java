package com.strixmc.proxystrong.utils.settings;

import java.util.List;

public interface Settings {

  void updateSettings();

  int getCooldownInterval();

  List<String> getAllowedURLS();

  boolean isCustomMessage();

  boolean isCenteredMessage();

}
