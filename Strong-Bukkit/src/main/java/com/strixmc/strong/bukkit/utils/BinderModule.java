package com.strixmc.strong.bukkit.utils;

import com.strixmc.common.cache.BaseCache;
import com.strixmc.common.cache.Cache;
import com.strixmc.common.loader.Loader;
import com.strixmc.common.utils.Cooldown;
import com.strixmc.strong.bukkit.Strong;
import com.strixmc.strong.bukkit.loaders.CommandsLoader;
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

    bind(FileCreator.class).named("Config").toInstance(new FileCreator(main, "config"));
    bind(FileCreator.class).named("Lang").toInstance(new FileCreator(main, "lang"));

    bind(Loader.class).named("CommandsLoader").to(CommandsLoader.class).singleton();
  }

}
