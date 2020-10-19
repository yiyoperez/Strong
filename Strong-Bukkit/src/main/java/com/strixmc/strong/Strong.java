package com.strixmc.strong;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.utils.BinderModule;
import com.strixmc.strong.utils.ConfigUpdater;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class Strong extends JavaPlugin {

  @Inject @Named("LangLoader") Loader langLoader;
  @Inject @Named("CommandsLoader") Loader commandsLoader;
  @Inject private Strong main;

  @Override
  public void onEnable() {
    BinderModule binderModule = new BinderModule(this);
    Injector injector = binderModule.createInjector();
    injector.injectMembers(this);

    createConfig();

    langLoader.load();
    commandsLoader.load();
  }

  @SneakyThrows
  private void createConfig() {
    main.saveDefaultConfig();
    ConfigUpdater.update(main, "config.yml", new File(main.getDataFolder().getAbsolutePath(), "config.yml"), Arrays.asList("NOTHING", "YET"));
    main.reloadConfig();
  }
}
