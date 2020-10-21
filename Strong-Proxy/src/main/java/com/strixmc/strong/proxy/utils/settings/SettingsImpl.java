package com.strixmc.strong.proxy.utils.settings;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.proxy.Strong;
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

    allowedURLS = main.getConfig().getFile().getStringList("ALLOWED_URLS");
    cooldownInterval = main.getConfig().getFile().getInt("COMMAND_COOLDOWN");
    customMessage = main.getConfig().getFile().getBoolean("ALLOW_CUSTOM_MESSAGE");
    centeredMessage = main.getConfig().getFile().getBoolean("CENTER_MESSAGES");
  }

}
