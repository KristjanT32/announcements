package krisapps.announcements.utilities;

import org.bukkit.ChatColor;

public class SimpleMessageBuilder {

    private static final char ALTERNATE_COLOR_CHARACTER = '&';

    public static String parse(String text) {
        return ChatColor.translateAlternateColorCodes(ALTERNATE_COLOR_CHARACTER, text);
    }

}
