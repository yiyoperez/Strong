package com.strixmc.strong.proxy.injector;

import com.strixmc.common.cache.BaseCache;
import com.strixmc.common.cache.Cache;
import com.strixmc.common.loader.Loader;
import com.strixmc.common.utils.Cooldown;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.loaders.CommandsLoader;
import com.strixmc.strong.proxy.utils.FileManager;
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

    bind(new TypeReference<Cache<UUID, Cooldown>>() {}).toInstance(new BaseCache<>());

    bind(FileManager.class).named("Config").toInstance(new FileManager(main, "config.yml"));
    bind(FileManager.class).named("Lang").toInstance(new FileManager(main, "lang.yml"));

    bind(Loader.class).named("CommandsLoader").to(CommandsLoader.class).singleton();
  }

}
