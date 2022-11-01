package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class EditAnnouncementCommand implements CommandExecutor {

    Announcements main;

    public EditAnnouncementCommand(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Syntax: /editannouncement <id> <property> <newvalue>
        if (args.length >= 2) {
            String id = args[0];
            String editableProperty = args[1];

            StringBuilder b = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                b.append(args[i]).append(" ");
            }
            String newValue = b.toString().trim();

            if (isValidProperty(id, editableProperty)) {
                String oldVal = main.data.getString("presets." + id + "." + editableProperty);
                setProperty(id, editableProperty, newValue);
                if (editableProperty.equalsIgnoreCase("text")) {
                    sender.sendMessage(SimpleMessageBuilder.parse("&b[&eAnnouncements &a&lEdit&r&b] &aSuccessfully updated the value for &b[&e" + editableProperty + "&b] &a: &e(&b" + oldVal.substring(0, (oldVal.length() / 4)) + "... &a-> &b" + newValue + "&e)&a."));
                } else {
                    sender.sendMessage(SimpleMessageBuilder.parse("&b[&eAnnouncements &a&lEdit&r&b] &aSuccessfully updated the value for &b[&e" + editableProperty + "&b] &a: &e(&b" + oldVal + " &a-> &b" + newValue + "&e)&a."));
                }
            } else {
                sender.sendMessage(SimpleMessageBuilder.parse("&b[&eAnnouncements &a&lEdit&r&b] &cCould not update the property: &e" + id + " &chas no property &b" + editableProperty));
            }

        } else {
            sender.sendMessage(SimpleMessageBuilder.parse("&cToo few parameters provided."));
            return false;
        }


        return true;
    }

    private void setProperty(String id, String editableProperty, String newValue) {
        main.data.set("presets." + id + "." + editableProperty, newValue);

        try {
            main.data.save(main.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(SimpleMessageBuilder.parse("&cAn error has occurred while saving the announcement data: &e" + e.getMessage()));
                }
            }
        }
    }

    private boolean isValidProperty(String id, String editableProperty) {
        return main.data.contains("presets." + id + "." + editableProperty);
    }
}
