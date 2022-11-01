package krisapps.announcements.commands.tab;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EditAnnouncementTab implements TabCompleter {

    Announcements main;

    public EditAnnouncementTab(Announcements main) {
        this.main = main;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (main.data.contains("presets")) {
                for (String ann : main.data.getConfigurationSection("presets").getKeys(false)) {
                    completions.add(ann);
                }
            } else {
                completions.add("<no announcements to show yet>");
            }
        }

        if (args.length == 2) {
            if (main.data.contains("presets." + args[0])) {
                for (String property : main.data.getConfigurationSection("presets." + args[0]).getKeys(false)) {
                    completions.add(property);
                }
            } else {
                completions.add("<invalid announcement id>");
            }
        }

        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("context")) {
                for (AnnouncementContext context : AnnouncementContext.values()) {
                    completions.add(context.name());
                }
            } else {
                completions.add("<new value>");
            }
        }


        return completions;
    }
}
