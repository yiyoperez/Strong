package com.strixmc.strong.proxy;

import com.strixmc.common.loader.Loader;
import com.strixmc.strong.proxy.utils.BinderModule;
import com.strixmc.strong.proxy.utils.FileManager;
import com.strixmc.strong.proxy.utils.UpdateChecker;
import com.strixmc.strong.proxy.utils.settings.Settings;
import lombok.Getter;
import me.yushust.inject.Injector;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.MetricsLite;

import javax.inject.Inject;
import javax.inject.Named;

public class Strong extends Plugin {

  @Getter private FileManager config;
  @Inject @Named("CommandsLoader") private Loader commandsLoader;
  @Inject @Named("LangLoader") private Loader langLoader;
  @Inject private Settings settings;

  @Override
  public void onEnable() {
    new MetricsLite(this, 9163);
    UpdateChecker.init(this, 85040).requestUpdateCheck().whenComplete((result, exception) -> {
      UpdateChecker.UpdateReason reason = result.getReason();
      switch (reason) {
        case NEW_UPDATE: {
          this.getLogger().info(String.format("An update is available! Strong %s may be downloaded at %s", result.getNewestVersion(), "https://www.spigotmc.org/resources/85040/updates"));
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
    config = new FileManager(this, "config.yml", "config.yml").loadDefaultFile();

    settings.updateSettings();
    commandsLoader.load();
    langLoader.load();
  }

}
