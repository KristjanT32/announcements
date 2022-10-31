package krisapps.announcements.commands.tab;

import krisapps.announcements.Announcements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnnounceTab implements TabCompleter {

    Announcements main;

    public AnnounceTab(Announcements main) {
        this.main = main;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (main.data.getConfigurationSection("presets") != null) {
                for (String preset : main.data.getConfigurationSection("presets").getKeys(false)) {
                    completions.add(preset);
                }
            } else {
                completions.add("<no presets to show yet>");
            }
        }

        return completions;
    }
}
