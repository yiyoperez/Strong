package com.strixmc.strong.bukkit.utils;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileCreator extends YamlConfiguration {

  private final String fileName;
  private final Plugin plugin;
  private final File file;

  public FileCreator(Plugin plugin, String filename, File folder) {
    this.plugin = plugin;
    this.fileName = filename + (filename.endsWith(".yml") ? "" : ".yml");
    this.file = new File(folder, this.fileName);
    this.createFile();
  }

  public FileCreator(Plugin plugin, String fileName) {
    this(plugin, fileName, plugin.getDataFolder());
  }

  public FileCreator(Plugin plugin, String fileName, String filePath) {
    this(plugin, fileName, new File(plugin.getDataFolder().getAbsolutePath() + File.separator + filePath));
  }

  @SneakyThrows
  private void createFile() {
    if (!file.exists()) {
      if (this.plugin.getResource(this.fileName) != null) {
        this.plugin.saveResource(this.fileName, false);
      } else {
        this.save(file);
      }
      this.load(file);
      return;
    }
    this.load(file);

    this.save(file);
  }

  @SneakyThrows
  public void reload() {
    load(file);
  }

  @SneakyThrows
  public void save() {
    this.save(file);
  }

}
