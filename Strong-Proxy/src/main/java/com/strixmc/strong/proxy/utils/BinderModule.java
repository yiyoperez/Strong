package com.strixmc.strong.proxy.utils;

import com.strixmc.common.cache.BaseCache;
import com.strixmc.common.cache.Cache;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.lang.LangUtility;
import com.strixmc.strong.proxy.lang.LangUtilityImpl;
import com.strixmc.strong.proxy.loaders.CommandsLoader;
import com.strixmc.strong.proxy.loaders.LangLoader;
import com.strixmc.strong.proxy.utils.settings.Settings;
import com.strixmc.strong.proxy.utils.settings.SettingsImpl;
import me.yushust.inject.AbstractModule;
import me.yushust.inject.Injector;
import me.yushust.inject.key.TypeReference;

import java.util.UUID;

public class BinderModule extends AbstractModule {

  private final Strong main;

  public BinderModule(Strong main) {this.main = main;}

  public Injector createInjector() {
    return Injector.create(this);
  }

  @Override
  protected void configure() {
    bind(Strong.class).toInstance(this.main);

    bind(new TypeReference<Cache<UUID, Long>>() {
    }).toInstance(new BaseCache<>());

    bind(LangUtility.class).to(LangUtilityImpl.class).singleton();
    bind(Settings.class).to(SettingsImpl.class).singleton();

    bind(Loader.class).named("CommandsLoader").to(CommandsLoader.class).singleton();
    bind(Loader.class).named("LangLoader").to(LangLoader.class).singleton();
  }

}
