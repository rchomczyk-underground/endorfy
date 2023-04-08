package moe.rafal.endorfy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import moe.rafal.endorfy.mapping.MappingController;

public class PostLoginListener {

    private final MappingController mappingController;

    public PostLoginListener(MappingController mappingController) {
        this.mappingController = mappingController;
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        mappingController.createOrUpdateMapping(
            event.getPlayer().getUniqueId(),
            event.getPlayer().getUsername());
    }
}
