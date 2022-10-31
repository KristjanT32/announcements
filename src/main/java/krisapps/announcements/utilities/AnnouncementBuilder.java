package krisapps.announcements.utilities;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class AnnouncementBuilder {

    final static String CURRENT_PLAYER = "%player%";
    final static String CURRENT_PLAYER_COUNT = "%playerCount%";
    final static String DATE = "%today%";
    final static String DIVIDER = "%divider%";
    final static String HEADER = "%header%";
    final static String FOOTER = "%footer%";
    final static String MAX_PLAYERS = "%maxPlayers%";
    final static String BANNED_PLAYERS_COUNT = "%bannedPlayers%";
    final static String MOTD = "%motd%";

    public static void parsePlaceholdersAndAnnounce(String ann, AnnouncementContext context, @Nullable String contextVariable, Announcements main) {

        String out =
                ann.replaceAll(CURRENT_PLAYER_COUNT, String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                        .replaceAll(DATE, new Date().toString())
                        .replaceAll(DIVIDER, main.config.getString("customization.divider"))
                        .replaceAll(HEADER, main.config.getString("customization.header"))
                        .replaceAll(FOOTER, main.config.getString("customization.footer"))
                        .replaceAll(MAX_PLAYERS, String.valueOf(Bukkit.getServer().getMaxPlayers()))
                        .replaceAll(BANNED_PLAYERS_COUNT, String.valueOf(Bukkit.getServer().getBannedPlayers().size()))
                        .replaceAll(MOTD, Bukkit.getMotd());

        switch (context) {

            case CONTEXT_GLOBAL:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(SimpleMessageBuilder.parse(out).replaceAll(CURRENT_PLAYER, p.getName()));
                }
                break;
            case CONTEXT_OPS:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isOp()) {
                        p.sendMessage(SimpleMessageBuilder.parse(out).replaceAll(CURRENT_PLAYER, p.getName()));
                    }
                }
                break;
            case CONTEXT_SPECIFIC_PLAYER:
                if (Bukkit.getPlayer(contextVariable) != null) {
                    Bukkit.getPlayer(contextVariable).sendMessage(out.replaceAll(CURRENT_PLAYER, contextVariable));
                }
                break;
        }
    }

    public static String parsePlaceholders(String ann, Announcements main) {
        String out =
                ann.replaceAll(CURRENT_PLAYER_COUNT, String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                        .replaceAll(DATE, new Date().toString())
                        .replaceAll(DIVIDER, main.config.getString("customization.divider"))
                        .replaceAll(HEADER, main.config.getString("customization.header"))
                        .replaceAll(FOOTER, main.config.getString("customization.footer"))
                        .replaceAll(MAX_PLAYERS, String.valueOf(Bukkit.getServer().getMaxPlayers()))
                        .replaceAll(BANNED_PLAYERS_COUNT, String.valueOf(Bukkit.getServer().getBannedPlayers().size()))
                        .replaceAll(MOTD, Bukkit.getMotd());
        return out;
    }

    public static String parsePlaceholders(String ann, String playerName, Announcements main) {
        String out =
                ann.replaceAll(CURRENT_PLAYER_COUNT, String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                        .replaceAll(DATE, new Date().toString())
                        .replaceAll(DIVIDER, main.config.getString("customization.divider"))
                        .replaceAll(HEADER, main.config.getString("customization.header"))
                        .replaceAll(FOOTER, main.config.getString("customization.footer"))
                        .replaceAll(MAX_PLAYERS, String.valueOf(Bukkit.getServer().getMaxPlayers()))
                        .replaceAll(BANNED_PLAYERS_COUNT, String.valueOf(Bukkit.getServer().getBannedPlayers().size()))
                        .replaceAll(MOTD, Bukkit.getMotd())
                        .replaceAll(CURRENT_PLAYER, playerName);
        return out;
    }


}
