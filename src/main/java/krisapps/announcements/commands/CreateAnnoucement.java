package krisapps.announcements.commands;

import krisapps.announcements.Announcements;
import krisapps.announcements.enums.AnnouncementContext;
import krisapps.announcements.utilities.SimpleMessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CreateAnnoucement implements CommandExecutor {

    public CommandSender sender;
    Announcements main;


    public CreateAnnoucement(Announcements main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Syntax: `/createannouncement <id> <context> [contextSpecificArgument] <announcementBody>`
        AnnouncementContext context;
        String contextVariable = "";
        StringBuilder bodyText = new StringBuilder();

        this.sender = sender;

        if (args.length >= 3) {

            try {
                AnnouncementContext _temp = AnnouncementContext.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(SimpleMessageBuilder.parse("&cSorry, but &e" + args[1] + " &cis not a valid announcement context."));
                sender.sendMessage(SimpleMessageBuilder.parse("&eValid contexts are: &b" + printContexts()));
                return false;
            }
            context = AnnouncementContext.valueOf(args[1].toUpperCase());

            if (context.equals(AnnouncementContext.CONTEXT_SPECIFIC_PLAYER)) {
                for (int i = 3; i < args.length; i++) {
                    bodyText.append(args[i]).append(" ");
                }
            } else {
                for (int i = 2; i < args.length; i++) {
                    bodyText.append(args[i]).append(" ");
                }
            }

            if (context.equals(AnnouncementContext.CONTEXT_SPECIFIC_PLAYER)) {
                contextVariable = args[2];
            } else {
                contextVariable = null;
            }
            create(context, contextVariable, bodyText.toString(), args[0]);

        } else {
            sender.sendMessage(SimpleMessageBuilder.parse("&cToo few arguments provided."));
            return false;
        }


        return true;
    }

    private void create(AnnouncementContext context, String contextVariable, String body, String id) {
        YamlConfiguration data = (YamlConfiguration) main.data;
        data.set("presets." + id + ".context", context.name());
        if (contextVariable != null) {
            data.set("presets." + id + ".target", contextVariable);
        }
        data.set("presets." + id + ".text", body);

        try {
            data.save(main.dataFile);
            sender.sendMessage(SimpleMessageBuilder.parse("&aAnnouncement successfully created! You can use &e/announce &b" + id + " &ato announce it."));
            if (main.config.getBoolean("config.feedback.sendAnnouncementDetailsOnCreate")) {
                sender.sendMessage(SimpleMessageBuilder.parse("&e* Announcement Identifier: &b" + id));
                sender.sendMessage(SimpleMessageBuilder.parse("&e* Announcement Context: &b" + context));
                if (contextVariable != null) {
                    sender.sendMessage(SimpleMessageBuilder.parse("&e* Announcement Target: &b" + contextVariable + checkPlayer(contextVariable)));
                }
                sender.sendMessage("&e=&b[&lANNOUNCEMENT CONTENT&r&b]&e==================================");
                sender.sendMessage(body);
                sender.sendMessage("&e=&b[&l--------------------&r&b]&e==================================");
            }
        } catch (IOException e) {
            main.getLogger().warning("Could not create an announcement: " + e.getMessage());
            e.printStackTrace();
            sender.sendMessage(SimpleMessageBuilder.parse("&cSomething went wrong when creating the announcement: &b" + e.getMessage()));
        }
    }

    private String checkPlayer(String player) {
        if (Bukkit.getPlayer(player) == null) {
            return "&c[&l!&r&c]";
        } else {
            return "&a[&l+&r&a]";
        }
    }

    private String printContexts() {
        StringBuilder sb = new StringBuilder();
        for (AnnouncementContext context : AnnouncementContext.values()) {
            sb.append(context.toString().toLowerCase()).append("&e, &b");
        }
        return sb.replace(sb.length(), sb.length(), "").toString();
    }
}
