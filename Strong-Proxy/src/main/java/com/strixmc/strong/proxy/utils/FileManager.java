package com.strixmc.strong.proxy.utils;

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
  private File file;
  private Configuration configuration;
  private String fileName;

  public FileManager(Plugin plugin, String fileName) {
    this.plugin = plugin;
    this.fileName = fileName;
    loadDefaultFile();
  }

  public Configuration getFile() {
    if (configuration == null) {
      reloadFile();
    }

    return configuration;
  }

  public void reloadFile() {
    if (configuration == null) {
      file = new File(plugin.getDataFolder(), fileName);
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

  private FileManager loadDefaultFile() {

    if (!plugin.getDataFolder().exists()) {
      plugin.getDataFolder().mkdirs();
    }

    if (file == null) {
      file = new File(plugin.getDataFolder(), fileName);
    }

    if (!file.exists()) {
      try {
        if (fileName == null) {
          file.createNewFile();
          return this;
        }

        InputStream inputStream = plugin.getResourceAsStream(fileName);
        if (inputStream == null) {
          throw new FileNotFoundException("The file " + fileName + "was not founded in plugin files");
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