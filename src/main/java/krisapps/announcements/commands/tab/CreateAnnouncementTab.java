package krisapps.announcements.commands.tab;

import krisapps.announcements.enums.AnnouncementContext;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CreateAnnouncementTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("<id>");
        }
        if (args.length == 2) {
            for (AnnouncementContext context : AnnouncementContext.values()) {
                completions.add(context.name().toLowerCase());
            }
        }
        if (args.length == 3) {
            if (args[1].equals(AnnouncementContext.CONTEXT_SPECIFIC_PLAYER.name().toLowerCase())) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
            } else {
                completions.add("<announcementText>");
            }
        }
        if (args.length >= 4) {
            completions.add("<announcementText>");
        }

        return completions;
    }
}
