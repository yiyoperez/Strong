package com.strixmc.strong.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.strixmc.strong.bukkit.utils.BinderModule;
import com.strixmc.strong.bukkit.utils.settings.Settings;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.bukkit.utils.ConfigUpdater;
import lombok.SneakyThrows;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class Strong extends JavaPlugin {

  @Inject @Named("LangLoader") Loader langLoader;
  @Inject @Named("CommandsLoader") Loader commandsLoader;
  @Inject private Settings settings;

  @Override
  public void onEnable() {
    new MetricsLite(this, 9149);

    BinderModule binderModule = new BinderModule(this);
    Injector injector = binderModule.createInjector();
    injector.injectMembers(this);

    createConfig();

    settings.updateSettings();
    langLoader.load();
    commandsLoader.load();
  }

  @SneakyThrows
  private void createConfig() {
    saveDefaultConfig();
    ConfigUpdater.update(this, "config.yml", new File(getDataFolder().getAbsolutePath(), "config.yml"), Arrays.asList("ALLOWED_URLS"));
    reloadConfig();
  }
}
