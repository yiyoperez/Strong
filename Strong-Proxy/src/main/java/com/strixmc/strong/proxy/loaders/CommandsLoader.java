package com.strixmc.strong.proxy.loaders;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.Strong;
import com.strixmc.strong.proxy.commands.CommandTranslation;
import com.strixmc.strong.proxy.commands.CommandUsage;
import com.strixmc.strong.proxy.commands.StreamingCommand;
import com.strixmc.strong.proxy.commands.StrongCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bungee.BungeeCommandManager;
import me.fixeddev.commandflow.bungee.factory.BungeeModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;

import javax.inject.Inject;

public class CommandsLoader implements Loader {

  @Inject private Strong main;
  @Inject private StreamingCommand streamingCommand;
  @Inject private StrongCommand strongCommand;

  @Override
  public void load() {
    CommandManager manager = new BungeeCommandManager(main);
    manager.setTranslator(new DefaultTranslator(new CommandTranslation()));
    manager.setUsageBuilder(new CommandUsage());
    PartInjector injector = PartInjector.create();
    injector.install(new DefaultsModule());
    injector.install(new BungeeModule());
    AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(injector);

    manager.registerCommands(builder.fromClass(streamingCommand));
    manager.registerCommands(builder.fromClass(strongCommand));
  }
}
