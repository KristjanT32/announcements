package krisapps.announcements.commands.tab;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementTrigger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoAnnounceTab implements TabCompleter {
    Announcements main;

    public AutoAnnounceTab(Announcements main) {
        this.main = main;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (main.data.getConfigurationSection("presets") == null) {
                completions.add("<no announcements to show yet>");
                return completions;
            }
            for (String ann : main.data.getConfigurationSection("presets").getKeys(false)) {
                completions.add(ann);
            }
        }

        if (args.length == 2) {
            for (AnnouncementTrigger trigger : AnnouncementTrigger.values()) {
                completions.add(trigger.name());
            }
        }

        return completions;
    }
}
