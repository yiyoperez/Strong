package com.strixmc.proxystrong.utils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {
  private final Plugin plugin;
  private final String outputFile;
  private File file;
  private Configuration configuration;
  private String inputFile;

  public FileManager(Plugin plugin, String outputFile) {
    this.plugin = plugin;
    this.outputFile = outputFile;
  }

  public FileManager(Plugin plugin, String outputFile, String inputFile) {
    this.plugin = plugin;
    this.outputFile = outputFile;
    this.inputFile = inputFile;
  }

  public Configuration getFile() {
    if (configuration == null) {
      reloadFile();
    }

    return configuration;
  }

  public void reloadFile() {
    if (configuration == null) {
      file = new File(plugin.getDataFolder(), outputFile);
    }

    try {
      configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveFile() {
    try {
      ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public FileManager loadDefaultFile() {

    if (!plugin.getDataFolder().exists()) {
      plugin.getDataFolder().mkdirs();
    }

    if (file == null) {
      file = new File(plugin.getDataFolder(), outputFile);
    }

    if (!file.exists()) {
      try {
        if (inputFile == null) {
          file.createNewFile();
          return this;
        }

        InputStream inputStream = plugin.getResourceAsStream(inputFile);
        if (inputStream == null) {
          throw new FileNotFoundException("The file " + inputFile + "was not founded in plugin files");
        }

        Configuration inputConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(inputStream);

        if (file.isDirectory()) {
          file.delete();
        }

        if (!file.exists()) {
          file.createNewFile();
        }

        ConfigurationProvider.getProvider(YamlConfiguration.class).save(inputConfig, file);

      } catch (IOException e) {
        e.printStackTrace();
      }
      return this;
    }

    reloadFile();
    return this;
  }

}