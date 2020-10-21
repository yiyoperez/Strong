package com.strixmc.strong.bukkit.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.bukkit.lang.LangUtility;
import com.strixmc.common.loader.Loader;

@Singleton
public class LangLoader implements Loader {

  @Inject private LangUtility langUtility;

  @Override
  public void load() {
    langUtility.createLang();
    langUtility.updateMessages();
  }
}
