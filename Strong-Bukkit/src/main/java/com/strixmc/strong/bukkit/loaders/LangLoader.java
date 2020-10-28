package com.strixmc.strong.bukkit.loaders;

import com.strixmc.strong.bukkit.lang.LangUtility;
import com.strixmc.common.loader.Loader;

import javax.inject.Inject;

public class LangLoader implements Loader {

  @Inject private LangUtility langUtility;

  @Override
  public void load() {
    langUtility.createLang();
    langUtility.updateMessages();
  }
}
