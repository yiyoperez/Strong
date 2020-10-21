package com.strixmc.strong.bukkit.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.strixmc.strong.bukkit.Strong;
import com.strixmc.strong.bukkit.commands.StreamingCommand;
import com.strixmc.common.loader.Loader;
import com.strixmc.strong.bukkit.commands.StrongCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

@Singleton
public class CommandsLoader implements Loader {

  @Inject private Strong main;
  @Inject private StreamingCommand streamingCommand;
  @Inject private StrongCommand strongCommand;

  @Override
  public void load() {
    CommandManager commandManager = new BukkitCommandManager(main.getName());
    PartInjector injector = PartInjector.create();
    injector.install(new DefaultsModule());
    injector.install(new BukkitModule());
    AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(injector);

    commandManager.registerCommands(builder.fromClass(streamingCommand));
    commandManager.registerCommands(builder.fromClass(strongCommand));
  }
}
