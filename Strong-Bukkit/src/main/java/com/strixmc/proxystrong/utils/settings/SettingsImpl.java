package com.strixmc.proxystrong.utils.settings;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.proxystrong.Strong;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class SettingsImpl implements Settings {

  @Getter private boolean customMessage;
  @Getter private boolean centeredMessage;
  @Getter private int cooldownInterval;
  @Getter private List<String> allowedURLS;

  @Inject private Strong main;

  @Override
  public void updateSettings() {
    allowedURLS = new ArrayList<>();

    allowedURLS = main.getConfig().getStringList("ALLOWED_URLS");
    cooldownInterval = main.getConfig().getInt("COMMAND_COOLDOWN");
    customMessage = main.getConfig().getBoolean("ALLOW_CUSTOM_MESSAGE");
    centeredMessage = main.getConfig().getBoolean("CENTER_MESSAGES");
  }

}
