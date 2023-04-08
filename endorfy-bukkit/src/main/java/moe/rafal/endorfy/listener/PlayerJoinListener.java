package moe.rafal.endorfy.listener;

import moe.rafal.endorfy.mapping.MappingController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final MappingController mappingController;

    public PlayerJoinListener(MappingController mappingController) {
        this.mappingController = mappingController;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        mappingController.createOrUpdateMapping(
            event.getPlayer().getUniqueId(),
            event.getPlayer().getName());
    }
}
