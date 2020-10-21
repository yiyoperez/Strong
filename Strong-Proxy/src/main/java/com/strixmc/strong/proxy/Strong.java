package com.strixmc.strong.proxy;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.utils.BinderModule;
import com.strixmc.strong.proxy.utils.FileManager;
import com.strixmc.strong.proxy.utils.settings.Settings;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.MetricsLite;

public class Strong extends Plugin {

  @Getter private FileManager config;
  @Inject @Named("CommandsLoader") private Loader commandsLoader;
  @Inject @Named("LangLoader") private Loader langLoader;
  @Inject private Settings settings;

  @Override
  public void onEnable() {
    new MetricsLite(this, 9163);

    BinderModule binderModule = new BinderModule(this);
    Injector injector = binderModule.createInjector();
    injector.injectMembers(this);
    config = new FileManager(this, "config.yml", "config.yml").loadDefaultFile();

    settings.updateSettings();
    commandsLoader.load();
    langLoader.load();
  }

}