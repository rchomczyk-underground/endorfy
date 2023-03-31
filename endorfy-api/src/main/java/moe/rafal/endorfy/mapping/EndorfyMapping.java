package moe.rafal.endorfy.mapping;

import java.util.UUID;

public class EndorfyMapping {

    private final UUID uniqueId;
    private String username;

    protected EndorfyMapping(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    /**
     * Gets the player's unique id, which has been
     * stored during last player's connection.
     * @return player's unique id
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Gets the player's username, which has been
     * stored during last player's connection. In
     * situation, while player changes his username,
     * without unique id change it will be automatically
     * updated to be up-to-date on his next connection.
     * @return player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the player's username, while it is out-of-date,
     * for example, because of manual username change.
     * @param username current player's username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
