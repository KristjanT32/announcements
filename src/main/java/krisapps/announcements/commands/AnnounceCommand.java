package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import krisapps.announcements.utilities.AnnouncementBuilder;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AnnounceCommand implements CommandExecutor {

    Announcements main;

    public AnnounceCommand(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Syntax: /announce <id>
        if (main.data.contains("presets." + args[0])) {
            String body = main.data.getString("presets." + args[0] + ".text");
            AnnouncementContext context = AnnouncementContext.valueOf(main.data.getString("presets." + args[0] + ".context"));
            AnnouncementBuilder.parsePlaceholdersAndAnnounce(body, context, main.data.getString("presets." + args[0] + ".target", "none"), main);

        } else {
            sender.sendMessage(SimpleMessageBuilder.parse("&cThere are no announcements with this id &r(&b" + args[0] + "&r)."));
        }

        return true;
    }
}
