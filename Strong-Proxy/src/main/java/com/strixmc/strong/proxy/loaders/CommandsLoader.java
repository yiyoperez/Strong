package com.strixmc.strong.proxy.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.commands.StreamingCommand;
import com.strixmc.strong.proxy.commands.StrongCommand;
import net.md_5.bungee.api.plugin.PluginManager;

@Singleton
public class CommandsLoader implements Loader {

  @Inject private Strong main;
  @Inject private StreamingCommand streamingCommand;
  @Inject private StrongCommand strongCommand;

  @Override
  public void load() {
    PluginManager pm = main.getProxy().getPluginManager();
    pm.registerCommand(main, streamingCommand);
    pm.registerCommand(main, strongCommand);
  }
}
