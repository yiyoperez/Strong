package com.strixmc.strong.bukkit;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.bukkit.utils.BinderModule;
import com.strixmc.strong.bukkit.utils.ConfigUpdater;
import com.strixmc.strong.bukkit.utils.UpdateChecker;
import com.strixmc.strong.bukkit.utils.settings.Settings;
import lombok.SneakyThrows;
import me.yushust.inject.Injector;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Arrays;

public class Strong extends JavaPlugin {

  @Inject @Named("LangLoader") private Loader langLoader;
  @Inject @Named("CommandsLoader") private Loader commandsLoader;
  @Inject private Settings settings;

  @Override
  public void onEnable() {
    new MetricsLite(this, 9149);
    UpdateChecker.init(this, 85026).requestUpdateCheck().whenComplete((result, exception) -> {
      UpdateChecker.UpdateReason reason = result.getReason();
      switch (reason) {
        case NEW_UPDATE: {
          this.getLogger().info(String.format("An update is available! Strong %s may be downloaded at %s", result.getNewestVersion(), "https://www.spigotmc.org/resources/85026/updates"));
          break;
        }
        case UP_TO_DATE: {
          this.getLogger().info(String.format("Your version of Strong (%s) is up to date!", result.getNewestVersion()));
          break;
        }
        case COULD_NOT_CONNECT: {
          this.getLogger().warning("Could not check for a new version of Strong. Reason: " + reason);
          break;
        }
        case UNRELEASED_VERSION: {
          this.getLogger().info(String.format("Your version of Strong (%s) is more recent than the one publicly available. Are you on a development build?", result.getNewestVersion()));
          break;
        }
      }
    });

    BinderModule binderModule = new BinderModule(this);
    Injector injector = binderModule.createInjector();
    injector.injectMembers(this);

    createConfig();

    settings.updateSettings();
    langLoader.load();
    commandsLoader.load();
  }

  @SneakyThrows
  private void createConfig() {
    saveDefaultConfig();
    ConfigUpdater.update(this, "config.yml", new File(getDataFolder().getAbsolutePath(), "config.yml"), Arrays.asList("ALLOWED_URLS"));
    reloadConfig();
  }
}
