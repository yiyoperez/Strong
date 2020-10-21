package com.strixmc.proxystrong.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.common.loader.Loader;
import com.strixmc.proxystrong.lang.LangUtility;

@Singleton
public class LangLoader implements Loader {

  @Inject private LangUtility langUtility;

  @Override
  public void load() {
    langUtility.createLang();
    langUtility.updateMessages();
  }
}
