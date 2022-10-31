package krisapps.announcements.commands.tab;

import krisapps.announcements.Announcements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResetAutoAnnounceTab implements TabCompleter {
    Announcements main;

    public ResetAutoAnnounceTab(Announcements main) {
        this.main = main;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (main.data.getString("coredata.announcementList") == null) {
                completions.add("<no conditions have been set for any announcements>");
                return completions;
            }
            for (Object ann : main.data.getList("coredata.announcementList")) {
                completions.add(ann.toString());
            }
        }
        return completions;
    }
}
