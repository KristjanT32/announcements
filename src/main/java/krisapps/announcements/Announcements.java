package krisapps.announcements;

import krisapps.announcements.commands.*;
import krisapps.announcements.commands.tab.AnnounceTab;
import krisapps.announcements.commands.tab.AutoAnnounceTab;
import krisapps.announcements.commands.tab.CreateAnnouncementTab;
import krisapps.announcements.commands.tab.ResetAutoAnnounceTab;
import krisapps.announcements.events.AnnouncementTriggerEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class Announcements extends JavaPlugin {

    public File configFile = new File(getDataFolder(), "config.yml");
    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration config = new YamlConfiguration();
    public FileConfiguration data = new YamlConfiguration();
    public Logger log = this.getLogger();

    @Override
    public void onEnable() {
        setup();

    }

    @Override
    public void onDisable() {

    }

    private void setup() {

        if (!configFile.getParentFile().exists() || !configFile.exists()) {
            log.info("[Setup/Files INF]: Creating a new configuration file");
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", true);
        }
        if (!dataFile.getParentFile().exists() || !dataFile.exists()) {
            log.info("[Setup/Files INF]: Creating a new internal data file");
            dataFile.getParentFile().mkdirs();
        }

        log.info("[Setup/Files]: Initializing files...");
        try {
            config.load(configFile);
            data.load(dataFile);
            log.info("[Setup/Files INF]: All files initialized successfully.");
        } catch (InvalidConfigurationException | IOException e) {
            log.severe("[Setup/Files ERR]: Failed to initialize the files: " + e.getMessage());
        }

        register();

    }

    private void register() {
        log.info("[Setup/Components]: Registering plugin components...");

        // Commands

        getCommand("createannouncement").setExecutor(new CreateAnnoucement(this));
        getCommand("announce").setExecutor(new AnnounceCommand(this));
        getCommand("previewannouncement").setExecutor(new PreviewAnnouncementCommand(this));
        getCommand("scheduleautoannounce").setExecutor(new ScheduleAutoAnnounce(this));
        getCommand("resetautoannounce").setExecutor(new ResetAutoAnnounceCommand(this));

        // Tab
        getCommand("createannouncement").setTabCompleter(new CreateAnnouncementTab());
        getCommand("announce").setTabCompleter(new AnnounceTab(this));
        getCommand("scheduleautoannounce").setTabCompleter(new AutoAnnounceTab(this));
        getCommand("previewannouncement").setTabCompleter(new AnnounceTab(this));
        getCommand("resetautoannounce").setTabCompleter(new ResetAutoAnnounceTab(this));

        // Events
        Bukkit.getPluginManager().registerEvents(new AnnouncementTriggerEventHandler(this), this);

        log.info("[Setup/Components]: Complete.");
    }
}
