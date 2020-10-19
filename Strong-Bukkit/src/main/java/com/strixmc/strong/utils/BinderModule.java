package com.strixmc.strong.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.strixmc.common.cache.BaseCache;
import com.strixmc.common.cache.Cache;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.Strong;
import com.strixmc.strong.lang.LangUtility;
import com.strixmc.strong.lang.LangUtilityImpl;
import com.strixmc.strong.loaders.CommandsLoader;
import com.strixmc.strong.loaders.LangLoader;

import java.util.UUID;

public class BinderModule extends AbstractModule {

  private final Strong main;

  public BinderModule(Strong main) {this.main = main;}

  public Injector createInjector() {
    return Guice.createInjector(this);
  }

  @Override
  protected void configure() {
    bind(Strong.class).toInstance(this.main);

    bind(new TypeLiteral<Cache<UUID, Long>>() {
    }).toInstance(new BaseCache<>());

    bind(LangUtility.class).to(LangUtilityImpl.class);

    bind(Loader.class).annotatedWith(Names.named("CommandsLoader")).to(CommandsLoader.class);
    bind(Loader.class).annotatedWith(Names.named("LangLoader")).to(LangLoader.class);
  }

}
