package com.strixmc.strong.bukkit.loaders;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.bukkit.Strong;
import com.strixmc.strong.bukkit.commands.CommandTranslation;
import com.strixmc.strong.bukkit.commands.CommandUsage;
import com.strixmc.strong.bukkit.commands.StreamingCommand;
import com.strixmc.strong.bukkit.commands.StrongCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;

import javax.inject.Inject;

public class CommandsLoader implements Loader {

  @Inject private Strong main;
  @Inject private StreamingCommand streamingCommand;
  @Inject private StrongCommand strongCommand;

  @Override
  public void load() {
    CommandManager manager = new BukkitCommandManager(main.getName());
    manager.setTranslator(new DefaultTranslator(new CommandTranslation()));
    manager.setUsageBuilder(new CommandUsage());
    PartInjector injector = PartInjector.create();
    injector.install(new DefaultsModule());
    injector.install(new BukkitModule());
    AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(injector);

    manager.registerCommands(builder.fromClass(streamingCommand));
    manager.registerCommands(builder.fromClass(strongCommand));
  }
}
