package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementTrigger;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleAutoAnnounce implements CommandExecutor {

    Announcements main;

    public ScheduleAutoAnnounce(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Syntax: /setautoannounce <id> <condition>

        if (args.length == 2) {
            AnnouncementTrigger condition = AnnouncementTrigger.valueOf(args[1]);
            String id = args[0];
            String text = null;
            if (main.data.getString("presets." + args[0]) != null) {
                text = main.data.getString("presets." + args[0]);
            } else {
                sender.sendMessage(SimpleMessageBuilder.parse("&cThere are no announcements with this id &b(&r" + id + "&b)&c."));
                return true;
            }

            if (!condition.getRequiresPlayerPlaceholder()) {
                if (text.contains("%currentPlayer%")) {
                    sender.sendMessage(SimpleMessageBuilder.parse(String.format("&cThe chosen announcement &b(&r%s&b) &cdoes not support the use of this condition &b(&r%s&b)&c: the announcement contains placeholder(s) for the current player, which will not be replaced by this condition.", id, condition.name().toLowerCase())));
                    return true;
                }
            }

            main.data.set("presets." + id + ".trigger", condition.name());

            ArrayList<String> list = new ArrayList<>();
            if (main.data.getList("coredata.announcementList") != null) {
                list = (ArrayList<String>) main.data.getList("coredata.announcementList");
            }
            list.add(id);

            main.data.set("coredata.announcementList", list);

            try {
                main.data.save(main.dataFile);
                sender.sendMessage(SimpleMessageBuilder.parse("&aSuccessfully applied the auto-announce condition. &eYou can reset it with &b/resetautoaanounce &d<id>"));
            } catch (IOException e) {
                sender.sendMessage(SimpleMessageBuilder.parse("&cFailed to apply changes: &e" + e.getMessage()));
                e.printStackTrace();
                return true;
            }


        }


        return true;
    }
}
