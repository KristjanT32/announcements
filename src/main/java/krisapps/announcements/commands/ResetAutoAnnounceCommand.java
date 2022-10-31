package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class ResetAutoAnnounceCommand implements CommandExecutor {

    Announcements main;

    public ResetAutoAnnounceCommand(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Syntax: /resetautoannounce <id>

        if (main.data.getConfigurationSection("presets") != null) {
            if (main.data.getString("presets." + args[0]) != null) {

                main.data.set("presets." + args[0] + ".trigger", null);

                ArrayList<String> list = new ArrayList<>();
                if (main.data.getList("coredata.announcementList") != null) {
                    list = (ArrayList<String>) main.data.getList("coredata.announcementList");
                }
                list.remove(args[0]);

                main.data.set("coredata.announcementList", list);

                try {
                    main.data.save(main.dataFile);
                    sender.sendMessage(SimpleMessageBuilder.parse("&aSuccessfully reset the auto-announce condition. &eYou can reschedule it with &b/scheduleautoaanounce &d<id>"));
                } catch (IOException e) {
                    sender.sendMessage(SimpleMessageBuilder.parse("&cFailed to apply changes: &e" + e.getMessage()));
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(SimpleMessageBuilder.parse("&cThere are no announcements with this id &r(&b" + args[0] + "&r)."));
            }
        }


        return true;
    }
}
