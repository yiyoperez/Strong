package com.strixmc.strong.lang;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.Strong;
import com.strixmc.strong.utils.FileCreator;
import lombok.Getter;

@Singleton
public class LangUtilityImpl implements LangUtility {

  @Inject private Strong main;
  private FileCreator lang;

  @Getter String noPermissions;
  @Getter String cooldownActive;
  @Getter String clickMessage;
  @Getter String hoverMessage;
  @Getter String successfully;

  @Override
  public void updateMessages() {
    lang = new FileCreator(main, "lang", ".yml");

    this.noPermissions = lang.getString("NO_PERMISSIONS");
    this.cooldownActive = lang.getString("COOLDOWN_ACTIVE");
    this.clickMessage = lang.getString("CLICK_MESSAGE");
    this.hoverMessage = lang.getString("HOVER_MESSAGE");
    this.successfully = lang.getString("SUCCESSFULLY");
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
  public String getHoverMessage(String playerName) {
    return this.hoverMessage.replace("$PLAYER", playerName);
  }
}
