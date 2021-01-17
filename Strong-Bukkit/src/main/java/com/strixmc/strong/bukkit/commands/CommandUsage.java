package com.strixmc.strong.bukkit.commands;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.command.Command;
import me.fixeddev.commandflow.usage.UsageBuilder;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

public class CommandUsage implements UsageBuilder {
  @Override
  public Component getUsage(CommandContext context) {
    Command command = context.getCommand();
    String label = String.join(" ", context.getLabels());
    Component labelComponent = TextComponent.of("/").append(TextComponent.of(label)).color(TextColor.RED);
    Component partComponents = command.getPart().getLineRepresentation();
    if (partComponents != null) {
      labelComponent = labelComponent.append(TextComponent.of(" ")).append(partComponents.mergeColor(labelComponent));
    }
    return labelComponent;
  }

}
