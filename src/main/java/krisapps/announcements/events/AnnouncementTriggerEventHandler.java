package krisapps.announcements.events;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import krisapps.announcements.enums.AnnouncementTrigger;
import krisapps.announcements.utilities.AnnouncementBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.ArrayList;

public class AnnouncementTriggerEventHandler implements Listener {

    Announcements main;

    public AnnouncementTriggerEventHandler(Announcements main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent joinEvent) {
        main.log.info("Handling player join announcements...");

        if (!isConditionListEmpty()) {
            for (String announcement : getConditionList()) {
                if (isConditionEqual(AnnouncementTrigger.SHOW_ON_JOIN, announcement)) {
                    AnnouncementBuilder.parsePlaceholdersAndAnnounce(main.data.getString("presets." + announcement + ".text"),
                            AnnouncementContext.valueOf(main.data.getString("presets." + announcement + ".context").toUpperCase()),
                            joinEvent.getPlayer().getName(), main);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent quitEvent) {
        main.log.info("Handling player quit announcements...");

        if (!isConditionListEmpty()) {
            for (String announcement : getConditionList()) {
                if (isConditionEqual(AnnouncementTrigger.SHOW_ON_LEAVE, announcement)) {
                    AnnouncementBuilder.parsePlaceholdersAndAnnounce(main.data.getString("presets." + announcement + ".text"),
                            AnnouncementContext.valueOf(main.data.getString("presets." + announcement + ".context").toUpperCase()),
                            quitEvent.getPlayer().getName(), main);
                }
            }
        }
    }

    @EventHandler
    public void onServerStarted(ServerLoadEvent loadEvent) {
        main.log.info("Handling server load announcements...");

        if (!isConditionListEmpty()) {
            for (String announcement : getConditionList()) {
                if (isConditionEqual(AnnouncementTrigger.SHOW_ON_SERVER_START, announcement)) {
                    AnnouncementBuilder.parsePlaceholdersAndAnnounce(main.data.getString("presets." + announcement + ".text"),
                            AnnouncementContext.valueOf(main.data.getString("presets." + announcement + ".context").toUpperCase()),
                            null, main);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent kickEvent) {
        if (kickEvent.getLeaveMessage().contains("kick")) {
            main.log.info("Handling player kick announcements...");

            if (!isConditionListEmpty()) {
                for (String announcement : getConditionList()) {
                    if (isConditionEqual(AnnouncementTrigger.SHOW_ON_PLAYER_KICKED, announcement)) {
                        AnnouncementBuilder.parsePlaceholdersAndAnnounce(main.data.getString("presets." + announcement + ".text"),
                                AnnouncementContext.valueOf(main.data.getString("presets." + announcement + ".context").toUpperCase()),
                                kickEvent.getPlayer().getName(), main);
                    }
                }
            }
        } else if (kickEvent.getLeaveMessage().contains("ban")) {
            main.log.info("Handling player ban announcements...");

            if (!isConditionListEmpty()) {
                for (String announcement : getConditionList()) {
                    if (isConditionEqual(AnnouncementTrigger.SHOW_ON_PLAYER_BANNED, announcement)) {
                        AnnouncementBuilder.parsePlaceholdersAndAnnounce(main.data.getString("presets." + announcement + ".text"),
                                AnnouncementContext.valueOf(main.data.getString("presets." + announcement + ".context").toUpperCase()),
                                kickEvent.getPlayer().getName(), main);
                    }
                }
            }
        }
    }

    boolean isConditionListEmpty() {
        if (main.data.contains("coredata.announcementList")) {
            return main.data.getList("coredata.announcementList").size() <= 0;
        } else {
            return true;
        }
    }

    ArrayList<String> getConditionList() {
        if (main.data.getList("coredata.announcementList") != null) {
            return (ArrayList<String>) main.data.getList("coredata.announcementList");
        } else {
            return new ArrayList<>();
        }
    }

    private boolean isConditionEqual(AnnouncementTrigger trigger, String announcement) {
        return (AnnouncementTrigger.valueOf(main.data.getString("presets." + announcement + ".trigger").toUpperCase()).equals(trigger));
    }


}
