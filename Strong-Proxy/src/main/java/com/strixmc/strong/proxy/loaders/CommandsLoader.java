package com.strixmc.strong.proxy.loaders;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.commands.StreamingCommand;
import com.strixmc.strong.proxy.commands.StrongCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bungee.BungeeCommandManager;
import me.fixeddev.commandflow.bungee.factory.BungeeModule;

import javax.inject.Inject;

public class CommandsLoader implements Loader {

  @Inject private Strong main;
  @Inject private StreamingCommand streamingCommand;
  @Inject private StrongCommand strongCommand;

  @Override
  public void load() {
    CommandManager commandManager = new BungeeCommandManager(main);
    PartInjector injector = PartInjector.create();
    injector.install(new DefaultsModule());
    injector.install(new BungeeModule());
    AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(injector);

    commandManager.registerCommands(builder.fromClass(streamingCommand));
    commandManager.registerCommands(builder.fromClass(strongCommand));
  }
}
