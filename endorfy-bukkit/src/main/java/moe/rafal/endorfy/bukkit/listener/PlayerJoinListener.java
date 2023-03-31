package moe.rafal.endorfy.bukkit.listener;

import moe.rafal.endorfy.mapping.MappingService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final MappingService mappingService;

    public PlayerJoinListener(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        mappingService.getMappingByUniqueId(event.getPlayer().getUniqueId())
            .thenAccept(mapping -> {
                String currentUsername = event.getPlayer().getName();
                if (mapping == null) {
                    mappingService.saveMapping(mappingService.createMappingOf(event.getPlayer().getUniqueId(), currentUsername));
                    return;
                }

                String expectedUsername = mapping.getUsername();
                if (expectedUsername.equals(currentUsername)) {
                    return;
                }

                mapping.setUsername(currentUsername);
                mappingService.saveMapping(mapping);
            });
    }
}
