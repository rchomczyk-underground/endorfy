package moe.rafal.endorfy.facade;

import moe.rafal.endorfy.mapping.EndorfyMapping;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EndorfyFacade {

    /**
     * Gets the mapping containing player's unique id, username,
     * by searching through cache, or database for mapping stored
     * under player's unique id.
     * @param uniqueId player's unique id
     * @return the mapping containing player's unique id, username
     */
    CompletableFuture<EndorfyMapping> getMappingByUniqueId(UUID uniqueId);

    /**
     * Gets the mapping containing player's unique id, username,
     * by searching through cache, or database for mapping stored
     * under player's username.
     * @param username player's username
     * @return the mapping containing player's unique id, username
     */
    CompletableFuture<EndorfyMapping> getMappingByUsername(String username);
}
