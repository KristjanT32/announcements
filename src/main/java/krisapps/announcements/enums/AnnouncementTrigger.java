package krisapps.announcements.enums;

import javax.annotation.Nullable;

public enum AnnouncementTrigger {

    SHOW_ON_JOIN(true),
    SHOW_ON_LEAVE(true),
    SHOW_ON_SERVER_START(false),
    SHOW_ON_PLAYER_KICKED(true),
    SHOW_ON_PLAYER_BANNED(true);

    @Nullable
    private boolean requiresPlayerPlaceholder = false;

    AnnouncementTrigger(boolean requiresPlayerPlaceholder) {
        this.requiresPlayerPlaceholder = requiresPlayerPlaceholder;
    }

    public boolean getRequiresPlayerPlaceholder() {
        return this.requiresPlayerPlaceholder;
    }

    public void setRequiresPlayerPlaceholder(boolean value) {
        this.requiresPlayerPlaceholder = value;
    }
}
