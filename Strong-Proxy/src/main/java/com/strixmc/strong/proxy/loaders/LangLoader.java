package com.strixmc.strong.proxy.loaders;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.lang.LangUtility;

import javax.inject.Inject;

public class LangLoader implements Loader {

  @Inject private LangUtility langUtility;

  @Override
  public void load() {
    langUtility.createLang();
    langUtility.updateMessages();
  }
}
