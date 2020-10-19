package com.strixmc.strong.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.lang.LangUtility;
import com.strixmc.common.loader.Loader;

@Singleton
public class LangLoader implements Loader {

  @Inject private LangUtility langUtility;

  @Override
  public void load() {
    langUtility.updateMessages();
  }
}
