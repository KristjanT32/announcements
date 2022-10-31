package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import krisapps.announcements.utilities.AnnouncementBuilder;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PreviewAnnouncementCommand implements CommandExecutor {

    Announcements main;

    public PreviewAnnouncementCommand(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Syntax: /previewannouncement <id>
        if (main.data.contains("presets." + args[0])) {
            String body = main.data.getString("presets." + args[0] + ".text");
            AnnouncementContext context = AnnouncementContext.valueOf(main.data.getString("presets." + args[0] + ".context"));
            sender.sendMessage(SimpleMessageBuilder.parse("&e=====&b[[&b&lPREVIEW&r&b]]&e================================\n") +
                    SimpleMessageBuilder.parse(AnnouncementBuilder.parsePlaceholders(body, main.data.getString("presets." + args[0] + ".target", "none"), main))
                    + SimpleMessageBuilder.parse("\n&e=====&b[[&b&lPREVIEW&r&b]]&e================================\n")
                    + SimpleMessageBuilder.parse("&eThis will be broadcast according to its original context &b(&a" + context + "&b)&e when used with &b/announce&e.")
            );

        } else {
            sender.sendMessage(SimpleMessageBuilder.parse("&cThere are no announcements to preview with this id &r(&b" + args[0] + "&r)."));
        }
        return true;
    }
}
